package com.openprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.openprice.file.FileSystemService;
import com.openprice.process.ProcessSettings;

@EnableConfigurationProperties({InternalApiApplicationSettings.class})
public class InternalApiApplication extends AbstractApiApplication {

    /**
     * Main method, used to run the application.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(InternalApiApplication.class);
        app.run(args);
    }

    @Bean
    public FileSystemService fileSystemService(InternalApiApplicationSettings settings) {
        return new FileSystemService(settings.getFile());
    }

    @Bean
    public ProcessSettings processSettings(InternalApiApplicationSettings settings) {
        return settings.getProcess();
    }

}
