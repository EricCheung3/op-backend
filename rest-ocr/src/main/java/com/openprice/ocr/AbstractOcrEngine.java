package com.openprice.ocr;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.core.env.Environment;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractOcrEngine implements OcrEngine {

    @Getter
    private final String name;

    public AbstractOcrEngine(final Environment environment) {
        this.name = environment.getProperty("ocr.server.name");
    }

    @PostConstruct
    public void init() {
        log.info("OCR Engine {} started.", name);
    }

    @PreDestroy()
    public void stop() {
        log.info("OCR Engine {} stopped.", name);
    }

}
