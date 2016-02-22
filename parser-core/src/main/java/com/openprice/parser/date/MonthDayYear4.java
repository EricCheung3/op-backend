package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

/**
 * "month day year (4-digit)" format
 *  month and day could one or two digits
 */
public class MonthDayYear4 implements DateParser{

    private static Pattern patternMonthDayYear4 = Pattern.compile(
//            "([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER +
            "\\d{1,2}[" + DateConstants.DATE_SPLITTER +
//            "]([1-9]|0[1-9]|[12][0-9]|3[01])[" + DateConstants.DATE_SPLITTER +
            "]\\d{1,2}[" + DateConstants.DATE_SPLITTER +
            "](19|20)\\d\\d");

    @Override
    public LocalDate parse(final String line) {
        final String mdy4 = DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line),
                patternMonthDayYear4);
        final String[] mdy4Splits = mdy4.split("[" + DateConstants.DATE_SPLITTER +"]");
        if(mdy4Splits.length < 3)
            return null;
        return DateUtils.fromDayMonthYear(
                mdy4Splits[1],
                mdy4Splits[0],
                mdy4Splits[2]);
    }


}
