package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

public class LiteralMonthDayYear4Test {

    private final LiteralMonthDayYear4 literalMDY4 = new LiteralMonthDayYear4();

    public static ThreeStrings threeStrings(final int[] array){
        return threeStrings(array[0], array[1], array[2]);
    }
    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(DateParserUtils.getYearMonthDay(literalMDY4.parse(line)));
    }

    @Test
    public void patternLiterMonthDayYearTest1() throws Exception{
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

        final ThreeStrings truth2015March19 = threeStrings(2015, 3, 19);
        assertEquals(truth2015March19, parseToThreeStrings("March 19, 2015  sfdgsd "));
        assertEquals(truth2015March19, parseToThreeStrings("Mar 19, 2015  sfdgsd "));

        final ThreeStrings april19 = threeStrings(2015, 4, 19);
        assertEquals(april19, parseToThreeStrings("April 19, 2015  sfdgsd "));
        assertEquals(april19, parseToThreeStrings("Apr 19, 2015  sfdgsd "));

        final ThreeStrings may19 = threeStrings(2015, 5, 19);
        assertEquals(may19, parseToThreeStrings("May 19, 2015  sfdgsd "));
        assertEquals(may19, parseToThreeStrings("May 19, 2015  sfdgsd "));

        final ThreeStrings june19 = threeStrings(2015, 6, 19);
        assertEquals(june19, parseToThreeStrings("June 19, 2015  sfdgsd "));
        assertEquals(june19, parseToThreeStrings("Jun 19, 2015  sfdgsd "));

        final ThreeStrings july19 = threeStrings(2015, 7, 19);
        assertEquals(july19, parseToThreeStrings("July 19, 2015  sfdgsd "));
        assertEquals(july19, parseToThreeStrings("Jul 19, 2015  sfdgsd "));

        final ThreeStrings aug19 = threeStrings(2015, 8, 19);
        assertEquals(aug19, parseToThreeStrings("August 19, 2015  sfdgsd "));
        assertEquals(aug19, parseToThreeStrings("Aug 19, 2015  sfdgsd "));

        final ThreeStrings sep19 = threeStrings(2015, 9, 19);
        assertEquals(sep19, parseToThreeStrings("September 19, 2015  sfdgsd "));
        assertEquals(sep19, parseToThreeStrings("Sep 19, 2015  sfdgsd "));

        final ThreeStrings oct19 = threeStrings(2015, 10, 19);
        assertEquals(oct19, parseToThreeStrings("October 19, 2015  sfdgsd "));
        assertEquals(oct19, parseToThreeStrings("Oct 19, 2015  sfdgsd "));

        final ThreeStrings nov19 = threeStrings(2015, 11, 19);
        assertEquals(nov19, parseToThreeStrings("November 19, 2015  sfdgsd "));
        assertEquals(nov19, parseToThreeStrings("Nov 19, 2015  sfdgsd "));

        final ThreeStrings dec19 = threeStrings(2015, 12, 19);
        assertEquals(dec19, parseToThreeStrings("December 19, 2015  sfdgsd "));
        assertEquals(dec19, parseToThreeStrings("Dec 19, 2015  sfdgsd "));
    }
}
