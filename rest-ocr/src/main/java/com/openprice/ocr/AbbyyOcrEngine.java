package com.openprice.ocr;

import org.springframework.core.env.Environment;

public class AbbyyOcrEngine extends AbstractOcrEngine {

    public AbbyyOcrEngine(final Environment environment) {
        super(environment);
    }

    @Override
    protected String getCommand(String filePath) {
        return "./aby.sh slow " + filePath;
    }

    @Override
    protected String getEngineName() {
        return "ABBYY";
    }

}
