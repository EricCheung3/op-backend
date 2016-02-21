package com.openprice.parser.date;

import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

public class MonthDayYear2 implements DateParser{

    //month(one or two digits) and day (one or two digits), 2-digit year
    private static Pattern patternMonthDayYear2= Pattern.compile("([1-9]|0[1-9]|1[012])["
            + DateConstants.DATE_SPLITTER
            + "]([1-9]|0[1-9]|[12][0-9]|3[01])[" + DateConstants.DATE_SPLITTER+"]\\d\\d");

    @Override
    public String parse(String line) {
        return DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line), patternMonthDayYear2);
    }



}
