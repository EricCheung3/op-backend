package com.openprice.parser.date;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

public class DateUtilsTest {

    @Test(expected=Exception.class)
    public void calendarShouldThrowException() throws Exception{
        DateUtils.getCalendar(34, 01, 2015);
    }

    @Test
    public void beforeUsingDate() throws Exception{
        final Calendar cal1 = DateUtils.getCalendar(1, 01, 2015);
        final Calendar cal2 = DateUtils.getCalendar(2, 01, 2015);
        assertTrue(DateUtils.before(cal1.getTime(), cal2.getTime()));
    }

    @Test
    public void sameDayUsingDate() throws Exception{
        final Calendar cal1 = DateUtils.getCalendar(1, 01, 2015);
        final Calendar cal2 = DateUtils.getCalendar(1, 01, 2015);
        assertTrue(DateUtils.sameDay(cal1.getTime(), cal2.getTime()));
    }

    @Test
    public void beforeUsingCalendar() throws Exception{
        final Calendar cal1 = DateUtils.getCalendar(1, 01, 2015);
        final Calendar cal2 = DateUtils.getCalendar(2, 01, 2015);
        assertTrue(DateUtils.before(cal1, cal2));
    }

    @Test
    public void sameDayUsingCalendar() throws Exception{
        final Calendar cal1 = DateUtils.getCalendar(1, 01, 2015);
        final Calendar cal2 = DateUtils.getCalendar(1, 01, 2015);
        assertTrue(DateUtils.sameDay(cal1, cal2));
    }
}
