package com.openprice.parser.date;

import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

public abstract class LiteralMonthDateParser extends DateParserRegularExpression {

    //format like "Feb 9, 2015"
    private final Pattern pattern;
    private final DateStringFormat format;

    public LiteralMonthDateParser(final Pattern pattern, final DateStringFormat format) {
        this.pattern = pattern;
        this.format = format;
    }

    @Override
    public LocalDateFeatures parseWithSpaces(String origLine) {
        final String nonSpaceLower = StringCommon.removeAllSpaces(origLine).toLowerCase();
        if(MONTH_LITERALS.monthLiterals().stream().anyMatch(m -> nonSpaceLower.contains(m.toLowerCase())))
            return selectAccordingToWideSpace(origLine, getDateSubString(origLine), format);
        return null;
    }

    @Override
    public String getDateSubString(String line) {
        return DateParserUtils.pruneDateStringWithMatch(line, pattern);
    }

}
