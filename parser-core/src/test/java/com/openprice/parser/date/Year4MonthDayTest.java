package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

public class Year4MonthDayTest {

    final private Year4MonthDay y4MD = new Year4MonthDay();
    public static ThreeStrings threeStrings(final int[] array){
        return threeStrings(array[0], array[1], array[2]);
    }

    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(DateParserUtils.getYearMonthDay(y4MD.parse(line)));
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
    public void patternY4MD2TestDayHasOneDigitsSpaceIsOkay()throws Exception{
        assertEquals(threeStrings(2014, 5, 1), parseToThreeStrings("abd 2 014 /5 /0  1 efre"));
    }

}
