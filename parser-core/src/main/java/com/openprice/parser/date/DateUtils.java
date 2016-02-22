package com.openprice.parser.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {

    public static String formatDateString(final Calendar date){
        return formatDateString(date.getTime());
    }

    public static String formatDateString(final Date date){
        final int[] yMD=getYearMonthDay(date);
        return    yMD[0] + DateConstants.DATE_SPLITTER_UNIFORM
                + yMD[1] + DateConstants.DATE_SPLITTER_UNIFORM
                + yMD[2];
    }

    //note this got to have real time instance
    public static Date getToday(){
        return Calendar.getInstance().getTime();
    }

    //note this got to have real time instance
    public static int getCurrentYear(){
        return Calendar.getInstance().get(Calendar.YEAR) - 2000;
    }

    /**
     * get the year month day in integer from a Date object
     * @param date
     * @return
     */
    public static int[] getYearMonthDay(final Date date){
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getYearMonthDay(cal);
    }
    public static int[] getYearMonthDay(final Calendar cal){
        return new int[]{
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH)+1,
                cal.get(Calendar.DAY_OF_MONTH)};
    }

    /**
     * convert a digital date string to Date
     * two order are allowed:
     * Month Day Year or Year Month Day
     * @param dateStr
     * @return
     */
    public static Calendar getCalendar(final int day, final int month, final int year) throws Exception{
        Calendar date = new GregorianCalendar();
        //setting allows invalid dates
        date.setLenient(false);
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month - 1);
        date.set(Calendar.DAY_OF_MONTH, day);
        //getting will throw Excepiton in Lenient false mode
        try {
            date.getTime();
        }
        catch (Exception e) {
            throw e;
        }
        return date;
    }

    public static Calendar getCalendar(final String day, final String month, final String year) {
        try{
            return getCalendar(Integer.valueOf(day.trim()), Integer.valueOf(month.trim()), Integer.valueOf(year.trim()));
        }catch(Exception e){
            log.warn(e.getMessage());
        }
        return null;
    }

    public static Calendar getCalendar(final Date date) throws Exception{
        final int[] yMD = getYearMonthDay(date);
        return getCalendar(yMD[2], yMD[1], yMD[0]);
    }

    public static Date getDate(final Calendar cal){
        return cal.getTime();
    }

    public static boolean before(final Date date1, final Date date2){
        return date1.before(date2);
    }

    public static boolean sameDay(final Date date1, final Date date2){
        return date1.equals(date2);
    }

    public static boolean before(final Calendar date1, final Calendar date2){
        return date1.before(date2);
    }

    public static boolean sameDay(final Calendar date1, final Calendar date2){
        return date1.equals(date2);
    }
}
