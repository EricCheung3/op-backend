package com.openprice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.openprice.common.client.ServiceSettings;
import com.openprice.file.FileFolderSettings;
import com.openprice.file.FileSystemService;
import com.openprice.internal.client.InternalService;
import com.openprice.mail.EmailProperties;
import com.openprice.mail.EmailService;
import com.openprice.mail.stub.DummyEmailService;
import com.openprice.parser.categorypredictor.SimpleCategoryPredictor;
import com.openprice.predictor.CategoryPredictor;
import com.openprice.store.MetadataLoader;
import com.openprice.store.StoreMetadata;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAspectJAutoProxy
public class ApiDocsApplication {

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApiDocsApplication.class);
        app.run(args);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAware<String>() {
            @Override
            public String getCurrentAuditor() {
                final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication == null || !authentication.isAuthenticated()) {
                    return "system"; // if no user logged in, we assume the system will create/modify the entity
                }

                return authentication.getName();
            }
        };
    }

    @Bean
    public EmailService dummyEmailService() {
        return new DummyEmailService();
    }

    @Bean
    public FileSystemService fileSystemService() {
        return new FileSystemService(new FileFolderSettings());
    }

    @Bean
    public InternalService internalService() {
        return new InternalService(new ServiceSettings());
    }

    @Bean
    public EmailProperties emailProperties() {
        return new EmailProperties("http://openprice,com",
                                   "Openprice Admin",
                                   "admin@openprice.com",
                                   "Openprice Team",
                                   "support@openprice.com");
    }

    @Bean
    public CategoryPredictor categoryPredictor() {
        return SimpleCategoryPredictor.fromConfig();
    }

    @Bean
    public StoreMetadata storeMetadata() {
        return MetadataLoader.loadMetadata();
    }


    @EnableWebSecurity
    public static class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .authorizeRequests()
                    .anyRequest().permitAll()
                    .and()
                .csrf().disable()
                ;
            // @formatter:on
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                .inMemoryAuthentication()
                    .withUser("user").password("groundtruth").roles("USER");
        }
    }
}
