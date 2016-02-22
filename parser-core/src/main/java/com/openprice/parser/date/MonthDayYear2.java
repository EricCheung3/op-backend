package com.openprice.parser.date;

import java.util.Calendar;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

public class MonthDayYear2 implements DateParser{

    //month(one or two digits) and day (one or two digits), 2-digit year
    private static Pattern patternMonthDayYear2= Pattern.compile(
//            "([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER
            "\\d{1,2}[" + DateConstants.DATE_SPLITTER
//            + "]([1-9]|0[1-9]|[12][0-9]|3[01])[" + DateConstants.DATE_SPLITTER+"]\\d\\d"
            + "]\\d{1,2}[" + DateConstants.DATE_SPLITTER+"]\\d\\d"
            );

    @Override
    public Calendar parse(String line) {
        final String dateStr = DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line), patternMonthDayYear2);
        final String[] mDY2 = dateStr.split("[" + DateConstants.DATE_SPLITTER +"]");
        if(mDY2.length < 3)
            return null;
        return DateParserUtils.getCalendar(mDY2[1].trim(), mDY2[0].trim(), "20"+mDY2[2].trim());
    }



}
