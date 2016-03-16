package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class DayLiteralMonthYear4Test extends DateParserRegularExpressionTestClass{

    private static final DayLiteralMonthYear4 DMY_PARSER= new DayLiteralMonthYear4();

    public DayLiteralMonthYear4Test() {
        super(DMY_PARSER);
    }

    @Test
    public void isLiteralMonthFormat1() {
        assertTrue(DMY_PARSER.isLiteralMonthFormat("9'Feb 2015"));
        assertTrue(DMY_PARSER.isLiteralMonthFormat("19'Feb 2015"));
        assertTrue(DMY_PARSER.isLiteralMonthFormat("191'Feb 2015"));
    }


    @Test
    public void dayLiteralMonthYear1(){
        final List<String> words = DMY_PARSER.splitToLiteralMonthDayYear4("9'Feb 2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void dayLiteralMonthYear2(){
        final List<String> words = DMY_PARSER.splitToLiteralMonthDayYear4("19'Feb 2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("19", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void dayLiteralMonthYear2Space(){
        final List<String> words = DMY_PARSER.splitToLiteralMonthDayYear4("19_ Feb 2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("19", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void dayLiteralMonthYear2Space2(){
        final List<String> words = DMY_PARSER.splitToLiteralMonthDayYear4("  1   9_  F eb 2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("19", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test(expected=Exception.class)
    public void soSlowForThisLineInAReceipt() throws Exception{
        final String line = "ReaPrice    4    9                                                                                                        ";
        parseToThreeStrings(line);
    }

    @Test
    public void test1() throws Exception{
        final String line = "DATE/TIME:                      17-Jan-2015 13:50:35 ";
        assertEquals(threeStrings(2015,1,17), parseToThreeStrings(line));
    }

}
