package com.openprice.parser.date.ml;

import lombok.Getter;

public enum DateTokenType {

    Day(1),
    Month(2),
    Year(3),
    Unpredictable(-1);

    @Getter
    private final int intVal;

    private DateTokenType(int i){
        intVal = i;
    }

}
