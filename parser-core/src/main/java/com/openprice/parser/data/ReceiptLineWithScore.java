package com.openprice.parser.data;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.api.ReceiptLine;

import lombok.Value;
/**
 * Value object to store one line in the receipt with matching score for a field from any algorithm.
 */
@Value
public class ReceiptLineWithScore {
    private double score;
    private ReceiptFieldType field;
    private ReceiptLine receiptLine;
    private String value;

}
