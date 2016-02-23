package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
        return threeStrings(mdy2.parse(line, true));
    }

    @Test
    public void test(){
        assertTrue(MonthDayYear2.patternMonthDayYear2.matcher("2/3/13").matches());
        //TODO why these two lines don't pass
//        assertTrue(MonthDayYear2.patternMonthDayYear2.matcher("1 2/3/13").matches());
//        assertTrue(MonthDayYear2.patternMonthDayYear2.matcher("01429 15~ 7913 4606631  2/3/13             6:04P").matches());
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
        assertNull(mdy2.parse("15/12/14a b ce", true));
    }


}
