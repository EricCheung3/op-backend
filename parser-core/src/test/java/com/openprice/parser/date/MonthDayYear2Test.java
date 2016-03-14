package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonthDayYear2Test extends DateParserRegularExpressionTestClass {

    public MonthDayYear2Test() {
        super(new MonthDayYear2());
    }

    @Test
    public void testMatches(){
        assertTrue(MonthDayYear2.pattern.matcher("2/3/13").matches());
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
        assertEquals(threeStrings(2016, 3, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2OneSpaceAfterNoisyNumbers2() throws Exception{
        final String str = " 1 2 /05/ 15 11:16 ";
        assertEquals(threeStrings(2015, 12, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2TwoSpacesAfterNoisyNumbers3() throws Exception{
        final String str = " 1  2 /05/ 15 11:16 ";
        assertEquals(threeStrings(2015, 12, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2ThreeSpacesAfterNoisyNumbers3() throws Exception{
        final String str = " 1   2 /05/ 15 11:16 ";
        assertEquals(threeStrings(2015, 12, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2FourSpacesAfterNoisyNumbers3() throws Exception{
        final String str = " 1    2 /05/ 15 11:16 ";
        assertEquals(threeStrings(2015, 12, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2FourSpacesAfterNoisyNumbers4() throws Exception{
        final String str = " 1    2/05/15 11:16 ";
        assertEquals(threeStrings(2015, 2, 5), parseToThreeStrings(str));
    }

    @Test
    public void monthDayYear2FiveSpacesAfterNoisyNumbers3() throws Exception{
        final String str = " 1     2 /05/ 16 11:16 ";
        assertEquals(threeStrings(2016, 2, 5), parseToThreeStrings(str));
    }

    @Test(expected = Exception.class)
    public void futureDateThrowsException() throws Exception{
        final String str = " 12 /05/ 16 11:16 ";
        parseToThreeStrings(str);
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
    public void RespectSpaceShouldFind2AsMonthTwoSpaceBeforeDateString() throws Exception{
        final String line = "01429 15~ 7913 4606631  2/3/13             6:04P";
        assertEquals(threeStrings(2013, 2, 3), parseToThreeStrings(line));
    }

    @Test
    public void patternMDY2TestMonthHasTwoDigits() throws Exception{
        assertEquals(threeStrings(2014, 11, 12), parseToThreeStrings("11/12/14a b ce"));
    }

    @Test(expected=Exception.class)
    public void invalidDateStringWillReturnNull() throws Exception{
        parseToThreeStrings("15/12/14a b ce");
    }

    @Test
    public void twoSpacesBefore() throws Exception{
        assertEquals(threeStrings(2014, 6, 15), parseToThreeStrings("01429 140 5102 4619352  6/15/14    5:06P"));
    }

    @Test
    public void oneSpacesBefore() throws Exception{
        assertEquals(threeStrings(2014, 6, 15), parseToThreeStrings("01429 140 5102 4619352 6/15/14    5:06P"));
    }

    @Test(expected=Exception.class)
    public void noSpacesBefore() throws Exception{
        parseToThreeStrings("01429 140 5102 46193526/15/14    5:06P");
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
