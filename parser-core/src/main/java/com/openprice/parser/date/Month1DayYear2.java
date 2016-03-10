package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * this is for sears, where date string is messed in a line of numbers
 */
@Slf4j
public class Month1DayYear2 implements DateParser{

    //month(one digit) and day (one or two digits), 2-digit year
    @Getter
    private static Pattern patternMonthDayYear2= Pattern.compile(
            "\\s*\\d{1}\\s*[" + DateConstants.DATE_SPLITTERS
            + "]\\s*(\\d|\\d\\s*\\d|\\d{2})\\s*[" + DateConstants.DATE_SPLITTERS+"]\\s*\\d\\s*\\d\\s*"
            );

    @Override
    public LocalDate parseWithSpaces(final String line) {
        final String dateStr  = DateParserUtils.pruneDateStringWithMatch(line, patternMonthDayYear2);
        log.debug("dateStr="+dateStr);
        return parseToLocalDate(dateStr);
    }

    private static LocalDate parseToLocalDate(final String dateStr){
        final String[] mDY2 = dateStr.split("[" + DateConstants.DATE_SPLITTERS +"]");
        if(mDY2.length < 3)
            return null;
        final List<String> clean = DateParserUtils.getMeaningfulDateWords(mDY2);
        log.debug("clean="+clean);
        return DateUtils.fromDayMonthYear(
                clean.get(1),
                clean.get(0),
                "20" + clean.get(2)
                );
    }
}
