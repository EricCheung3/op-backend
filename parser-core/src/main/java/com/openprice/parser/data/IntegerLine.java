package com.openprice.parser.data;

import lombok.Builder;
import lombok.Data;

/**
 * Integer value plus line number.
 */
@Data
@Builder
public class IntegerLine{
    int value=0;//value of the field
    int line=-1;//line number of the field

    public static IntegerLine fromValueLine(ValueLine v) throws NumberFormatException{
        if(v==null)
            return new IntegerLine(-1, -1);
        if(v.getValue().isEmpty())
            return new IntegerLine(-1, v.getLine());
        return new IntegerLine(Integer.valueOf(v.getValue()), v.getLine());
    }
}
