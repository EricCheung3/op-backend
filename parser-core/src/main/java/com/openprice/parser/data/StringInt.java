package com.openprice.parser.data;

import com.openprice.common.StringCommon;

import lombok.Data;

/**
 * TODO remove it??
 * A String-int class
 */
@Data
public class StringInt {

    private static int DEFAULT_LINE=-1;

    private String value=StringCommon.EMPTY;
    private int line=DEFAULT_LINE;

    public StringInt(final String value, final int line){
        this.value=value;
        if(line<0)
            this.line=DEFAULT_LINE;
        else
            this.line=line;
    }

    public boolean isEmpty(){
        return value.isEmpty() && line<0;
    }

    public static StringInt emptyValue() {
        return new StringInt(StringCommon.EMPTY, DEFAULT_LINE);
    }
}
