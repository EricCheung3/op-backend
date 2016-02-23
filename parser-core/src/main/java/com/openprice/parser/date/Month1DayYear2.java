package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

/**
 * this is for sears, where date string is messed in a line of numbers
 */
public class Month1DayYear2 implements DateParser{

    //month(one digit) and day (one or two digits), 2-digit year
    private static Pattern patternMonthDayYear2= Pattern.compile(
//            "([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER
            "\\d{1}[" + DateConstants.DATE_SPLITTER
//            + "]([1-9]|0[1-9]|[12][0-9]|3[01])[" + DateConstants.DATE_SPLITTER+"]\\d\\d"
            + "]\\d{1,2}[" + DateConstants.DATE_SPLITTER+"]\\d\\d"
            );

    @Override
    public LocalDate parse(final String line, final boolean removeSpace) {
        String dateStr;
        if(removeSpace)
            dateStr  = DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line), patternMonthDayYear2);
        else
            dateStr  = DateParserUtils.pruneDateStringWithMatch(line, patternMonthDayYear2);
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
