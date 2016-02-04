package com.openprice.parser.data;

import com.openprice.common.StringCommon;

import lombok.Data;

/**
 * TODO remove it??
 * A String-int class
 */
@Data
public class ValueLine {
    private String value=StringCommon.EMPTY;
    private int line=-1;

    public ValueLine(final String value, final int line){
        this.value=value;
        this.line=line;
    }

    public boolean isEmpty(){
        return value.isEmpty() && line<0;
    }

    public static ValueLine defaultValueLine() {
        return new ValueLine(StringCommon.EMPTY, -1);
    }
}
