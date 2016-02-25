package com.openprice.parser.store;

import java.util.List;

import com.openprice.common.StringCommon;

public class GasStationCommon {


    public static String parseUnitPrice(final String line, final List<String> headers){
        for(String h: headers){
            final String price = FieldParserCommon.getValueAtTail(line, h, 0.65);
            System.out.println("price="+price);
            try{
                return Double.valueOf(StringCommon.formatPrice(price)) +"";
            }catch(Exception e){
                continue;
            }
            //return the one that has the most digits?
            //final int[] digitsChars = StringCommon.countDigitAndChars(price);
        }
        return StringCommon.EMPTY;
    }
}
