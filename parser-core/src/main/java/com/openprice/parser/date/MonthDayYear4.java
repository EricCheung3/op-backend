package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.parser.date.ml.StringGeneralFeatures;

/**
 * "month day year (4-digit)" format
 *  month and day could one or two digits
 */
public class MonthDayYear4 implements DateParser{

    private static Pattern patternMonthDayYear4 = Pattern.compile(
            Year4MonthDay.DAY_MONTH_PATTERN
                + "["+ DateConstants.DATE_SPLITTERS + "]"
            + Year4MonthDay.DAY_MONTH_PATTERN
                + "["+ DateConstants.DATE_SPLITTERS + "]"
            +Year4MonthDay.YEAR_4_PATTERN);

    @Override
    public LocalDateFeatures parseWithSpaces(String line) {
        final String mdy4 = DateParserUtils.pruneDateStringWithMatch(line, patternMonthDayYear4);
        final LocalDate localDate = parseToDate(mdy4);
        return new LocalDateFeatures(
                localDate,
                DateStringFormat.MonthDayYear4,
                mdy4,
                StringGeneralFeatures.fromString(mdy4),
                DateStringFeatures.fromString(mdy4));
    }

    private static LocalDate parseToDate(final String mdy4) {
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
