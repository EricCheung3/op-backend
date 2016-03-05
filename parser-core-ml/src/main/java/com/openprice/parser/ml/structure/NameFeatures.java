package com.openprice.parser.ml.structure;

import com.openprice.parser.ml.data.Features;
import com.openprice.parser.ml.line.StatisticalFeatures;

import lombok.Value;

@Value
public class NameFeatures implements Features{
    StatisticalFeatures statistical;

    public NameFeatures(final String str){
        statistical = StatisticalFeatures.fromString(str);
    }

    @Override
    public int size() {
        return statistical.size();
    }

}
