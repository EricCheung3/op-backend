package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtilsTest {

    @Test
    public void expiredTest() throws Exception{
        final LocalDate today = LocalDate.now();
        final LocalDate olderDay = LocalDate.of(2015, 1, 10);
        assertTrue(olderDay.isBefore(today));
    }

    @Test
    public void formatDateStringTest1(){
        assertEquals("2015/1/2", DateUtils.formatDateString(LocalDate.of(2015, 01, 02)));
    }

    @Test
    public void formatDateStringTest2(){
        assertEquals("2015/1/2", DateUtils.formatDateString(LocalDate.of(2015, 1, 02)));
    }

    @Test(expected=Exception.class)
    public void invalidMonthShouldThrowException() throws Exception{
        LocalDate.of(2015, 13, 1);
    }

    @Test(expected=Exception.class)
    public void invalidDayShouldThrowException() throws Exception{
        LocalDate.of(2015, 12, 32);
    }


    @Test
    public void testInt(){
        assertEquals(1, 01);
    }

    @Test
    public void beforeUsingDate() throws Exception{
        final LocalDate day1 = LocalDate.of(2015, 1, 01);
        final LocalDate day2 = LocalDate.of(2015, 1, 02);
        assertTrue(DateUtils.before(day1, day2));
    }

    @Test
    public void sameDayUsingDate() throws Exception{
        final LocalDate day1 = LocalDate.of(2015, 1, 01);
        final LocalDate day2 = LocalDate.of(2015, 1, 01);
        assertTrue(DateUtils.sameDay(day1, day2));
    }
}
