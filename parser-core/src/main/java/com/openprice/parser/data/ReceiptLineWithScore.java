package com.openprice.parser.data;

import com.openprice.parser.ReceiptLine;

import lombok.Value;
/**
 * Value object to store one line in the receipt with matching score for a field from any algorithm.
 */
@Value
public class ReceiptLineWithScore {
    private double score;
    private ReceiptField field;
    private ReceiptLine receiptLine;
    private String value;

}
