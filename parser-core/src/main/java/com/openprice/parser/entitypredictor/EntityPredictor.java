package com.openprice.parser.entitypredictor;

public interface EntityPredictor {

    //predict the ItemEntity of the string
    ItemEntity predict(final String str);

}
