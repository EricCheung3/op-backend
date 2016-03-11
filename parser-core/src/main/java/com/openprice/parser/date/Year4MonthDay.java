package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.parser.date.ml.StringGeneralFeatures;

import lombok.extern.slf4j.Slf4j;

/**
 * "year (4-digit) month day" format
 *  month and day could one or two digits
 *  If day has two digits, it should return two-digit day if it makes sense (between 1-31)
 */
@Slf4j
public class Year4MonthDay implements DateParser{

    //pattern for month and day
//    public final static String DAY_MONTH_PATTERN = "\\s*(0?\\s*\\d|\\d\\s*\\d|\\d{1,2})\\s*";
    public final static String DAY_MONTH_PATTERN = "\\s*(\\d\\s*\\d|\\d{1,2})\\s*";
    public final static String YEAR_4_PATTERN = "\\s*(1\\s*9|2\\s*0)\\s*\\d\\s*\\d\\s*";

    private static Pattern patternYear4MonthDay = Pattern.compile(
                YEAR_4_PATTERN
                    + "[" + DateConstants.DATE_SPLITTERS + "]"
                + DAY_MONTH_PATTERN
                    + "[" + DateConstants.DATE_SPLITTERS + "]"
                + DAY_MONTH_PATTERN
        );


    @Override
    public LocalDateFeatures parseWithSpaces(final String line) {
        final String y4MD = DateParserUtils.pruneDateStringWithMatch(line, patternYear4MonthDay);
        log.debug("y4MD="+y4MD);
        final LocalDate localDate = parseToDate(y4MD);
        return new LocalDateFeatures(
                localDate,
                DateStringFormat.Year4MonthDay,
                y4MD,
                StringGeneralFeatures.fromString(y4MD),
                DateStringFeatures.fromString(y4MD));
    }

    private static LocalDate parseToDate(final String y4MD ) {
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



}
