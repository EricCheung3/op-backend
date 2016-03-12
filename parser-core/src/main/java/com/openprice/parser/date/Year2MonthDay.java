package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Year2MonthDay extends DateParserFromSpaces{

    public final static String YEAR_2_PATTERN = "\\s*\\d\\s*\\d\\s*";//"\\d\\d";

    private static Pattern patternYear2MonthDay= Pattern.compile(
            YEAR_2_PATTERN
                 + "[" + DateConstants.DATE_SPLITTERS + "]" +
            Year4MonthDay.DAY_MONTH_PATTERN
                +"[" + DateConstants.DATE_SPLITTERS + "]" +
            Year4MonthDay.DAY_MONTH_PATTERN
            );


    @Override
    public LocalDateFeatures parseWithSpaces(String line) {
        return selectAccordingToWideSpace(line, patternYear2MonthDay, DateStringFormat.Year2MonthDay);
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

}
