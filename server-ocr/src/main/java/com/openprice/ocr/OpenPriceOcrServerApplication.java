package com.openprice.ocr;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.openprice.file.FileSystemService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan({ "com.openprice.ocr" })
@EnableConfigurationProperties({OcrServerApplicationSettings.class})
@Slf4j
public class OpenPriceOcrServerApplication {

    @Inject
    private Environment environment;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(OpenPriceOcrServerApplication.class, args);
    }

    @PostConstruct
    public void initApplication() throws IOException {
        if (environment.getActiveProfiles().length == 0) {
            log.warn("No Spring profile configured, running with default configuration");
        } else {
            log.info("Running with Spring profile(s) : {}", Arrays.toString(environment.getActiveProfiles()));
        }
    }

    @Bean @Profile("abbyy")
    public OcrEngine abbyy() {
        return new AbbyyOcrEngine();
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
