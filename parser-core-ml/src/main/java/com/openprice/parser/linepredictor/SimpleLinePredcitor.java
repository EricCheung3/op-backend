package com.openprice.parser.linepredictor;

import com.openprice.parser.ml.api.LinePredictor;
import com.openprice.parser.ml.api.LineType;
import com.openprice.parser.ml.api.PriceParserConstant;

public class SimpleLinePredcitor implements LinePredictor{

    private static final FeatureGenerator featureGen = new FeatureGenerator();

    @Override
    public LineType classify(final String str) {
        if(str.contains("kg") && str.contains("@"))
            return LineType.WeightPrice;

        final StatisticalFeatures features =  featureGen.getFeatures(str);
        if(features.getNumberOfChars() > PriceParserConstant.MIN_ITEM_NAME_LETTERS
              && features.getCharsToCharsAndLetters() >= PriceParserConstant.MIN_ITEM_NAME_LETTERS_PERCENT)
            return LineType.Item;
        return LineType.Unpredictable;
    }


}
