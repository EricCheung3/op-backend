package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

import lombok.extern.slf4j.Slf4j;

/**
 * "year (4-digit) month day" format
 *  month and day could one or two digits
 *  If day has two digits, it should return two-digit day if it makes sense (between 1-31)
 */

@Slf4j
public class Year4MonthDay implements DateParser{

    private static Pattern patternYear4MonthDay = Pattern.compile(
            "(19|20)\\d\\d["+DateConstants.DATE_SPLITTER
//            + "]([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER
            + "]\\d{1,2}[" + DateConstants.DATE_SPLITTER
//            + "]([1-9]|0[1-9]|[12][0-9]|3[01])");
            + "]\\d{1,2}");


    @Override
    public LocalDate parseNoSpaces(final String line) {
        final String y4MD = DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line), patternYear4MonthDay);
        return parseToDate(y4MD);
    }

    @Override
    public LocalDate parseWithSpaces(final String line) {
        final String y4MD = DateParserUtils.pruneDateStringWithMatch(line, patternYear4MonthDay);
        return parseToDate(y4MD);
    }

    private static LocalDate parseToDate(final String y4MD ) {
        final String[] splits = y4MD.split("[" + DateConstants.DATE_SPLITTER +"]");
        if(splits.length < 3)
            return null;
        return DateUtils.fromDayMonthYear(
                splits[2],
                splits[1].trim(),
                splits[0].trim()
                );
    }



}
