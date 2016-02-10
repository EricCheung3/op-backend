package com.openprice.parser.api;

import java.util.Set;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.data.StringInt;

/**
 * the record/result of MatchField
 * Note MatchedRecord is the result of matching,
 * MatchField is the operation of matching
 */
public interface MatchedRecord {

    // whether a line is matched
    boolean isFieldLine(int line);

    // whether a field is matched
    boolean fieldIsMatched(ReceiptFieldType f);

    //a line may match multiple fields, get the matched fields
    Set<ReceiptFieldType> matchedFieldsOnLine(int line);

    //a filed may match multiple lines, get the matched lines
    Set<Integer> matchedLinesOfField(ReceiptFieldType type);

    //get the parsed value for a field
    StringInt valueOfField(ReceiptFieldType type);

    //get the last/maximum line number of all fields. It is the last field line.
    int lastFieldLine();

    //item stops number (no item after this line)
    int itemStopLineNumber();

    void putFieldLineValue(ReceiptFieldType fName, int lineNumber, String value);

}
