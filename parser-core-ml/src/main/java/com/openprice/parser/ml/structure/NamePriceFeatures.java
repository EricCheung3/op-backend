package com.openprice.parser.ml.structure;

import com.openprice.parser.ml.data.Features;
import com.openprice.parser.ml.line.StatisticalFeatures;

import lombok.Value;

@Value
public class NamePriceFeatures implements Features{
    StatisticalFeatures statistical;

    public NamePriceFeatures(final String str){
        statistical = StatisticalFeatures.fromString(str);
    }

    @Override
    public int size() {
        return statistical.size();
    }
}