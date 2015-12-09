package com.openprice.ocr;

import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.openprice.file.FileSystemService;

@SpringBootApplication
@EnableConfigurationProperties({OcrServerApplicationSettings.class})
public class OcrServerApplication {

    @Inject
    private Environment environment;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(OcrServerApplication.class, args);
    }

    @Bean @Profile("abbyy")
    public OcrEngine abbyy() {
        return new AbbyyOcrEngine(environment);
    }

    @Bean @Profile("fake_ocr")
    public OcrEngine fake() {
        return new FakeOcrEngine(environment);
    }

    @Bean
    public FileSystemService fileSystemService(OcrServerApplicationSettings settings) {
        return new FileSystemService(settings.getFile());
    }

}
