package com.openprice.parser.data;

import com.openprice.parser.common.StringCommon;

import lombok.Builder;
import lombok.Data;

/**
 * TODO remove it??
 * A String-int class
 */
@Data
@Builder
public class ValueLine {
    private String value;
    private int line;

    public boolean isEmpty(){
        return value.isEmpty() && line<0;
    }

    public static ValueLine defaultValueLine() {
        return ValueLine.builder().value(StringCommon.EMPTY).line(-1).build();
    }
}
