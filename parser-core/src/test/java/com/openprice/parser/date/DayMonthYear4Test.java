package com.openprice.parser.date;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DayMonthYear4Test {

    @Test
    public void test(){
        assertTrue(DayMonthYear4.pattern.matcher("03/05/2015").matches());
    }

    @Test
    public void testSpacesIsOkay(){
        assertTrue(DayMonthYear4.pattern.matcher("03/ 05/  2015").matches());
    }
}
