package com.openprice.parser.date;

import java.time.LocalDate;

import com.openprice.parser.price.ThreeStrings;

/**
 * all relevant DateParserRegularExpression Test classes inherit this class.
 */
public class DateParserRegularExpressionTestClass {

    private final DateParserRegularExpression parser;

    public DateParserRegularExpressionTestClass(final DateParserRegularExpression parser) {
        this.parser = parser;
    }

    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }

    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line) {
        final LocalDateFeatures features = parser.parseWithSpaces(line);
        if(features == null) {
            throw new RuntimeException("parsed result is null");
        }
        return threeStrings(features.getDate());
    }

}
