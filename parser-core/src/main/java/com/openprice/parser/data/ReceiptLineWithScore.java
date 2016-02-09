package com.openprice.parser.data;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.api.ReceiptLine;

import lombok.Value;
/**
 * Value object to store one line in the receipt with matching score for a field from any algorithm.
 */
@Value
public class ReceiptLineWithScore {
    private ReceiptLine receiptLine;
    private ReceiptFieldType field;
    private double score;
    private String value;
}
