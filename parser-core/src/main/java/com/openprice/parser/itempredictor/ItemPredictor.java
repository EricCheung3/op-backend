package com.openprice.parser.itempredictor;

/**
 * predicting a line is an item or not
 */
public interface ItemPredictor {

    boolean isItemLine(String line);

}
