package com.openprice.parser.date;

import java.util.Calendar;

public class DateConstants {

    //today
    public static Calendar TODAY = Calendar.getInstance();

    //the current year
    public static int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR) - 2000;

    //date delimiter
    public static final String DATE_SPLITTER="-/.";//date splitter between day month year

    public static final String DATE_SPLITTER_UNIFORM="/";//uniformly used


}
