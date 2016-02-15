package com.openprice.parser.data;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.api.ReceiptLine;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * Value object to store one line in the receipt with matching score for a field from any algorithm.
 */
@Value
@AllArgsConstructor
public class ReceiptLineWithScore {
    private ReceiptLine receiptLine;
    private ReceiptFieldType field;

    //matching score of recieptLine and field
    private double score;

    //parsed field value from receiptLine
    private String value;
}
