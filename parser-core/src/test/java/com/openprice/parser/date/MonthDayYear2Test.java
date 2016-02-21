package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MonthDayYear2Test {

    private final MonthDayYear2 mdy2 = new MonthDayYear2();

    @Test
    public void patternMDY2Test1Dayhas1Digit() throws Exception{
        assertEquals("5/12/14", mdy2.parse("5/12/14a b ce"));
    }

    @Test
    public void patternMDY2TestDayHasTwoDigits() throws Exception{
        assertEquals("05/12/14", mdy2.parse("05/12/14a b ce"));
    }

    @Test
    public void patternMDY2TestDayHasTwoDigitsSpaceIsOkay() throws Exception{
        assertEquals("05/12/14", mdy2.parse("0 5/1   2  /1        4  a b ce"));
    }

}
