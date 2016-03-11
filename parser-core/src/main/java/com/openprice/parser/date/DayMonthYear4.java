package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.parser.date.ml.StringGeneralFeatures;

public class DayMonthYear4 implements DateParser{

  // day (one or two digits), month(one or two digits), 4-digit year
    public static Pattern pattern= Pattern.compile(
            Year4MonthDay.DAY_MONTH_PATTERN +
                    "[" + DateConstants.DATE_SPLITTERS+ "]" +
            Year4MonthDay.DAY_MONTH_PATTERN +
                    "[" + DateConstants.DATE_SPLITTERS + "]" +
            Year4MonthDay.YEAR_4_PATTERN
            );

//    @Override
//    public LocalDate parseNoSpaces(final String line) {
//        final String dateStr=DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line), pattern);
//        return parseToDate(dateStr);
//    }

    @Override
    public LocalDateFeatures parseWithSpaces(final String line) {
        final String dateStr=DateParserUtils.pruneDateStringWithMatch(line, pattern);
        final LocalDate date = parseToDate(dateStr);
        return new LocalDateFeatures(
                date,
                DateStringFormat.DayMonthYear4,
                dateStr,
                StringGeneralFeatures.fromString(dateStr),
                DateStringFeatures.fromString(dateStr)
                );
    }

    private static LocalDate parseToDate(final String dateStr) {
        final String[] mDY2 = dateStr.split("[" + DateConstants.DATE_SPLITTERS +"]");
        if(mDY2.length < 3)
            return null;
        final List<String> cleanWords = DateParserUtils.getMeaningfulDateWords(mDY2);
        return DateUtils.fromDayMonthYear(
                cleanWords.get(0),
                cleanWords.get(1),
                cleanWords.get(2)
                );
    }

}
