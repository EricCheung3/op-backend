package com.openprice.parser.data;

import com.openprice.parser.common.StringCommon;

import lombok.Builder;
import lombok.Data;

/**
 * Float value plus line number.
 *
 */
@Data
@Builder
public class FloatLine {
    float value=0;//value of the field
    int line=-1;//line number of the field

    public static FloatLine fromValueLine(ValueLine v) throws Exception{
        if(v==null)
            return new FloatLine((float)(-0.0), -1);
        if(v.getValue().isEmpty()){
            return new FloatLine((float)0.0, v.getLine());
        }
        return new FloatLine(Float.valueOf(StringCommon.formatPrice(v.getValue())), v.getLine());
    }
}
