package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Year2MonthDayTest {

    private final Year2MonthDay y2md = new Year2MonthDay();

    @Test
    public void test1(){
        assertEquals("15/03/14", y2md.parse("sdfa 15/03/14"));

    }
}
