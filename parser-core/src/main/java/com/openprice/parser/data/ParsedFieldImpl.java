package com.openprice.parser.data;

import com.openprice.parser.ParsedField;
import com.openprice.parser.ReceiptFieldType;

import lombok.Data;

@Data
public class ParsedFieldImpl implements ParsedField{
    ReceiptFieldType fieldType;

    //parsed value of fieldType
    String fieldValue;

    //parsed fieldValue at the line number
    int lineNumber;

    public ParsedFieldImpl(final ReceiptFieldType type, final String value,  final int line){
        this.fieldType=type;
        this.fieldValue=value;
        this.lineNumber=line;
    }
}
