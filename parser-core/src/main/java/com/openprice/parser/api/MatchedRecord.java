package com.openprice.parser.api;

import java.util.Set;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.data.StringInt;
import com.openprice.store.StoreBranch;

public interface MatchedRecord {

    // whether a line is matched
    boolean isFieldLine(int line);

    // whether a field is matched
    boolean fieldNameIsMatched(ReceiptFieldType f);

    boolean fieldIsMatched(ReceiptFieldType f);

    //a line may match multiple fields, get the matched fields
    Set<ReceiptFieldType> matchedFields(int line);

    //a filed may match multiple lines, get the matched lines
    Set<Integer> matchedLines(ReceiptFieldType type);

    //get the parsed value for a field
    StringInt fieldValue(ReceiptFieldType type);

    //get the last/maximum line number of all fields. It is the last field line.
    int lastFieldLine();

    //match to a branch and record matched information
    void matchToBranch(ReceiptData receipt, StoreBranch storeBranch);

    //match to headers and record matched information
    void matchToHeaders(ReceiptData receipt, StoreConfig config, StoreParser parser);

}
