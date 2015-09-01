package com.openprice.ocr;

import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openprice.common.api.ImageProcessResult;
import com.openprice.file.FileFolderSettings;
import com.openprice.mail.EmailProperties;

@SpringBootApplication
@ComponentScan({ "com.openprice.file", "com.openprice.ocr" })
@Controller
@EnableConfigurationProperties({EmailProperties.class, FileFolderSettings.class})
public class OpenPriceOCRServerApplication{

    @Inject
    OCRProcessor ocrProcessor;
    
    @RequestMapping(method = RequestMethod.GET, value="/", produces = "text/plain")
    @ResponseBody
    String hello() {
        return "Hello!";
    }

    @RequestMapping(method = RequestMethod.GET, value="/process/{username}")
    @ResponseBody
    public ImageProcessResult process(@PathVariable("username") final String username,
                                      @RequestParam("fileName") final String fileName) {
        return ocrProcessor.processImage(username, fileName);
    }
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(OpenPriceOCRServerApplication.class, args);
    }

    @Bean
    public Tesseract tesseract() {
        return new TesseractCommandImpl();
    }
}
