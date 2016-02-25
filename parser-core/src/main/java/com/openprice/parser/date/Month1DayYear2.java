package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

/**
 * this is for sears, where date string is messed in a line of numbers
 */
public class Month1DayYear2 implements DateParser{

    //month(one digit) and day (one or two digits), 2-digit year
    private static Pattern patternMonthDayYear2= Pattern.compile(
//            "([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER
            "\\d{1}[" + DateConstants.DATE_SPLITTERS
//            + "]([1-9]|0[1-9]|[12][0-9]|3[01])[" + DateConstants.DATE_SPLITTER+"]\\d\\d"
            + "]\\d{1,2}[" + DateConstants.DATE_SPLITTERS+"]\\d\\d"
            );

    @Override
    public LocalDate parseWithSpaces(final String line) {
        final String dateStr  = DateParserUtils.pruneDateStringWithMatch(line, patternMonthDayYear2);
        return parseToLocalDate(dateStr);
    }

    @Override
    public LocalDate parseNoSpaces(final String line) {
        final String dateStr  = DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line), patternMonthDayYear2);
        return parseToLocalDate(dateStr);
    }

    private static LocalDate parseToLocalDate(final String dateStr){
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
