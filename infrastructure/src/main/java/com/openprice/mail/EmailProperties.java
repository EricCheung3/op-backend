package com.openprice.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("application.email")
public class EmailProperties {
    /**
     * Name of administrator the notification or contact message will send to.
     */
    @Getter @Setter
    private String adminName;
    
    /**
     * Email of administrator the notification or contact message will send to.
     */
    @Getter @Setter
    private String adminEmail;
    
    /**
     * System message sender name.
     */
    @Getter @Setter
    private String systemName;
    
    /**
     * System message sender email.
     */
    @Getter @Setter
    private String systemEmail;
        
}
