package com.openprice.ocr.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request parameters for receipt image processing.
 * TODO: handle admin uploaded receipt images - system owned receipt images are stored in different folder
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageProcessRequest {

    private String imageFilePath;

}
