package com.openprice.ocr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.io.CharStreams;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeOcrEngine extends AbstractOcrEngine {

    static final String OCR_RESULT_FILE_PATH = "ocrResult.txt";

    private String staticResult;

    public FakeOcrEngine(final Environment environment) {
        super(environment);
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();
        try {
            Resource resource = new ClassPathResource(OCR_RESULT_FILE_PATH);
            InputStream resourceInputStream = resource.getInputStream();
            this.staticResult = CharStreams.toString(new InputStreamReader(resourceInputStream, "UTF-8"));
            log.info("Fake OCR Engine started with loaded static ocr result");
        } catch (IOException ex) {
            log.error("Cannot load static result from file {}, please check the file exits.", OCR_RESULT_FILE_PATH);
            throw new RuntimeException("Error loading static result file "+OCR_RESULT_FILE_PATH, ex);
        }
    }

    @Override
    public String scan(final String filePath) {
        File imageFile = new File(filePath);
        if (!imageFile.exists()) {
            throw new RuntimeException("No file at "+filePath);
        }
        log.info("Fake OCR Engine {} return static ocr result.", getName());
        return staticResult;
    }
}
