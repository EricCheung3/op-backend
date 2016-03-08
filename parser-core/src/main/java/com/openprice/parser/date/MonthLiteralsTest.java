package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class MonthLiteralsTest {

    private final MonthLiterals months = new MonthLiterals();

    @Test
    public void test1(){
        assertEquals("feb", months.mostSimilarMonthLiteral("fee"));
    }

    @Test
    public void junNotJan(){
        assertEquals("jun", months.mostSimilarMonthLiteral("jen"));
    }


}
