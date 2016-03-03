package com.openprice.parser.structure;

import java.util.Map;

import com.openprice.parser.ml.api.ItemLineStructure;

public interface StructurePredictor {

    /**
     * split a line to give structure values of the line
     * @param origLine original line from a receipt
     * @return the map, key being ItemLineStructure, value being the value of ItemLineStructure
     */
    Map<ItemLineStructure, String> split(String origLine);

}
