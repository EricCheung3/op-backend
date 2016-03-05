package com.openprice.parser.ml.api.predictor;

import java.util.Map;

import com.openprice.parser.ml.data.ItemEntity;

public interface LineParser {

    /**
     * split a line and Recognize each token to give structure values of the line
     * @param origLine original line from a receipt
     * @return the map, key being ItemEntity, value being the value of ItemEntity
     */
    Map<ItemEntity, String> splitAndRecognize(String origLine);

}
