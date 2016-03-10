package com.openprice.parser.ml.structure;

import com.openprice.parser.ml.api.predictor.StructurePredictor;
import com.openprice.parser.ml.data.LineStructure;
import com.openprice.parser.ml.data.PriceParserConstant;
import com.openprice.parser.ml.item.NumberNamePriceFeatures;

public class SimpleStructurePredictor implements StructurePredictor{

//    private static final StatisticalFeaturesGenerator generator = new StatisticalFeaturesGenerator();

    @Override
    public LineStructure classify(String origLine) {
        final NumberNamePriceFeatures features = NumberNamePriceFeatures.fromString(origLine);

        if( features.getNumCharsAtMiddle() >= PriceParserConstant.MIN_ITEM_NAME_LETTERS
             && features.getNumHeadingDigits() >= PriceParserConstant.MIN_ITEM_NUMBER_LENGTH
             && features.getNumTrailingDigits() >= 2 //price digits has to be at least 2
             && features.isOneDollarSignAtTail()
             && features.isOneDotAtTail())
            return LineStructure.NumberNamePrice;

        return LineStructure.Unpredictable;
    }

}
