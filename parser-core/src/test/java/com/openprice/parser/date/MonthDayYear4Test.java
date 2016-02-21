package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MonthDayYear4Test {

    private final MonthDayYear4 mdy4 = new MonthDayYear4();

    @Test
    public void day2Month2IsOkay(){
        assertEquals("05/08/2014", mdy4.parse("05/08/2014 fdafda d"));
    }

    @Test
    public void day1Month2IsOkay(){
        assertEquals("5/08/2014", mdy4.parse("5/08/2014 fdafda d"));
    }

    @Test
    public void day1Month1IsOkay(){
        assertEquals("5/8/2014", mdy4.parse("sdfsd 5/8/2014 fdafda d"));
    }

    @Test
    public void day1Month1IsOkaySpaceIsAlsoFine(){
        assertEquals("5/8/2014", mdy4.parse("sdfsd 5 /   8/2 014 fdafda d"));
    }
}
