package com.openprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.openprice.file.FileFolderSettings;
import com.openprice.internal.client.InternalServiceSettings;
import com.openprice.mail.EmailProperties;

@EnableConfigurationProperties({EmailProperties.class, FileFolderSettings.class, InternalServiceSettings.class})
public class OpenPriceApiApplication extends AbstractApiApplication {

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OpenPriceApiApplication.class);
        app.run(args);
    }
}
