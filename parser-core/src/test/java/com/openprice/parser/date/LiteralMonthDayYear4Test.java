package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LiteralMonthDayYear4Test {

    private final LiteralMonthDayYear4 literalMDY4 = new LiteralMonthDayYear4();

    public static ThreeStrings threeStrings(final int y, final int m, final int d) {
        return new ThreeStrings(y+"", m+"",  d+"");
    }
    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(literalMDY4.parseWithSpaces(line).getDate());
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

    @Test
    public void testInvalidDateStringWillReturnNull(){
        assertNull(literalMDY4.parseWithSpaces("December 44, 2015 "));
    }

    @Test
    public void uppercaseIsOkay(){
        final ThreeStrings dec19 = threeStrings(2015, 12, 19);
        assertEquals(dec19, parseToThreeStrings("DECEMBER 19, 2015  sfdgsd "));
    }


    @Test
    public void literalMonthDayYearSplitTest1(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("Feb 9  , 2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitTest2(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("Feb 9,2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitCommaSpacesIsOkay(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("Feb 9,    2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearUpperCaseIsOkay(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("FEB 9,    2015");
        assertEquals(3, words.size());
        assertEquals("FEB", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }


    @Test
    public void literalMonthDayYearSplitCommaSpacesIsOkay2(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("Feb  9  ,    2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitDashSpacesIsOkay(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("Feb 9-    2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitUpperIsOkay(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("FEB 9-    2015");
        assertEquals(3, words.size());
        assertEquals("FEB", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitDashSpacesIsOkay2(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("Feb 9   -       2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitUnderscoreSpacesIsOkay(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("Feb 9 _    2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void literalMonthDayYearSplitUnderscoreSpacesIsOkay2(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("Feb 9_    2015");
        assertEquals(3, words.size());
        assertEquals("Feb", words.get(0));
        assertEquals("9", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void twoDayWillHaveNull(){
       assertNull(literalMDY4.parseWithSpaces("Apr 11 27 , 2015"));
    }

    @Test
    public void singleQuoteNonUnicodeIsOkay(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("OCT.08’2015");
        assertEquals(3, words.size());
        assertEquals("OCT", words.get(0));
        assertEquals("08", words.get(1));
        assertEquals("2015", words.get(2));
    }

    @Test
    public void singleQuoteIsOkay(){
        final List<String> words = LiteralMonthDayYear4.literalMonthDayYearSplit("OCT.08'2015");
        assertEquals(3, words.size());
        assertEquals("OCT", words.get(0));
        assertEquals("08", words.get(1));
        assertEquals("2015", words.get(2));
    }

}
