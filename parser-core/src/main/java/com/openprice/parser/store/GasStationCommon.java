package com.openprice.parser.store;

import java.util.List;

import com.openprice.common.StringCommon;
import com.openprice.parser.api.ReceiptLine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GasStationCommon {

    public static String parseTotalSoldLiters(final ReceiptLine line) {
        final String lineString = line.getCleanText();
        log.debug("lineString="+lineString);
        try{
            int last = StringCommon.lastLetter(lineString);
            log.debug("last="+last);
            if (last == -1) {
                log.warn("line '{}' did not find a letter?", lineString);
                return "";
            }
            final String doubleString=StringCommon.getOnlyDigitsDots(lineString.substring(last + 1));
            log.debug("doubleString is "+doubleString);
            return Double.valueOf(doubleString)+ StringCommon.EMPTY;
        }catch(Exception e){
            log.warn(e.getMessage());
            return lineString;
        }
    }

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
