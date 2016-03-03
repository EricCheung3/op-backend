package com.openprice;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.openprice.mail.EmailProperties;
import com.openprice.mail.EmailService;
import com.openprice.mail.sendgrid.SendgridEmailService;
import com.openprice.mail.stub.DummyEmailService;
import com.openprice.parser.categorypredictor.SimpleCategoryPredictor;
import com.openprice.predictor.CategoryPredictor;
import com.openprice.store.MetadataLoader;
import com.openprice.store.StoreMetadata;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAspectJAutoProxy
@EnableAsync
public abstract class AbstractApiApplication {

    @Inject
    private Environment environment;

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

    @Bean @Profile("no_email")
    public EmailService dummyEmailService() {
        return new DummyEmailService();
    }

    @Bean @Profile("sendgrid")
    public EmailService sendgridEmailService() {
        return new SendgridEmailService(environment.getProperty("sendgrid.username"),
                                        environment.getProperty("sendgrid.password"));
    }

    @Bean @Profile("sendgrid")
    public EmailProperties emailProperties(AbstractApplicationSettings settings) {
        return settings.getEmail();
    }

    @Bean
    public CategoryPredictor categoryPredictor() {
        return SimpleCategoryPredictor.fromConfig();
    }

    @Bean
    public StoreMetadata storeMetadata() {
        return MetadataLoader.loadMetadata();
    }

    /**
     * Trace REST API calls for development.
     * @return
     */
    @Bean @Profile("trace")
    public RestApiLoggingAspect loggingAspect() {
        return new RestApiLoggingAspect();
    }

}
