package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class LiteralMonthParserTest {

    @Test
    public void splitToLiteralMonthDayYear2OrYear4Test1(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("Feb 9  , 2015", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void mergeTheLastTwoDigits(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("Feb 9, 1 5", 2);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("15", words.get(2));
    }

    @Test
    public void splitToLiteralMonthDayYear2OrYear4Test2(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("Feb 9,2015", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void splitToLiteralMonthDayYear2OrYear4CommaSpacesIsOkay(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("Feb 9,    2015", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearUpperCaseIsOkay(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("FEB 9,    2015", 4);
        assertEquals(3, words.size());
        assertEquals("FEB", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }


    @Test
    public void splitToLiteralMonthDayYear2OrYear4CommaSpacesIsOkay2(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("Feb  9  ,    2015", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void splitToLiteralMonthDayYear2OrYear4DashSpacesIsOkay(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("Feb 9-    2015", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void splitToLiteralMonthDayYear2OrYear4UpperIsOkay(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("FEB 9-    2015", 4);
        assertEquals(3, words.size());
        assertEquals("FEB", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void splitToLiteralMonthDayYear2OrYear4DashSpacesIsOkay2(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("Feb 9   -       2015", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void splitToLiteralMonthDayYear2OrYear4UnderscoreSpacesIsOkay(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("Feb 9 _    2015", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void splitToLiteralMonthDayYear2OrYear4UnderscoreSpacesIsOkay2(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("Feb 9_    2015", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void singleQuoteNonUnicodeIsOkay(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("OCT.08’2015", 4);
        assertEquals(3, words.size());
        assertEquals("OCT", words.get(0));
        assertEquals("08", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void singleQuoteNonUnicodeIsOkay2(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("OCT.8’2015", 4);
        assertEquals(3, words.size());
        assertEquals("OCT", words.get(0));
        assertEquals("8", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void singleQuoteIsOkay(){
        final List<String> words = LiteralMonthParser.formatMonthDayYearToList("OCT.08'2015", 4);
        assertEquals(3, words.size());
        assertEquals("OCT", words.get(0));
        assertEquals("08", words.get(1));
        assertEquals("2015", words.get(2));
    }

}
