package com.openprice;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.openprice.file.FileFolderSettings;
import com.openprice.mail.EmailProperties;
import com.openprice.mail.EmailService;
import com.openprice.mail.sendgrid.EmailServiceImpl;
import com.openprice.mail.stub.DummyEmailService;
import com.openprice.parser.simple.SimpleParser;
import com.openprice.process.ProcessSettings;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAspectJAutoProxy
@EnableConfigurationProperties({EmailProperties.class, FileFolderSettings.class, ProcessSettings.class})
@Slf4j
public class OpenPriceAdminAPIApplication {

    @Inject
    private Environment environment;

    /**
     * Initializes application.
     * <p/>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p/>
     */
    @PostConstruct
    public void initApplication() throws IOException {
        if (environment.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration");
        } else {
            log.info("Running with Spring profile(s) : {}", Arrays.toString(environment.getActiveProfiles()));
        }
    }

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OpenPriceAdminAPIApplication.class);
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
    public SimpleParser simpleParser() {
        return new SimpleParser();
    }

    @Bean @Profile("sendgrid")
    public EmailService sendgridEmailService() {
        return new EmailServiceImpl(environment.getProperty("sendgrid.username"),
                                    environment.getProperty("sendgrid.password"));
    }

    @Bean @Profile("noemail")
    public EmailService dummyEmailService() {
        return new DummyEmailService();
    }

    /**
     * Trace REST API calls for development.
     * @return
     */
    @Bean
    @Profile("dev")
    public RestApiLoggingAspect loggingAspect() {
        return new RestApiLoggingAspect();
    }

}