package com.openprice.parser.data;

import com.openprice.parser.FieldName;

import lombok.Data;

/**
 * a simple double-FieldNameAddressLines pair class.
 */
@Data
public class DoubleFieldPair {
    private final double score;
    private final FieldName fieldName;
    private final String value;
}