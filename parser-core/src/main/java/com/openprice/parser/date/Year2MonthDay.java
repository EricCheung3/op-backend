package com.openprice.parser.date;

import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

public class Year2MonthDay implements DateParser{

    private static Pattern patternYear2MonthDay= Pattern.compile("\\d\\d["
            + DateConstants.DATE_SPLITTER
            + "]([1-9]|0[1-9]|1[012])["
            + DateConstants.DATE_SPLITTER
            + "]([1-9]|0[1-9]|[12][0-9]|3[01])");

    @Override
    public String parse(String line) {
        return DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line),
                patternYear2MonthDay);
    }

}
