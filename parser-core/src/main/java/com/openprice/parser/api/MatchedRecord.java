package com.openprice.parser.api;

import java.util.Set;

import com.openprice.parser.ReceiptFieldType;

public interface MatchedRecord {

    // whether a line is matched
    boolean isFieldLine(int line);

    // whether a field is matched
    boolean fieldNameIsMatched(final ReceiptFieldType f);

    boolean fieldIsMatched(final ReceiptFieldType f);

    Set<ReceiptFieldType> matchedFields(final int line);

    /**
     * get the last/maximum line number of all fields. It is the last field line.
     */
    int lastFieldLine();

}
