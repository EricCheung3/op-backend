package com.openprice.parser.generic;

import lombok.Value;

@Value
public class StringDouble {

    String str;
    double value;

    public StringDouble(final String str, final double value){
        this.str=str;
        this.value=value;
    }

}
