package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

public class MonthDayYear2Test {

    private final MonthDayYear2 mdy2 = new MonthDayYear2();

    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }
    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(mdy2.parse(line));
    }

    @Test
    public void patternMDY2Test1Dayhas1Digit() throws Exception{
        assertEquals(threeStrings(2014, 5, 12), parseToThreeStrings("5/12/14a b ce"));
    }

    @Test
    public void patternMDY2TestDayHasTwoDigits() throws Exception{
        assertEquals(threeStrings(2014, 5, 12), parseToThreeStrings("05/12/14a b ce"));
    }

    @Test
    public void patternMDY2TestDayHasTwoDigitsSpaceIsOkay() throws Exception{
        assertEquals(threeStrings(2014, 5, 12), parseToThreeStrings("0  5/  1    2/   1    4a b ce"));
    }

    @Test
    public void patternMDY2TestMonthHasTwoDigits() throws Exception{
        assertEquals(threeStrings(2014, 11, 12), parseToThreeStrings("11/12/14a b ce"));
    }

    @Test
    public void invalidDateStringWillReturnNull() throws Exception{
        assertNull(mdy2.parse("15/12/14a b ce"));
    }


}
