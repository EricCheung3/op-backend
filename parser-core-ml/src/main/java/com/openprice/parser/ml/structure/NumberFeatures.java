package com.openprice.parser.ml.structure;

import com.openprice.parser.ml.data.Features;
import com.openprice.parser.ml.line.StatisticalFeatures;
import com.openprice.parser.ml.line.StatisticalFeaturesGenerator;

import lombok.Value;

@Value
public class NumberFeatures implements Features {
    final private static StatisticalFeaturesGenerator generator = new StatisticalFeaturesGenerator();
    StatisticalFeatures statistical;

    public NumberFeatures(final String str){
        statistical = generator.getFeatures(str);
    }

    @Override
    public int size() {
        return statistical.size();
    }

}
