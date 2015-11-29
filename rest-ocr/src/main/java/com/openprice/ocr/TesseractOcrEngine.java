package com.openprice.ocr;

import org.springframework.core.env.Environment;

public class TesseractOcrEngine extends AbstractOcrEngine {

    public TesseractOcrEngine(final Environment environment) {
        super(environment);
    }

    @Override
    protected String getCommand(String filePath) {
        return "./OP_OCR -g sup -i " + filePath;
    }

    @Override
    protected String getEngineName() {
        return "Tesseract";
    }

}
