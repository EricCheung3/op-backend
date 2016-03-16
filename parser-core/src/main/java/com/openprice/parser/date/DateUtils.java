package com.openprice.parser.date;

import java.time.LocalDate;

//@Slf4j
public class DateUtils {

    public static String localDateToString(final LocalDate date){
        return    date.getYear() + DateConstants.DATE_SPLITTER_UNIFORM
                + date.getMonthValue() + DateConstants.DATE_SPLITTER_UNIFORM
                + date.getDayOfMonth();
    }

    //note this got to have real time instance
    public static LocalDate getToday(){
        return LocalDate.now();
    }

    //note this got to have real time instance
    public static int getCurrentYearInTwoDigits(){
        return getToday().getYear() - 2000;
    }

    public static LocalDate fromDayMonthYear(final String day, final String month, final String year) {
        try{
            return LocalDate.of(
                    Integer.valueOf(year.trim()),
                    Integer.valueOf(month.trim()),
                    Integer.valueOf(day.trim()));
        }catch(Exception e){
//            log.warn(e.getMessage());
        }
        return null;
    }

    public static boolean before(final LocalDate date1, final LocalDate date2){
        return date1.isBefore(date2);
    }

    public static boolean sameDay(final LocalDate date1, final LocalDate date2){
        return date1.equals(date2);
    }
}
