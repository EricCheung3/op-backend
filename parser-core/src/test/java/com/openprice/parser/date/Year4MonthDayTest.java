package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.util.regex.Pattern;

import org.junit.Test;

public class Year4MonthDayTest extends DateParserRegularExpressionTestClass {

    public Year4MonthDayTest() {
        super(new Year4MonthDay());
    }

    @Test
    public void patternY4MD2Test1() throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014/5/01"));
    }

    @Test
    public void patternY4MD2Test2() throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014/05/01"));
    }

    @Test
    public void patternY4MD2TestDayHasOneDigits() throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014/5/1"));
    }

    @Test
    public void spaces1() throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014 /5/01"));
    }

    @Test
    public void spaces1A()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014/5/01"));
    }

    @Test
    public void spaces2()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014 / 5/01"));
    }

    @Test
    public void spaces3()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014 / 5 /01"));
    }

    @Test
    public void spaces4()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014 / 5 / 01"));
    }

    @Test
    public void spaceTest() {
        Pattern pattern = Pattern.compile("(\\d|\\d\\s*\\d)");
        pattern.matcher("1");
        pattern.matcher("11");
        pattern.matcher("1 1");
    }

    @Test
    public void spaces5()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014 / 5 / 01 "));
    }

    @Test
    public void spacesInner1()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2 014/5/01 "));
    }

    @Test
    public void spacesInner2()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2  014/5/01 "));
    }

    @Test
    public void spacesInner3_NoDigit0()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2  014/5/1 "));
    }

    @Test
    public void spacesInner3()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2  014/5/01 "));
    }

    @Test
    public void spacesInner4()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2  0 14/5/01 "));
    }

    @Test
    public void spacesInner5()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2  0 1 4/5/01 "));
    }

    @Test
    public void spacesInner6()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2  0 1 4 /5/01 "));
    }

    @Test
    public void twoDigitMonths()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2  0 1 4 /05/01 "));
    }

    @Test
    public void twoDigitMonthsSpace1()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2  0 1 4 / 05/01 "));
    }

    @Test
    public void twoDigitMonthsSpace2()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2  0 1 4 / 0 5/01 "));
    }

    @Test
    public void twoDigitMonthsSpac3()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2  0 1 4 / 0 5 / 0 1  "));
    }

    @Test
    public void twoDigitDay()throws Exception{
        assertEquals(threeStrings(2014, 11, 25), parseToThreeStrings("2014/11/25"));
    }

    @Test
    public void patternY4MD2TestDayHasOneDigitsSpaceIsOkay()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("abd 2 014 /5 /0  1 efre"));
    }





}
