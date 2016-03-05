package com.openprice.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailProperties {
    /**
     * Openprice Web UI Server url
     */
    private String webServerUrl;

    /**
     * Name of administrator the notification or contact message will send to.
     */
    private String adminName;

    /**
     * Email of administrator the notification or contact message will send to.
     */
    private String adminEmail;

    /**
     * System message sender name.
     */
    private String systemName;

    /**
     * System message sender email.
     */
    private String systemEmail;

}
