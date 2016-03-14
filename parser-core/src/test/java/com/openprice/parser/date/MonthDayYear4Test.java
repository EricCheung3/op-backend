package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//TODO only parse with no spaces is tested
public class MonthDayYear4Test extends DateParserRegularExpressionTestClass {

    public MonthDayYear4Test() {
        super(new MonthDayYear4());
    }

    @Test
    public void day2Month2IsOkay() throws Exception{
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("05/08/2014 fdafda d"));
    }

    @Test
    public void day1Month2IsOkay() throws Exception{
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("5/08/2014 fdafda d"));
    }

    @Test
    public void day1Month1IsOkay() throws Exception{
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("sdfsd 5/8/2014 fdafda d"));
    }

    @Test
    public void day1Month1IsOkaySpaceIsAlsoFine() throws Exception {
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("sdfsd 5 /   8/2 014 fdafda d"));
    }

    @Test(expected=Exception.class)
    public void invalidMonthWillReturnNull(){
        parseToThreeStrings("15/8/2014");
    }

    @Test(expected=Exception.class)
    public void invalidDayWillReturnNull(){
        parseToThreeStrings("12/40/2014");
    }

    @Test
    public void wideSpaceAfter() throws Exception{
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("sdfsd 5/8/2014     133"));
    }

    @Test
    public void fiveSpaceBefore() throws Exception {
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("1     5/8/2014     133"));
    }

    @Test
    public void fourSpaceBefore() throws Exception {
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("1    5/8/2014     133"));
    }

    @Test
    public void fourSpaceBefore2() throws Exception {
        assertEquals(threeStrings(2014, 2, 8), parseToThreeStrings("1    2/8/2014     133"));
    }

    @Test
    public void threeSpaceBefore2() throws Exception {
        assertEquals(threeStrings(2014, 2, 8), parseToThreeStrings("1   2/8/2014     133"));
    }

    @Test
    public void twoSpaceBefore2() throws Exception {
        assertEquals(threeStrings(2014, 2, 8), parseToThreeStrings("1  2/8/2014     133"));
    }

    @Test
    public void oneSpaceBefore1() throws Exception {
        assertEquals(threeStrings(2014, 2, 8), parseToThreeStrings("1 2/8/2014     133"));
    }

    @Test
    public void threeSpacesBefore() throws Exception {
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("1   5/8/2014     133"));
    }

    @Test
    public void twoSpacesBefore() throws Exception {
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("1  5/8/2014     133"));
    }

    @Test(expected = Exception.class)
    public void noSpacesBefore() throws Exception {
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("15/8/2014     133"));
    }
}
