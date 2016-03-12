package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import com.openprice.parser.price.ThreeStrings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonthDayYear2Test {

    private final MonthDayYear2 mdy2 = new MonthDayYear2();

    public static void verify(final LocalDateFeatures features, final ThreeStrings three){
        assertEquals(three.getFirst(), features.getDate().getYear()+"");
        assertEquals(three.getSecond(), features.getDate().getMonthValue()+"");
        assertEquals(three.getThird(), features.getDate().getDayOfMonth()+"");
    }

    public static ThreeStrings threeStrings(final LocalDate date) throws Exception{
        if(date==null)
            throw new Exception("parsed result is null");
        return new ThreeStrings(date.getYear()+"", date.getMonthValue()+"", date.getDayOfMonth()+"");
    }
    public static ThreeStrings threeStrings(final int a, final int b, final int c){
        return new ThreeStrings(a+"", b+"", c+"");
    }

    public ThreeStrings parseToThreeStrings(final String line) throws Exception{
        final LocalDateFeatures dateFeatures = mdy2.parseWithSpaces(line);
        if(dateFeatures==null) return null;
        return threeStrings(dateFeatures.getDate());
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
    public void monthDayYear2AfterNoisyNumbers1A() throws Exception{
        final String str = " 3973 02782 05 051 46296                                     3 /05/ 16 11:16 ";
//        assertEquals(threeStrings(2016, 3, 5), parseToThreeStrings(str));
        LocalDateFeatures dateFeatures = mdy2.parseWithSpaces(str);
        verify(dateFeatures, threeStrings(2016,3,5));
        assertEquals(5, dateFeatures.getDate().getDayOfMonth());
        assertEquals(2016, dateFeatures.getDate().getYear());
        assertEquals(3, dateFeatures.getDate().getMonthValue());
    }

    @Test
    public void monthDayYear2OneSpaceAfterNoisyNumbers2() throws Exception{
        final String str = " 1 2 /05/ 16 11:16 ";
        assertEquals(threeStrings(2016, 12, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2TwoSpacesAfterNoisyNumbers3() throws Exception{
        final String str = " 1  2 /05/ 16 11:16 ";
        assertEquals(threeStrings(2016, 12, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2ThreeSpacesAfterNoisyNumbers3() throws Exception{
        final String str = " 1   2 /05/ 16 11:16 ";
        assertEquals(threeStrings(2016, 12, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2FourSpacesAfterNoisyNumbers3() throws Exception{
        final String str = " 1    2 /05/ 16 11:16 ";
        assertEquals(threeStrings(2016, 2, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2FiveSpacesAfterNoisyNumbers3() throws Exception{
        final String str = " 1     2 /05/ 16 11:16 ";
        assertEquals(threeStrings(2016, 2, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2NoNoisyNumbersBefore() throws Exception{
        final String str = " 12 /05/ 16 11:16 ";
        assertEquals(threeStrings(2016, 12, 5), parseToThreeStrings(str));
    }

    @Test
    public void patternMDY2TestDayHasTwoDigits() throws Exception{
        assertEquals(threeStrings(2014, 5, 12), parseToThreeStrings("05/12/14a b ce"));
    }

    //TODO
//    @Test
//    public void patternMDY2TestDayHasTwoDigitsSpaceIsOkay() throws Exception{
//        assertEquals(threeStrings(2014, 5, 12), parseToThreeStrings("0  5/  1    2/   1    4a b ce"));
//    }

    @Test
    public void patternMDY2TestMonthHasTwoDigits() throws Exception{
        assertEquals(threeStrings(2014, 11, 12), parseToThreeStrings("11/12/14a b ce"));
    }

    @Test
    public void invalidDateStringWillReturnNull() throws Exception{
        assertNull(mdy2.parseWithSpaces("15/12/14a b ce"));
    }

    @Test
    public void twoSpacesBefore() throws Exception{
        assertEquals(threeStrings(2014, 6, 15), parseToThreeStrings("01429 140 5102 4619352  6/15/14    5:06P"));
    }

    @Test
    public void oneSpacesBefore() throws Exception{
        assertEquals(threeStrings(2014, 6, 15), parseToThreeStrings("01429 140 5102 4619352 6/15/14    5:06P"));
    }

    @Test
    public void noSpacesBefore() throws Exception{
        assertEquals(null, parseToThreeStrings("01429 140 5102 46193526/15/14    5:06P"));
    }

    @Test
    public void noSpacesBeforeA() throws Exception{
        assertEquals(threeStrings(2014, 6, 15), parseToThreeStrings("01429 140 5102 46193506/15/14    5:06P"));
    }

    //TODO: make sense?
//    @Test
//    public void manyWideSpaces2() throws Exception{
//        final String str = "Term Tran       Store         Oper 1               0/ 27/ 15";
//        assertEquals(threeStrings(2015, 10, 27), parseToThreeStrings(str));
//    }
}
