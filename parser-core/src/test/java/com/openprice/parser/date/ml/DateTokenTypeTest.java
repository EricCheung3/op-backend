package com.openprice.parser.date.ml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DateTokenTypeTest {

    @Test
    public void test() {
        assertEquals(1, DateTokenType.Day.getIntVal());
        assertEquals(2, DateTokenType.Month.getIntVal());
        assertEquals(3, DateTokenType.Year.getIntVal());
        assertEquals(4, DateTokenType.DayOrMonth.getIntVal());
        assertEquals(5, DateTokenType.DayOrMonthOrYear.getIntVal());
        assertEquals(-1, DateTokenType.Unpredictable.getIntVal());
    }
}
