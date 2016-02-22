package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

public class Month1DayYear2Test {

    private final Month1DayYear2 m1dy2 = new Month1DayYear2();

    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }
    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(m1dy2.parse(line));
    }

    @Test
    public void testSears() {
        final String fromSear = "01429 15~ 4884 4601652 4/11/13 4:15P";
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(fromSear));
    }

    @Test
    public void testSearsSpacesIsOkay() {
        final String fromSear = "01429 15~ 4884 4601652 4 /11/13 4:15P";
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(fromSear));
    }

    @Test
    public void testSearsSpacesIsOkay2() {
        final String fromSear = "01429 15~ 4884 4601652 4 /1  1  /1 3  4:15P";
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(fromSear));
    }

    @Test
    public void testSearsMessupIsOkay2() {
        final String fromSear = "01429 15~ 4884 46016524 /1  1  /1 3  4:15P";
        assertEquals(threeStrings(2013, 4, 11), parseToThreeStrings(fromSear));
    }
}
