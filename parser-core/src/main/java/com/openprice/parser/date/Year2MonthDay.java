package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Year2MonthDay implements DateParser{

    private static Pattern patternYear2MonthDay= Pattern.compile(
            "\\d\\d[" + DateConstants.DATE_SPLITTER
//            + "]([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER
            + "]\\d{1,2}[" + DateConstants.DATE_SPLITTER
//            + "]([1-9]|0[1-9]|[12][0-9]|3[01])"
            + "](\\d{1,2})"
            );

    @Override
    public LocalDate parseNoSpaces(final String line) {
        final String dateString = DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line), patternYear2MonthDay);
        log.debug("dateString=" + dateString);
        return parseToDate(dateString);
    }

    @Override
    public LocalDate parseWithSpaces(String line) {
        final String dateString = DateParserUtils.pruneDateStringWithMatch(line, patternYear2MonthDay);
        log.debug("dateString=" + dateString);
        return parseToDate(dateString);
    }

    private static LocalDate parseToDate(final String dateStr) {
        final String[] y2md = dateStr.split("["+ DateConstants.DATE_SPLITTER +"]");
        if(y2md.length < 3)
            return null;
        return DateUtils.fromDayMonthYear(
                y2md[2],
                y2md[1],
                "20"+y2md[0]);
    }

}
