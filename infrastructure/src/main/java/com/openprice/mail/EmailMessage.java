package com.openprice.mail;

/**
 * Wraps email message.
 *
 */
public class EmailMessage {

    private String fromEmail;
    private String fromName;
    private String toEmail;
    private String toName;
    private String subject;
    private String body;
    private String replyTo;

    public String getFromEmail() {
        return fromEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public String getToEmail() {
        return toEmail;
    }

    public String getToName() {
        return toName;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public EmailMessage(String fromEmail, String fromName, String toEmail, String toName, String subject, String body,
            String replyTo) {
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.toEmail = toEmail;
        this.toName = toName;
        this.subject = subject;
        this.body = body;
        this.replyTo = replyTo;
    }

    public EmailMessage(EmailProperties properties, String subject, String body, String replyTo) {
        this.fromEmail = properties.getSystemEmail();
        this.fromName = properties.getSystemName();
        this.toEmail = properties.getAdminEmail();
        this.toName = properties.getAdminName();
        this.subject = subject;
        this.body = body;
        this.replyTo = replyTo;
    }

    public EmailMessage(EmailProperties properties, String toEmail, String toName, String subject, String message,
            String replyTo) {
        this.fromEmail = properties.getSystemEmail();
        this.fromName = properties.getSystemName();
        this.toEmail = toEmail;
        this.toName = toName;
        this.subject = subject;
        this.body = message;
        this.replyTo = replyTo;
    }
}
