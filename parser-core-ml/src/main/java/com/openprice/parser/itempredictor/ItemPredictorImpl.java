package com.openprice.parser.itempredictor;

import com.openprice.parser.ml.api.PriceParserConstant;

public class ItemPredictorImpl implements ItemPredictor{
    private static final ItemFeatures generator = new ItemFeatures();

    @Override
    public boolean isItemLine(String line) {
        final SplittingFeatures features = generator.getFeatures(line);
        return features.getNumCharsAtMiddle() >= PriceParserConstant.MIN_ITEM_NAME_LETTERS
             && features.getNumHeadingDigits() >= PriceParserConstant.MIN_ITEM_NUMBER_LENGTH
             && features.getNumTrailingDigits() >= 2 //price digits has to be at least 2
             && features.isOneDollarSignAtTail()
             && features.isOneDotAtTail();
    }


}
