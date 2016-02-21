package com.openprice.parser.date;

import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

/**
 * "year (4-digit) month day" format
 *  month and day could one or two digits
 *  If day has two digits, it should return two-digit day if it makes sense (between 1-31)
 */

public class Year4MonthDay implements DateParser{

    private static Pattern patternYear4MonthDay = Pattern.compile("(19|20)\\d\\d["+DateConstants.DATE_SPLITTER
            + "]([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER
            + "]([1-9]|0[1-9]|[12][0-9]|3[01])");


    @Override
    public String parse(String line) {
        return DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line), patternYear4MonthDay);
    }


}
