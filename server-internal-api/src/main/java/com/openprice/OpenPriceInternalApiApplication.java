package com.openprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.openprice.file.FileFolderSettings;
import com.openprice.mail.EmailProperties;
import com.openprice.process.ProcessSettings;

@EnableConfigurationProperties({EmailProperties.class, FileFolderSettings.class, ProcessSettings.class})
public class OpenPriceInternalApiApplication extends AbstractApiApplication {

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OpenPriceInternalApiApplication.class);
        app.run(args);
    }
}
