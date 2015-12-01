package com.openprice;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.openprice.mail.EmailProperties;
import com.openprice.mail.EmailService;
import com.openprice.mail.sendgrid.SendgridEmailService;
import com.openprice.mail.stub.DummyEmailService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAspectJAutoProxy
@Slf4j
public abstract class AbstractApiApplication {

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

    @Bean @Profile("sendgrid")
    public EmailService sendgridEmailService() {
        return new SendgridEmailService(environment.getProperty("sendgrid.username"),
                                        environment.getProperty("sendgrid.password"));
    }

    @Bean @Profile("no_email")
    public EmailService dummyEmailService() {
        return new DummyEmailService();
    }

    @Bean @Profile("sendgrid")
    public EmailProperties emailProperties(AbstractApplicationSettings settings) {
        return settings.getEmail();
    }

    /**
     * Trace REST API calls for development.
     * @return
     */
    @Bean @Profile("dev")
    public RestApiLoggingAspect loggingAspect() {
        return new RestApiLoggingAspect();
    }

}
