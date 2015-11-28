package com.openprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.openprice.file.FileSystemService;
import com.openprice.internal.client.InternalService;
import com.openprice.mail.EmailProperties;

@EnableConfigurationProperties({EmailProperties.class, AdminApiApplicationSettings.class})
public class OpenPriceAdminApiApplication extends AbstractApiApplication {

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OpenPriceAdminApiApplication.class);
        app.run(args);
    }

    @Bean
    public FileSystemService fileSystemService(AdminApiApplicationSettings settings) {
        return new FileSystemService(settings.getFile());
    }

    @Bean
    InternalService internalService(final AdminApiApplicationSettings settings) {
        return new InternalService(settings.getInternal());
    }

}
