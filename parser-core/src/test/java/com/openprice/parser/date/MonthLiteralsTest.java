package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.common.StringCommon;


public class MonthLiteralsTest {

    private static final MonthLiterals MONTH_LITERALS = new MonthLiterals();

    @Test
    public void test1(){
        assertEquals("feb", MONTH_LITERALS.mostSimilarMonthLiteral("fee"));
    }

    @Test
    public void junNotJan(){
        assertEquals("jun", MONTH_LITERALS.mostSimilarMonthLiteral("jen"));
    }

    @Test
    public void notResult(){
        assertEquals(StringCommon.EMPTY, MONTH_LITERALS.mostSimilarMonthLiteral("afafdafds"));
    }
}
