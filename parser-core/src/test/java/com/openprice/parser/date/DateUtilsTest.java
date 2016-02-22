package com.openprice.parser.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtilsTest {

    @Test(expected=Exception.class)
    public void calendarShouldThrowException() throws Exception{
        DateUtils.getCalendar(34, 01, 2015);
    }

    @Test
    public void formatDateStringTest()throws Exception{
        final Date date=DateParserUtils.toDateFromDigitalFormat("2013/01/31");
        assertEquals("2013/1/31", DateUtils.formatDateString(date));
    }

    @Test
    public void testInt(){
        assertEquals(1, 01);
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
        log.debug("cal1="+cal1);
        log.debug("cal2="+cal2);
        assertTrue(DateUtils.before(cal1, cal2));
    }

    @Test
    public void sameDayUsingCalendar() throws Exception{
        final Calendar cal1 = DateUtils.getCalendar(1, 01, 2015);
        final Calendar cal2 = DateUtils.getCalendar(1, 01, 2015);
        assertTrue(DateUtils.sameDay(cal1, cal2));
    }
}
