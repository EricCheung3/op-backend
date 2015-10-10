package com.openprice.parser;

import lombok.Value;

@Value
public class ReceiptLine {
    private String originalText;
    private String cleanText; // trim
    private int number;
}
