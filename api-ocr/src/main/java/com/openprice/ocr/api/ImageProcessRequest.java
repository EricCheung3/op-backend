package com.openprice.ocr.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request parameters for receipt image processing.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageProcessRequest {

    private String imageFilePath;

}
