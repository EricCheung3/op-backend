package com.openprice.rest;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.openprice.file.FileFolderSettings;
import com.openprice.file.FileSystemService;
import com.openprice.mail.EmailService;
import com.openprice.mail.stub.DummyEmailService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAutoConfiguration
@ComponentScan({ "com.openprice.domain", "com.openprice.rest", "com.openprice.process", "com.openprice.parser" })
@EntityScan("com.openprice.domain")
@EnableJpaRepositories("com.openprice.domain")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Slf4j
public abstract class AbstractRestApiTestApplication extends WebSecurityConfigurerAdapter {

    @Inject
    private Environment env;

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAware<String>() {
            @Override
            public String getCurrentAuditor() {
                final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication == null || !authentication.isAuthenticated()) {
                  return null;
                }

                return authentication.getName();
            }
        };
    }

    @Bean
    public EmailService emailService() {
        return new DummyEmailService();
    }

    @Bean
    public FileSystemService fileSystemService() {
        return new FileSystemService(new FileFolderSettings());
    }

    @PostConstruct
    public void initApplication() throws IOException {
        if (env.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration");
        } else {
            log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        }
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder builder) throws Exception {
        builder
            .authenticationProvider(testAuthenticationProvider());
    }

    @Bean
    public TestAuthenticationProvider testAuthenticationProvider() {
        return new TestAuthenticationProvider(getUserDetailsService());
    }

    @Bean
    public TestAuthenticationFilter testAuthenticationFilter() throws Exception {
        return new TestAuthenticationFilter(UtilConstants.URL_SIGNIN, authenticationManagerBean());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    protected abstract UserDetailsService getUserDetailsService();
}
