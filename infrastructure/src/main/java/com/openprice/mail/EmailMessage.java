package com.openprice.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Wraps email message.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {

    private String fromEmail;
    private String fromName;
    private String toEmail;
    private String toName;
    private String subject;
    private String body;
    private String replyTo;

    public static EmailMessage createEmailToAdmin(final EmailProperties properties,
                                                  final String subject,
                                                  final String body,
                                                  final String replyTo) {
        return new EmailMessage(properties.getSystemEmail(),
                                properties.getSystemName(),
                                properties.getAdminEmail(),
                                properties.getAdminName(),
                                subject,
                                body,
                                replyTo);
    }

    public static EmailMessage createEmail(final EmailProperties properties,
                                           final String toEmail,
                                           final String toName,
                                           final String subject, final String body,
                                           final String replyTo) {
        return new EmailMessage(properties.getSystemEmail(),
                                properties.getSystemName(),
                                toEmail,
                                toName,
                                subject,
                                body,
                                replyTo);

    }
}
