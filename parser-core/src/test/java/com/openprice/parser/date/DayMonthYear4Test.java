package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

public class DayMonthYear4Test {

    private final DayMonthYear4 dmy4 = new DayMonthYear4();

    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }
    public static ThreeStrings threeStrings(final int y, final int m, final int d){
        return new ThreeStrings(y+"", m+"", d+"");
    }

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(dmy4.parseWithSpaces(line).getDate());
    }
    @Test
    public void testPattern(){
        assertTrue(DayMonthYear4.pattern.matcher("03/05/2015").matches());
    }

    //TODO
//    @Test
//    public void testSpacesIsOkay(){
//        assertTrue(DayMonthYear4.pattern.matcher("03/05 /2015").matches());
//    }

    @Test
    public void test2(){
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("03/05/2015 sdfs "));
    }

    @Test
    public void testSpaceIsOkay(){
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("03 / 05 /2 0   15 sdfs "));
    }

    @Test
    public void testSingleDigitDayIsOkay(){
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("03 / 5 /2 0   15 sdfs "));
    }

    @Test
    public void testSingleDigitMonthIsOkay(){
        assertEquals(threeStrings(2015, 5, 3), parseToThreeStrings("3 / 5 /2 0   15 sdfs "));
    }

    @Test
    public void fromLucky99()throws Exception{
        final String fromLucky99 = "DATE: 23/05/2015 TIME: 5:02:29 PM               HUA";
        assertEquals(threeStrings(2015, 5, 23), parseToThreeStrings(fromLucky99));
    }
}
