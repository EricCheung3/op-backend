package com.openprice.parser.date.ml;

public enum DateTokenType {

    Day,
    Month,
    Year,
    DayOrMonth,//This is for "02"; we really don't know whether it's month or day (or even a year).
    DayOrMonthOrYear
}
