package com.openprice.parser.date;

import java.util.Calendar;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Year2MonthDay implements DateParser{

    private static Pattern patternYear2MonthDay= Pattern.compile(
            "\\d\\d[" + DateConstants.DATE_SPLITTER
            + "]([1-9]|0[1-9]|1[012])[" + DateConstants.DATE_SPLITTER
//            + "]([1-9]|0[1-9]|[12][0-9]|3[01])"
            + "](\\d{1,2})"
            );

    @Override
    public Calendar parse(String line) {
        final String dateString = DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line),
                patternYear2MonthDay);
        log.debug("dateString=" + dateString);
        final String[] y2md = dateString.split("["+ DateConstants.DATE_SPLITTER +"]");

        return DateParserUtils.getCalendar(y2md[2].trim(), y2md[1].trim(), "20"+y2md[0].trim());
    }

}
