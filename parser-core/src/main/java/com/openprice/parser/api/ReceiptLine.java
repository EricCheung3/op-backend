package com.openprice.parser.api;

import lombok.Value;

@Value
public class ReceiptLine {
    //original line text
    private String originalText;

    // cleaned line text, usually trimmed
    private String cleanText;

    //original line number
    private int number;

    //receipt data that contains this ReceiptLine
    private ReceiptData receipt;
}
