package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class DayLiteralMonthYear4Test {

    final DayLiteralMonthYear4 parser = new DayLiteralMonthYear4();

    @Test
    public void dayLiteralMonthYear1(){
        final List<String> words = parser.splitToLiteralMonthDayYear4("9'Feb 2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void dayLiteralMonthYear2(){
        final List<String> words = parser.splitToLiteralMonthDayYear4("19'Feb 2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("19", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void dayLiteralMonthYear2Space(){
        final List<String> words = parser.splitToLiteralMonthDayYear4("19_ Feb 2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("19", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void dayLiteralMonthYear2Space2(){
        final List<String> words = parser.splitToLiteralMonthDayYear4("  1   9_  F eb 2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("19", words.get(1));
        assertEquals("2015", words.get(2));
    }

}
