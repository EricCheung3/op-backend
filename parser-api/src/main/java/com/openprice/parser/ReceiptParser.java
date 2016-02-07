package com.openprice.parser;

import java.util.List;

/**
 * Interface for receipt parser.
 */
public interface ReceiptParser {

    /**
     * Parses OCR result text and return ParsedReceipt object.
     *
     * @param ocrTextList list of OCR result text from receipt images.
     * @return null if ocrTextList is null or it has less than 5 lines of string.
     */
    ParsedReceipt parseReceiptOcrResult(final List<String> ocrTextList);
}
