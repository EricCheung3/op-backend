package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.junit.Test;


public class DayMonthYear2Test {


    @Test
    public void testPatternShouldMatch1(){
        final String str="25/01/15";
        assertTrue(DayMonthYear2.pattern.matcher(str).matches());
    }

    @Test
    public void testPatternShouldMatch(){
        final String str="7117 00058 77840     DATE: 25/01/15 TIME: 11:30 AM";
        final Matcher match=DayMonthYear2.pattern.matcher(str);
        final List<String> allMatches=new ArrayList<>();
        while(match.find()){
            allMatches.add(match.group());
        }
        assertEquals(1, allMatches.size());
        assertEquals("25/01/15", allMatches.get(0));
    }

}
