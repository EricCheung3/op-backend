package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonthDayYear2 extends MonthDayYear{

    //month(one or two digits) and day (one or two digits), 2-digit year
    public static Pattern patternMonthDayYear2= Pattern.compile(
            Year4MonthDay.DAY_MONTH_PATTERN
                + "["+ DateConstants.DATE_SPLITTERS + "]" +
            Year4MonthDay.DAY_MONTH_PATTERN
                + "["+ DateConstants.DATE_SPLITTERS +"]" +
            Year2MonthDay.YEAR_2_PATTERN
            );

    @Override
    public LocalDateFeatures parseWithSpaces(String line) {
        return selectAccordingToWideSpace(line, patternMonthDayYear2, DateStringFormat.MonthDayYear2);
    }

    @Override
    public LocalDate parseToDate(final String dateStr) {
        log.debug("dateStr="+dateStr);
        final String[] mDY2 = dateStr.split("[" + DateConstants.DATE_SPLITTERS +"]");
        if(mDY2.length < 3)
            return null;
        final List<String> clean = DateParserUtils.getMeaningfulDateWords(mDY2);
        return DateUtils.fromDayMonthYear(
                clean.get(1).trim(),
                clean.get(0).trim(),
                "20" + clean.get(2).trim()
                );
    }

}
