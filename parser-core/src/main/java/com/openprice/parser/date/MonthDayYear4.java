package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

/**
 * "month day year (4-digit)" format
 *  month and day could one or two digits
 */
public class MonthDayYear4 extends DateParserFromSpaces{

    private static Pattern patternMonthDayYear4 = Pattern.compile(
            Year4MonthDay.DAY_MONTH_PATTERN
                + "["+ DateConstants.DATE_SPLITTERS + "]"
            + Year4MonthDay.DAY_MONTH_PATTERN
                + "["+ DateConstants.DATE_SPLITTERS + "]"
            +Year4MonthDay.YEAR_4_PATTERN);

    @Override
    public LocalDateFeatures parseWithSpaces(String line) {
        return selectAccordingToWideSpace(line, patternMonthDayYear4, DateStringFormat.MonthDayYear4);
    }

    @Override
    public LocalDate parseToDate(final String mdy4) {
        final String[] mdy4Splits = mdy4.split("[" + DateConstants.DATE_SPLITTERS +"]");
        if(mdy4Splits.length < 3)
            return null;
        final List<String> clean = DateParserUtils.getMeaningfulDateWords(mdy4Splits);
        return DateUtils.fromDayMonthYear(
                clean.get(1),
                clean.get(0),
                clean.get(2));
    }

}
