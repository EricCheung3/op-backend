package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

public class MonthDayYear4Test {

    private final MonthDayYear4 mdy4 = new MonthDayYear4();

    public static ThreeStrings threeStrings(final int[] array){
        return threeStrings(array[0], array[1], array[2]);
    }
    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(DateUtils.getYearMonthDay(mdy4.parse(line)));
    }

    @Test
    public void day2Month2IsOkay(){
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("05/08/2014 fdafda d"));
    }

    @Test
    public void day1Month2IsOkay(){
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("5/08/2014 fdafda d"));
    }

    @Test
    public void day1Month1IsOkay(){
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("sdfsd 5/8/2014 fdafda d"));
    }

    @Test
    public void day1Month1IsOkaySpaceIsAlsoFine(){
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("sdfsd 5 /   8/2 014 fdafda d"));
    }

    @Test
    public void invalidMonthWillReturnNull(){
        assertNull(mdy4.parse("15/8/2014"));
    }

    @Test
    public void invalidDayWillReturnNull(){
        assertNull(mdy4.parse("12/40/2014"));
    }
}
