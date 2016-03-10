package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Year2MonthDay implements DateParser{

    private static Pattern patternYear2MonthDay= Pattern.compile(
            "\\d\\d[" + DateConstants.DATE_SPLITTERS
//            + "]([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER
            + "]\\d{1,2}[" + DateConstants.DATE_SPLITTERS
//            + "]([1-9]|0[1-9]|[12][0-9]|3[01])"
            + "](\\d{1,2})"
            );


    @Override
    public LocalDate parseWithSpaces(String line) {
        final String dateString = DateParserUtils.pruneDateStringWithMatch(line, patternYear2MonthDay);
        log.debug("dateString=" + dateString);
        return parseToDate(dateString);
    }

    private static LocalDate parseToDate(final String dateStr) {
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
