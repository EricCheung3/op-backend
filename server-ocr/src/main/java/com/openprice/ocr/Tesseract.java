package com.openprice.ocr;

public interface Tesseract {

    /**
     * Scan the image file and return OCR result.
     *
     * @param filePath absolute path of image file
     * @return
     */
    String scan(String filePath);
}
