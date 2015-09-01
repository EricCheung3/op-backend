package com.openprice.mail;

/**
 * Interface for service that can send email.
 *
 */
public interface EmailService {

    void sendEmail(EmailMessage message);
}
