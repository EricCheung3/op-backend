package com.openprice.parser.ml.line;

import com.openprice.parser.ml.api.predictor.LinePredictor;
import com.openprice.parser.ml.data.LineType;
import com.openprice.parser.ml.data.PriceParserConstant;

public class SimpleLinePredcitor implements LinePredictor{

    private static final StatisticalFeaturesGenerator featureGen = new StatisticalFeaturesGenerator();

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
