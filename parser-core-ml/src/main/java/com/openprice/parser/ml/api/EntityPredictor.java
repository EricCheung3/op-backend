package com.openprice.parser.ml.api;

public interface EntityPredictor {

    //predict the ItemEntity of the string
    ItemEntity predict(final String str);

}
