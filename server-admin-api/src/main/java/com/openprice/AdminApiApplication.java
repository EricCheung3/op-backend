package com.openprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.openprice.file.FileSystemService;
import com.openprice.internal.client.InternalService;

@SpringBootApplication
@EnableConfigurationProperties({AdminApiApplicationSettings.class})
public class AdminApiApplication extends AbstractApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
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
