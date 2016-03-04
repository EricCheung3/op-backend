package com.openprice.parser.structure;

import com.openprice.parser.line.StatisticalFeatures;
import com.openprice.parser.ml.api.Features;

import lombok.Value;

@Value
public class NameFeatures implements Features{

    StatisticalFeatures statistical;
    String parsedName; //parsed name value from statistical.str

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

}
