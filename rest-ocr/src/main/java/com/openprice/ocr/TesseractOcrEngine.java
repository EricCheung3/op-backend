package com.openprice.ocr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TesseractOcrEngine implements OcrEngine {

    @Override
    public String scan(final String filePath) {
        final String command = "./OP_OCR -g sup -i " + filePath;
        log.info("call Tesseract: '{}'.", command);
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
                log.warn("Got error message from Tesseract OCR program: " + errorMessage);
            }
            log.info("took {} milli-seconds to ocr image.", (System.currentTimeMillis() - start));
        } catch (Exception ex) {
            log.error("Got error while calling Tesseract!", ex);
            throw new RuntimeException("Got exception from Tesseract: " + ex.getMessage(), ex);
        }

        return output.toString();
    }

}
