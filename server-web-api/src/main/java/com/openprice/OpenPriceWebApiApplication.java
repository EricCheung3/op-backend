package com.openprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.openprice.file.FileSystemService;
import com.openprice.internal.client.InternalService;

@EnableConfigurationProperties({WebApiApplicationSettings.class})
public class OpenPriceWebApiApplication extends AbstractApiApplication {

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OpenPriceWebApiApplication.class);
        app.run(args);
    }

    @Bean
    public FileSystemService fileSystemService(WebApiApplicationSettings settings) {
        return new FileSystemService(settings.getFile());
    }

    @Bean
    InternalService internalService(final WebApiApplicationSettings settings) {
        return new InternalService(settings.getInternal());
    }

}
