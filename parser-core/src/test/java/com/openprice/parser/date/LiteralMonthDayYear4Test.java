package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LiteralMonthDayYear4Test extends DateParserRegularExpressionTestClass {

    public LiteralMonthDayYear4Test() {
        super(new LiteralMonthDayYear4());
    }

    @Test
    public void patternlitermonthdayyeartest1() throws Exception{
        final ThreeStrings truth2015Jan19 = threeStrings(2015, 1, 19);
        assertEquals(truth2015Jan19, parseToThreeStrings("Jan 19, 2015  sfdgsd "));
        assertEquals(truth2015Jan19, parseToThreeStrings("Jan 19 2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("Jan 19,2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("Jan 19, 2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("Jan 19,   2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("Jan 19.   2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("Jan 19_   2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("Jan  19,   2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("Jan  19 ,   2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("January 19 2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("January 19,2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("January 19, 2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("January 19,   2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("January 19.   2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("January 19_   2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("January  19,   2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("January  19 ,   2015"));

        assertEquals(truth2015Jan19, parseToThreeStrings("JAN  19 ,   2015"));
        assertEquals(truth2015Jan19, parseToThreeStrings("JANUARY 19 2015"));

        final ThreeStrings truth2015Feb19 = threeStrings(2015, 2, 19);
        assertEquals(truth2015Feb19, parseToThreeStrings("Feb 19, 2015  sfdgsd "));
        assertEquals(truth2015Feb19, parseToThreeStrings("Feb 19 2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("Feb 19,2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("Feb 19, 2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("Feb 19,   2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("Feb 19.   2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("Feb 19_   2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("Feb  19,   2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("Feb  19 ,   2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("February 19 2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("February 19,2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("February 19, 2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("February 19,   2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("February 19.   2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("February 19_   2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("February  19,   2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("February  19 ,   2015"));

        assertEquals(truth2015Feb19, parseToThreeStrings("FEB  19 ,   2015"));
        assertEquals(truth2015Feb19, parseToThreeStrings("FEBRUARY 19 2015"));

        final ThreeStrings truth2015March19 = threeStrings(2015, 3, 19);
        assertEquals(truth2015March19, parseToThreeStrings("March 19, 2015  sfdgsd "));
        assertEquals(truth2015March19, parseToThreeStrings("March 19, 2015  sfdgsd "));
        assertEquals(truth2015March19, parseToThreeStrings("MAR 19, 2015  sfdgsd "));
        assertEquals(truth2015March19, parseToThreeStrings("MARCH 19, 2015  sfdgsd "));

        final ThreeStrings april19 = threeStrings(2015, 4, 19);
        assertEquals(april19, parseToThreeStrings("April 19, 2015  sfdgsd "));
        assertEquals(april19, parseToThreeStrings("Apr 19, 2015  sfdgsd "));
        assertEquals(april19, parseToThreeStrings("APRIL 19, 2015  sfdgsd "));
        assertEquals(april19, parseToThreeStrings("APRIL 19, 2015  sfdgsd "));

        final ThreeStrings may19 = threeStrings(2015, 5, 19);
        assertEquals(may19, parseToThreeStrings("May 19, 2015  sfdgsd "));
        assertEquals(may19, parseToThreeStrings("May 19, 2015  sfdgsd "));
        assertEquals(may19, parseToThreeStrings("MAY 19, 2015  sfdgsd "));

        final ThreeStrings june19 = threeStrings(2015, 6, 19);
        assertEquals(june19, parseToThreeStrings("June 19, 2015  sfdgsd "));
        assertEquals(june19, parseToThreeStrings("Jun 19, 2015  sfdgsd "));
        assertEquals(june19, parseToThreeStrings("JUN 19, 2015  sfdgsd "));
        assertEquals(june19, parseToThreeStrings("JUNE 19, 2015  sfdgsd "));

        final ThreeStrings july19 = threeStrings(2015, 7, 19);
        assertEquals(july19, parseToThreeStrings("July 19, 2015  sfdgsd "));
        assertEquals(july19, parseToThreeStrings("Jul 19, 2015  sfdgsd "));
        assertEquals(july19, parseToThreeStrings("JULY 19, 2015  sfdgsd "));
        assertEquals(july19, parseToThreeStrings("JUL 19, 2015  sfdgsd "));

        final ThreeStrings aug19 = threeStrings(2015, 8, 19);
        assertEquals(aug19, parseToThreeStrings("August 19, 2015  sfdgsd "));
        assertEquals(aug19, parseToThreeStrings("Aug 19, 2015  sfdgsd "));
        assertEquals(aug19, parseToThreeStrings("AUG 19, 2015  sfdgsd "));
        assertEquals(aug19, parseToThreeStrings("AUGUST 19, 2015  sfdgsd "));

        final ThreeStrings sep19 = threeStrings(2015, 9, 19);
        assertEquals(sep19, parseToThreeStrings("September 19, 2015  sfdgsd "));
        assertEquals(sep19, parseToThreeStrings("Sep 19, 2015  sfdgsd "));
        assertEquals(sep19, parseToThreeStrings("SEPTEMBER 19, 2015  sfdgsd "));
        assertEquals(sep19, parseToThreeStrings("SEP 19, 2015  sfdgsd "));

        final ThreeStrings oct19 = threeStrings(2015, 10, 19);
        assertEquals(oct19, parseToThreeStrings("October 19, 2015  sfdgsd "));
        assertEquals(oct19, parseToThreeStrings("Oct 19, 2015  sfdgsd "));
        assertEquals(oct19, parseToThreeStrings("OCTOBER 19, 2015  sfdgsd "));
        assertEquals(oct19, parseToThreeStrings("OCT 19, 2015  sfdgsd "));

        final ThreeStrings nov19 = threeStrings(2015, 11, 19);
        assertEquals(nov19, parseToThreeStrings("November 19, 2015  sfdgsd "));
        assertEquals(nov19, parseToThreeStrings("Nov 19, 2015  sfdgsd "));
        assertEquals(nov19, parseToThreeStrings("NOVEMBER 19, 2015  sfdgsd "));
        assertEquals(nov19, parseToThreeStrings("NOV 19, 2015  sfdgsd "));

        final ThreeStrings dec19 = threeStrings(2015, 12, 19);
        assertEquals(dec19, parseToThreeStrings("December 19, 2015  sfdgsd "));
        assertEquals(dec19, parseToThreeStrings("Dec 19, 2015  sfdgsd "));
        assertEquals(dec19, parseToThreeStrings("DECEMBER 19, 2015  sfdgsd "));
        assertEquals(dec19, parseToThreeStrings("DEC 19, 2015  sfdgsd "));
    }

    @Test(expected=Exception.class)
    public void testInvalidDateStringWillThrowException(){
        parseToThreeStrings("December 44, 2015 ");
    }

    @Test
    public void uppercaseIsOkay(){
        final ThreeStrings dec19 = threeStrings(2015, 12, 19);
        assertEquals(dec19, parseToThreeStrings("DECEMBER 19, 2015  sfdgsd "));
    }

    @Test
    public void spaceInYearIsOkay(){
        final ThreeStrings dec19 = threeStrings(2015, 12, 19);
        assertEquals(dec19, parseToThreeStrings("DECEMBER 19, 2 015  sfdgsd "));
    }


    @Test(expected=Exception.class)
    public void twoDayWillThrowException(){
        parseToThreeStrings("Apr 11 27 , 2015");
    }

    @Test
    public void mergeTheLastDigitsIntoAYear1(){
        final List<String> words = LiteralMonthDayYear2.splitToLiteralMonthDayYear2OrYear4("Feb 9, 2 015", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void mergeTheLastDigitsIntoAYear2(){
        final List<String> words = LiteralMonthDayYear2.splitToLiteralMonthDayYear2OrYear4("Feb 9, 2 0 15", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    //TODO throw Exception?
//    @Test
//    public void mergeTheLastDigitsIntoAYear2A(){
//        final List<String> words = LiteralMonthDayYear2.splitToLiteralMonthDayYear2OrYear4("Feb 9, 2 05", 4);
//        assertEquals(3, words.size());
//        assertEquals("Feb", words.get(0));
//        assertEquals("9", words.get(1));
//        assertEquals("2015", words.get(2));
//    }

    @Test
    public void mergeTheLastDigitsIntoAYear3(){
        final List<String> words = LiteralMonthDayYear2.splitToLiteralMonthDayYear2OrYear4("Feb 9, 2 0 1 5", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void mergeTheLastDigitsIntoAYear4(){
        final List<String> words = LiteralMonthDayYear2.splitToLiteralMonthDayYear2OrYear4("Feb 9, 2 0 1 5 ", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void mergeTheLastDigitsIntoAYear5(){
        final List<String> words = LiteralMonthDayYear2.splitToLiteralMonthDayYear2OrYear4("Feb 1 9 , 2 0 1 5 ", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("19", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void mergeTheLastDigitsIntoAYear6(){
        final List<String> words = LiteralMonthDayYear2.splitToLiteralMonthDayYear2OrYear4("Fe b 1 9 , 2 0 1 5 ", 4);
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("19", words.get(1));
        assertEquals("2015", words.get(2));
    }
}
