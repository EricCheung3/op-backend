package com.openprice;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.openprice.mail.EmailService;
import com.openprice.mail.sendgrid.EmailServiceImpl;
import com.openprice.mail.stub.DummyEmailService;

@Configuration
public class EmailConfig {
    @Inject
    private Environment environment;

    @Bean @Profile("sendgrid")
    public EmailService sendgridEmailService() {
        return new EmailServiceImpl(environment.getProperty("sendgrid.username"),
                                    environment.getProperty("sendgrid.password"));
    }

    @Bean @Profile("noemail")
    public EmailService dummyEmailService() {
        return new DummyEmailService();
    }

}
