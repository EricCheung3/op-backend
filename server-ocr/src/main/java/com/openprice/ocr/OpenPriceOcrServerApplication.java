package com.openprice.ocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.openprice.file.FileFolderSettings;

@SpringBootApplication
@ComponentScan({ "com.openprice.file", "com.openprice.ocr" })
@EnableConfigurationProperties({FileFolderSettings.class})
public class OpenPriceOcrServerApplication{

    public static void main(String[] args) throws Exception {
        SpringApplication.run(OpenPriceOcrServerApplication.class, args);
    }

    @Bean
    public Tesseract tesseract() {
        return new TesseractCommandImpl();
    }
}
