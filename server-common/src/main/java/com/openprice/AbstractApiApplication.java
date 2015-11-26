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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.openprice.mail.EmailService;
import com.openprice.mail.sendgrid.EmailServiceImpl;
import com.openprice.mail.stub.DummyEmailService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAspectJAutoProxy
@Slf4j
public abstract class AbstractApiApplication extends WebSecurityConfigurerAdapter {

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
                return "system"; // always system as user.
            }
        };
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

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .anyRequest().permitAll()
            ;
    }

}
