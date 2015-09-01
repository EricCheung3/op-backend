package com.openprice.ocr;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TesseractCommandImpl implements Tesseract {

    @Override
    public String scan(final String filePath) {
        final String command = "./OP_OCR -i " + filePath;
        log.info("call tesseract: '{}'.", command);
        final long start = System.currentTimeMillis();
        
        final StringBuilder output = new StringBuilder();
        try {
            final Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
            log.info("took {} milli-seconds to ocr image.", (System.currentTimeMillis() - start));
        } catch (Exception ex) {
            log.error("Got error while calling tesseract!", ex);
            // TODO 
        }

        return output.toString();
    }

}
