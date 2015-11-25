package com.openprice.ocr.api;

import lombok.Data;

@Data
public class ImageProcessResult {

    private boolean success;

    private String ocrResult;

    private String errorMessage;

}
