package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;

import java.util.regex.Pattern;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Year2MonthDayTest extends DateParserRegularExpressionTestClass {

    public Year2MonthDayTest(){
        super(new Year2MonthDay());
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

    @Test(expected = Exception.class)
    public void test2YearShouldHaveAtLeastTwoDigits(){
        parseToThreeStrings("5/03/14");
    }

    @Test(expected = Exception.class)
    public void invalidDayWillReturnNull(){
        parseToThreeStrings("15/03/67");
    }

    @Test(expected = Exception.class)
    public void invalidMonthWillReturnNull(){
        parseToThreeStrings("15/13/6");
    }

    @Test
    public void test2(){
        final String str = "sdfa DATE/TIME:             15/09/12 02:29:08";
        assertEquals(threeStrings(2015, 9, 12), parseToThreeStrings(str));
    }

    @Test
    public void test3(){
        assertEquals(threeStrings(2014, 6, 15), parseToThreeStrings("sdfa DATE/TIME:            14/06/15 02:29:08"));
    }

    @Test(expected = Exception.class)
    public void testCPUTime1A() throws Exception {
        final String line = "ReaPrice    4    9                                                                                                        ";
        parseToThreeStrings(line);
    }

}
