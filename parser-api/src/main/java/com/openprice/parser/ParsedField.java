package com.openprice.parser;

/**
 * Value of receipt field from ReceiptParser result, such as address, slogan, date, etc.
 */
public interface ParsedField {

    /**
     * Recognized field type.
     *
     * @return
     */
    ReceiptFieldType getFieldType();

    /**
     * Parsed field value.
     *
     * @return
     */
    String getFieldValue();

    /**
     * Line number for this field in the original receipt OCR result text.
     * If multiple lines, return the first line number.
     *
     * @return
     */
    int getLineNumber();

}
