package com.openprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.openprice.file.FileFolderSettings;
import com.openprice.mail.EmailProperties;

@EnableConfigurationProperties({EmailProperties.class, FileFolderSettings.class})
public class OpenPriceAdminApiApplication extends AbstractApiApplication {

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OpenPriceAdminApiApplication.class);
        app.run(args);
    }

}
