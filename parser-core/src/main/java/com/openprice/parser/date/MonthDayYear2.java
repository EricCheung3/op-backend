package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

public class MonthDayYear2 implements DateParser{

    //month(one or two digits) and day (one or two digits), 2-digit year
    public static Pattern patternMonthDayYear2= Pattern.compile(
//            "([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER
            "\\d{1,2}[" + DateConstants.DATE_SPLITTERS
//            + "]([1-9]|0[1-9]|[12][0-9]|3[01])[" + DateConstants.DATE_SPLITTER+"]\\d\\d"
            + "]\\d{1,2}[" + DateConstants.DATE_SPLITTERS+"]\\d\\d"
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
        final String[] mDY2 = dateStr.split("[" + DateConstants.DATE_SPLITTERS +"]");
        if(mDY2.length < 3)
            return null;
        final List<String> clean = DateParserUtils.getMeaningfulDateWords(mDY2);
        return DateUtils.fromDayMonthYear(
                clean.get(1),
                clean.get(0),
                "20" + clean.get(2)
                );
    }



}