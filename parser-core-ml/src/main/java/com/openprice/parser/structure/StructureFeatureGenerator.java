package com.openprice.parser.structure;

import java.util.HashMap;
import java.util.Map;

import com.openprice.parser.ml.api.Features;
import com.openprice.parser.ml.api.ItemLineStructure;

public class StructureFeatureGenerator {

    public Map<ItemLineStructure, Features> getFeatures(final String origLine){
        final Map<ItemLineStructure, Features> features = new HashMap<>();

        final double scoreName = scoreName(origLine);
        features.put(ItemLineStructure.Name, value);

        return features;
    }

    //score of the line being a name
    final double scoreName(final String origLine) {

    }

    //score of the line being a number
    final double scoreNumber(final String origLine) {

    }

    //score of the line being a name plus price
    final double scoreNamePrice(final String origLine) {

    }

    //score of the line being a number name price
    final double scoreNumberNamePrice(final String origLine) {

    }







}
