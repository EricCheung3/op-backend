package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

/**
 * "year (4-digit) month day" format
 *  month and day could one or two digits
 *  If day has two digits, it should return two-digit day if it makes sense (between 1-31)
 */
public class Year4MonthDay extends DateParserRegularExpression{

    private static Pattern pattern = Pattern.compile(
                YEAR_4_PATTERN
                    + "[" + DateConstants.DATE_SPLITTERS + "]"
                + DAY_MONTH_PATTERN
                    + "[" + DateConstants.DATE_SPLITTERS + "]"
                + DAY_MONTH_PATTERN
        );


    @Override
    public LocalDateFeatures parseWithSpaces(final String line) {
        return selectAccordingToWideSpace(line, getDateSubString(line), DateStringFormat.Year4MonthDay);
    }

    @Override
    public LocalDate parseToDate(final String y4MD ) {
        final String[] splits = y4MD.split("[" + DateConstants.DATE_SPLITTERS +"]");
        if(splits.length < 3)
            return null;
        final List<String> clean = DateParserUtils.getMeaningfulDateWords(splits);
//        log.debug("clean="+clean);
        return DateUtils.fromDayMonthYear(
                clean.get(2),
                clean.get(1),
                clean.get(0)
                );
    }

    @Override
    public String getDateSubString(String line) {
        return DateParserUtils.pruneDateStringWithMatch(line, pattern);
    }



}
