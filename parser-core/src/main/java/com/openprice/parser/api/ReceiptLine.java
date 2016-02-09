package com.openprice.parser.api;

import lombok.Value;

@Value
public class ReceiptLine {
    private String originalText;//original line text
    private String cleanText; // cleaned line text, usually trimmed
    private int number;//original line number
    private ReceiptData receipt;
}
