package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * "year (4-digit) month day" format
 *  month and day could one or two digits
 *  If day has two digits, it should return two-digit day if it makes sense (between 1-31)
 */
@Slf4j
public class Year4MonthDay implements DateParser{

    private static Pattern patternYear4MonthDay = Pattern.compile(
            "(1\\s*9|2\\s*0)\\s*\\d\\s*\\d\\s*["+DateConstants.DATE_SPLITTERS
            + "]\\s*\\d{1,2}\\s*[" + DateConstants.DATE_SPLITTERS
//            + "]\\s*\\d{1,2}");
    + "]\\s*(0?\\s*\\d|\\d\\s*\\d|\\d\\d)");


    @Override
    public LocalDate parseWithSpaces(final String line) {
        final String y4MD = DateParserUtils.pruneDateStringWithMatch(line, patternYear4MonthDay);
        return parseToDate(y4MD);
    }

    private static LocalDate parseToDate(final String y4MD ) {
        final String[] splits = y4MD.split("[" + DateConstants.DATE_SPLITTERS +"]");
        if(splits.length < 3)
            return null;
        final List<String> clean = DateParserUtils.getMeaningfulDateWords(splits);
        log.debug("clean="+clean);
        return DateUtils.fromDayMonthYear(
                clean.get(2),
                clean.get(1),
                clean.get(0)
                );
    }



}
