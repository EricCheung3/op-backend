package com.openprice.parser.date.ml;

import lombok.Getter;

public enum DateTokenType {

    Day(1),
    Month(2),
    Year(3),
    DayOrMonth(4),//This is for "02"; we really don't know whether it's month or day (or even a year).
    DayOrMonthOrYear(5),
    Unpredictable(-1);

    @Getter
    private final int intVal;

    private DateTokenType(int i){
        intVal = i;
    }

}
