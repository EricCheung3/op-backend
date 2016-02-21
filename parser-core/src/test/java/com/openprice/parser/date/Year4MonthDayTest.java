package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class Year4MonthDayTest {

    final private Year4MonthDay y4MD = new Year4MonthDay();

    @Test
    public void patternY4MD2Test1()throws Exception{
        final Calendar cal = y4MD.parse("2014/5/01");
        final int[] ymd = DateParserUtils.getYearMonthDay(cal);
        assertEquals(2014, ymd[0]);
        assertEquals(5, ymd[1]);
        assertEquals(1, ymd[2]);
    }

    @Test
    public void patternY4MD2Test2()throws Exception{
        final Calendar cal = y4MD.parse("2014/05/01");
        final int[] ymd = DateParserUtils.getYearMonthDay(cal);
        assertEquals(2014, ymd[0]);
        assertEquals(5, ymd[1]);
        assertEquals(1, ymd[2]);
    }

    @Test
    public void patternY4MD2TestDayHasOneDigits()throws Exception{
        final Calendar cal = y4MD.parse("2014/5/1");
        final int[] ymd = DateParserUtils.getYearMonthDay(cal);
        assertEquals(2014, ymd[0]);
        assertEquals(5, ymd[1]);
        assertEquals(1, ymd[2]);
    }

    @Test
    public void patternY4MD2TestDayHasOneDigitsSpaceIsOkay()throws Exception{
        final Calendar cal = y4MD.parse("afdf 2 014  /5  / 01  dsfasf");
        final int[] ymd = DateParserUtils.getYearMonthDay(cal);
        assertEquals(2014, ymd[0]);
        assertEquals(5, ymd[1]);
        assertEquals(1, ymd[2]);
    }

}
