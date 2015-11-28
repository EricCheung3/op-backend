package com.openprice.ocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.openprice.file.FileFolderSettings;
import com.openprice.file.FileSystemService;

@SpringBootApplication
@ComponentScan({ "com.openprice.file", "com.openprice.ocr" })
public class OcrServerTestApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(OcrServerTestApplication.class, args);
    }

    @Bean
    public FileSystemService fileSystemService() {
        return new FileSystemService(new FileFolderSettings());
    }

    @Bean
    public OcrEngine tesseract() {
        return new OcrEngine() {

            @Override
            public String scan(String filePath) {
                return "result";
            }

        };
    }

}
