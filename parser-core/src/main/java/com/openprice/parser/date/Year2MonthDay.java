package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class Year2MonthDay extends DateParserRegularExpression{

    private static Pattern pattern= Pattern.compile(
            YEAR_2_PATTERN
                 + "[" + DateConstants.DATE_SPLITTERS + "]" +
            Year4MonthDay.DAY_MONTH_PATTERN
                +"[" + DateConstants.DATE_SPLITTERS + "]" +
            Year4MonthDay.DAY_MONTH_PATTERN
            );


    @Override
    public LocalDateFeatures parseWithSpaces(String line) {
        return selectAccordingToWideSpace(line, getDateSubString(line), DateStringFormat.Year2MonthDay);
    }

    @Override
    public LocalDate parseToDate(final String dateStr) {
        final String[] y2md = dateStr.split("["+ DateConstants.DATE_SPLITTERS +"]");
        if(y2md.length < 3)
            return null;
        final List<String> clean = DateParserUtils.getMeaningfulDateWords(y2md);
        return DateUtils.fromDayMonthYear(
                clean.get(2),
                clean.get(1),
                "20" + clean.get(0));
    }

    @Override
    public String getDateSubString(String line) {
        return DateParserUtils.pruneDateStringWithMatch(line, pattern);
    }

}
