package com.openprice.parser.structure;

import java.util.Map;

import com.openprice.parser.ml.api.Features;
import com.openprice.parser.ml.api.ItemLineStructure;

public class SimpleStructurePredictor implements StructurePredictor{

    private static final StructureFeatureGenerator generator = new StructureFeatureGenerator();

    @Override
    public Map<ItemLineStructure, String> split(String origLine) {
        Map<ItemLineStructure, Features> features = generator.getFeatures(origLine);



    }

}
