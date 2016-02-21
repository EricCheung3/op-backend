package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Year4MonthDayTest {

    final private Year4MonthDay y4MD = new Year4MonthDay();

    @Test
    public void patternY4MD2Test1()throws Exception{
        assertEquals("2014/5/01", y4MD.parse("2014/5/01"));
    }

    @Test
    public void patternY4MD2Test2()throws Exception{
        assertEquals("2014/05/01", y4MD.parse("2014/05/01"));
    }

    @Test
    public void patternY4MD2TestDayHasOneDigits()throws Exception{
        assertEquals("2014/05/1", y4MD.parse("2014/05/1"));
    }

    @Test
    public void patternY4MD2TestDayHasOneDigitsSpaceIsOkay()throws Exception{
        assertEquals("2014/05/1", y4MD.parse("201  4/0 5/ 1    "));
    }

}
