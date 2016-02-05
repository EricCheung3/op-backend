package com.openprice.parser.data;

import com.openprice.parser.ParsedField;
import com.openprice.parser.ReceiptFieldType;

import lombok.Data;

@Data
public class ParsedFieldImpl implements ParsedField{
    ReceiptFieldType fieldType;
    String fieldValue;//parsed value of fieldType
    int lineNumber;//parsed fieldValue at the line number

    public ParsedFieldImpl(final ReceiptFieldType type, final String value,  final int line){
        this.fieldType=type;
        this.fieldValue=value;
        this.lineNumber=line;
    }
}
