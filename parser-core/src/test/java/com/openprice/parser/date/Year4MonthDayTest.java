package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

public class Year4MonthDayTest {

    final private Year4MonthDay y4MD = new Year4MonthDay();
    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }
    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(y4MD.parseWithSpaces(line));
    }

    @Test
    public void patternY4MD2Test1()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014/5/01"));
    }

    @Test
    public void patternY4MD2Test2()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014/05/01"));
    }

    @Test
    public void patternY4MD2TestDayHasOneDigits()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("2014/5/1"));
    }

    @Test
    public void spaces1()throws Exception{
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
    public void patternY4MD2TestDayHasOneDigitsSpaceIsOkay()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("abd 2 014 /5 /0  1 efre"));
    }

}
