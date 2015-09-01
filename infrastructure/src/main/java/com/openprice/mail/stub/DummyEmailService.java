package com.openprice.mail.stub;

import com.openprice.mail.EmailMessage;
import com.openprice.mail.EmailService;

import lombok.extern.slf4j.Slf4j;

/**
 * Simple EmailService implementation for test purpose.
 *
 */
@Slf4j
public class DummyEmailService implements EmailService {
    @Override
    public void sendEmail(final EmailMessage message) {
        log.info(String.format("Dummy: Send email to %s. From  %s: \" %s \"", 
                message.getToEmail(), message.getFromEmail(), message.getBody()));
    }
}
