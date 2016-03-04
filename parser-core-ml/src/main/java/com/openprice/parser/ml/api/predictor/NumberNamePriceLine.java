package com.openprice.parser.ml.api.predictor;

/**
 * predicting a line is an item or not
 */
public interface NumberNamePriceLine {

    boolean isNumberNamePriceFormat(String line);

}
