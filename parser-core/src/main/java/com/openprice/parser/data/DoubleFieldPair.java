package com.openprice.parser.data;

import lombok.Data;

/**
 * a simple double-FieldNameAddressLines pair class.
 */
@Data
public class DoubleFieldPair {
    private final double score;
    private final ReceiptField fieldName;
    private final String value;
}