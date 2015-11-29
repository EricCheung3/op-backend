package com.openprice.ocr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.core.env.Environment;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractOcrEngine implements OcrEngine {

    @Getter
    private final String serverName;

    public AbstractOcrEngine(final Environment environment) {
        this.serverName = environment.getProperty("ocr.server.name");
    }

    @PostConstruct
    public void init() {
        log.info("{} OCR Engine {} started.", getEngineName(), serverName);
    }

    @PreDestroy()
    public void stop() {
        log.info("{} OCR Engine {} stopped.", getEngineName(), serverName);
    }

    @Override
    public String scan(final String filePath) {
        final String command = getCommand(filePath);
        log.info("call {} OCR Engine: '{}'.", getEngineName(), command);
        final long start = System.currentTimeMillis();

        final StringBuilder output = new StringBuilder();
        final StringBuilder errorMessage = new StringBuilder();
        try {
            final Process p = Runtime.getRuntime().exec(command);
            p.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

            reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = reader.readLine())!= null) {
                errorMessage.append(line + "\n");
            }
            if (errorMessage.length() > 0) {
                log.warn("Got error message from {} OCR Engine {} : {}.", getEngineName(), serverName, errorMessage);
            }
            log.info("took {} milli-seconds to ocr image.", (System.currentTimeMillis() - start));
        } catch (Exception ex) {
            ex.printStackTrace(); // TODO remove it?
            log.error("Got error while calling {} OCR Engine {}!", getEngineName(), serverName);
            throw new RuntimeException("Got exception from OCR Engine " + getEngineName()+ ":" + ex.getMessage(), ex);
        }

        return output.toString();
    }

    protected abstract String getCommand(final String filePath);

    protected abstract String getEngineName();
}
