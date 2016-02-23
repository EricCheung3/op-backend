package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

public class MonthDayYear2 implements DateParser{

    //month(one or two digits) and day (one or two digits), 2-digit year
    public static Pattern patternMonthDayYear2= Pattern.compile(
//            "([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER
            "\\d{1,2}[" + DateConstants.DATE_SPLITTER
//            + "]([1-9]|0[1-9]|[12][0-9]|3[01])[" + DateConstants.DATE_SPLITTER+"]\\d\\d"
            + "]\\d{1,2}[" + DateConstants.DATE_SPLITTER+"]\\d\\d"
            );

    @Override
    public LocalDate parseNoSpaces(final String line) {
        final String dateStr=DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line), patternMonthDayYear2);
        return parseToDate(dateStr);
    }

    @Override
    public LocalDate parseWithSpaces(String line) {
        final String dateStr=DateParserUtils.pruneDateStringWithMatch(line, patternMonthDayYear2);
        return parseToDate(dateStr);
    }

    private static LocalDate parseToDate(final String dateStr) {
        final String[] mDY2 = dateStr.split("[" + DateConstants.DATE_SPLITTER +"]");
        if(mDY2.length < 3)
            return null;
        return DateUtils.fromDayMonthYear(
                mDY2[1].trim(),
                mDY2[0].trim(),
                "20"+mDY2[2].trim()
                );
    }



}
