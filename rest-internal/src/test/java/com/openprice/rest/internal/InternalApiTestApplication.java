package com.openprice.rest.internal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.openprice.file.FileFolderSettings;
import com.openprice.file.FileSystemService;
import com.openprice.process.ProcessSettings;

@SpringBootApplication
@ComponentScan({ "com.openprice.domain", "com.openprice.rest", "com.openprice.process", "com.openprice.parser" })
@EntityScan("com.openprice.domain")
@EnableJpaRepositories("com.openprice.domain")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class InternalApiTestApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(InternalApiTestApplication.class, args);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAware<String>() {
            @Override
            public String getCurrentAuditor() {
                return "system";
            }
        };
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .anyRequest().permitAll()
            ;
    }

    @Bean
    public FileSystemService fileSystemService() {
        return new FileSystemService(new FileFolderSettings());
    }

    @Bean
    public ProcessSettings processSettings() {
        return new ProcessSettings(); // it will trigger StaticResultImageProcessor
    }
}
