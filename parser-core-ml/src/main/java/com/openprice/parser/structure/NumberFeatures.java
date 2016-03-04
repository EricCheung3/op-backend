package com.openprice.parser.structure;

import com.openprice.parser.line.StatisticalFeatures;
import com.openprice.parser.ml.api.Features;

public class NumberFeatures implements Features {
    StatisticalFeatures statistical;
    String parsedNumber; //parsed number value from statistical.str

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

}
