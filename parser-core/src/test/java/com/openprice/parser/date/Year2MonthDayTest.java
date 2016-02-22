package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

public class Year2MonthDayTest {

    private final Year2MonthDay y2md = new Year2MonthDay();

    public static ThreeStrings threeStrings(final LocalDate date){
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }
    public static ThreeStrings threeStrings(final int[] array){
        return threeStrings(array[0], array[1], array[2]);
    }

    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line){
        return threeStrings(y2md.parse(line));
    }

    @Test
    public void test(){
        final Pattern pattern= Pattern.compile("\\d{1,2}");
        final String shouldBe14 = DateParserUtils.pruneDateStringWithMatch("14", pattern);
        assertEquals("14", shouldBe14);
    }

    @Test
    public void test2DayHasOneDigits(){
        assertEquals(threeStrings(2015, 3, 1), parseToThreeStrings("sdfa 15/03/1"));
    }

    @Test
    public void test2DayHasTwoDigits(){
        assertEquals(threeStrings(2015, 3, 14), parseToThreeStrings("sdfa 15/03/14"));
    }

    @Test
    public void test2YearShouldHaveAtLeastTwoDigits(){
        assertEquals(null, y2md.parse("5/03/14"));
    }

    @Test
    public void invalidDayWillReturnNull(){
        assertNull(y2md.parse("15/03/67"));
    }

    @Test
    public void invalidMonthWillReturnNull(){
        assertNull(y2md.parse("15/13/6"));
    }

    @Test
    public void test2(){
        assertEquals(threeStrings(2015, 9, 12), parseToThreeStrings("sdfa DATE/TIME:             15/09/12 02:29:08"));
    }
}
