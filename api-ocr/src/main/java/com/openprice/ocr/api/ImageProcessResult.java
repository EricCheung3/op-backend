package com.openprice.ocr.api;

import lombok.Getter;
import lombok.Setter;

public class ImageProcessResult {

    @Getter @Setter
    private boolean success;

    @Getter @Setter
    private String ocrResult;

    @Getter @Setter
    private String errorMessage;

}
