package com.openprice.parser.date;

import java.util.regex.Pattern;

/**
 * 2014-Feb-12
 */
public class YearLiteralMonthDay implements DateParser{

    private static Pattern pattern = Pattern.compile(
            DateParserRegularExpression.YEAR_4_PATTERN +
                LiteralMonthDayYear2.SPLITTER +
            LiteralMonthDayYear2.LITERAL_MONTH +
                LiteralMonthDayYear2.SPLITTER +
            DateParserRegularExpression.DAY_MONTH_PATTERN
            );

    @Override
    public LocalDateFeatures parseWithSpaces(String line) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDateSubString(String line) {
        // TODO Auto-generated method stub
        return null;
    }


}
