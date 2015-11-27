package com.openprice.ocr.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageProcessResult {

    private boolean success;

    private String ocrResult;

    private String errorMessage;

}
