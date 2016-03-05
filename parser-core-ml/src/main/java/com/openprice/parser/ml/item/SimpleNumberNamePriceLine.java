package com.openprice.parser.ml.item;

import com.openprice.parser.ml.data.PriceParserConstant;

public class SimpleNumberNamePriceLine {
    private static final NumberNamePriceFeaturesGeneratorOld generator = new NumberNamePriceFeaturesGeneratorOld();

    public boolean isNumberNamePriceFormat(String line) {
        final NumberNamePriceFeatures features = generator.getFeatures(line);
        return  features.getNumCharsAtMiddle() >= PriceParserConstant.MIN_ITEM_NAME_LETTERS
             && features.getNumHeadingDigits() >= PriceParserConstant.MIN_ITEM_NUMBER_LENGTH
             && features.getNumTrailingDigits() >= 2 //price digits has to be at least 2
             && features.isOneDollarSignAtTail()
             && features.isOneDotAtTail();
    }


}
