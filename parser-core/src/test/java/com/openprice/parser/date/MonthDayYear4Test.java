package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

//TODO only parse with no spaces is tested
public class MonthDayYear4Test {

    private final MonthDayYear4 mdy4 = new MonthDayYear4();

    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }
    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line) throws Exception{
        if(mdy4.parseWithSpaces(line) == null)
            throw new Exception("parsed result is null");
        return threeStrings(mdy4.parseWithSpaces(line).getDate());
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

    @Test
    public void invalidMonthWillReturnNull(){
        assertNull(mdy4.parseWithSpaces("15/8/2014"));
    }

    @Test
    public void invalidDayWillReturnNull(){
        assertNull(mdy4.parseWithSpaces("12/40/2014"));
    }

    @Test
    public void wideSpaceAfter() throws Exception{
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("sdfsd 5/8/2014     133"));
    }

    @Test
    public void wideSpaceBefore() throws Exception {
        assertEquals(threeStrings(2014, 5, 8), parseToThreeStrings("1     5/8/2014     133"));
    }
}
