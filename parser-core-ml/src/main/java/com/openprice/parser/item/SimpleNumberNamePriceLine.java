package com.openprice.parser.item;

import com.openprice.parser.ml.api.NumberNamePriceLine;
import com.openprice.parser.ml.api.PriceParserConstant;

public class SimpleNumberNamePriceLine implements NumberNamePriceLine{
    private static final NumberNamePriceFeaturesGenerator generator = new NumberNamePriceFeaturesGenerator();

    @Override
    public boolean isNumberNamePriceFormat(String line) {
        final NumberNamePriceFeaturesData features = generator.getFeatures(line);
        return  features.getNumCharsAtMiddle() >= PriceParserConstant.MIN_ITEM_NAME_LETTERS
             && features.getNumHeadingDigits() >= PriceParserConstant.MIN_ITEM_NUMBER_LENGTH
             && features.getNumTrailingDigits() >= 2 //price digits has to be at least 2
             && features.isOneDollarSignAtTail()
             && features.isOneDotAtTail();
    }


}
