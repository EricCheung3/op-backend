package com.openprice.mail.sendgrid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;

import com.openprice.mail.EmailMessage;
import com.openprice.mail.EmailService;
import com.sendgrid.SendGrid;

/**
 * Email Service implementation using Sendgrid.
 *
 */
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final String username;
    private final String password;

    public EmailServiceImpl(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    @Async
    public void sendEmail(final EmailMessage message) {
        final SendGrid sendgrid = new SendGrid(username, password);
        final SendGrid.Email email = new SendGrid.Email();
        email.setFrom(message.getFromEmail());
        email.setFromName(message.getFromName());
        email.addTo(message.getToEmail());
        email.addToName(message.getToName());
        email.setReplyTo(message.getReplyTo());
        email.setSubject(message.getSubject());
        email.setText(message.getBody());

        try {
            log.info("Try to send email to {}, message is: {}", message.getToEmail(), message.getBody());
            final SendGrid.Response response = sendgrid.send(email);
            log.info("Sent email successfully, response from SendGrid is: {}", response.getMessage());
        } catch (final Exception ex) {
            log.debug("Exception:", ex);
            log.error("Got exception when sending email through sendgrid: {}", ex.getMessage());
        }
    }
}
