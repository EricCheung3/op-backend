package com.openprice.parser.data;

import lombok.Value;

@Value
public class StringDoubleInt {

    String str;
    double value;
    int lineNumber;

    public StringDoubleInt(final String str, final double value, final int line){
        this.str=str;
        this.value=value;
        this.lineNumber=line;
    }

}
