package com.openprice.parser.ml.api;

/**
 * predictin a line's content is about date, item, address or noise.
 */
public interface LinePredictor {

    //classify a line
    LineType classify(String str);
}
