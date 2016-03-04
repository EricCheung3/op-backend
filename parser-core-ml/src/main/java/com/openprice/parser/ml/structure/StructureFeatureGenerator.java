package com.openprice.parser.ml.structure;

import java.util.HashMap;
import java.util.Map;

import com.openprice.parser.ml.data.Features;
import com.openprice.parser.ml.data.ItemLineStructure;

public class StructureFeatureGenerator {

    public Map<ItemLineStructure, Features> getFeatures(final String origLine){
        final Map<ItemLineStructure, Features> map = new HashMap<>();
        map.put(ItemLineStructure.Name, new NameFeatures(origLine));
        map.put(ItemLineStructure.Number, new NumberFeatures(origLine));
        map.put(ItemLineStructure.NumberNamePrice, new NumberNamePriceFeatures(origLine));
        map.put(ItemLineStructure.NamePrice, new NamePriceFeatures(origLine));
        return map;
    }


}
