package com.openprice.parser.item;

import com.openprice.common.StringCommon;
import com.openprice.parser.structure.NumberNamePriceFeatures;

import lombok.Value;

/**
 * item features
 */
@Value
public class NumberNamePriceFeaturesGenerator {

    public NumberNamePriceFeaturesData getFeatures(final String line){
        final NumberNamePriceFeatures splitF = new NumberNamePriceFeatures(line);
        final int numHeadingDigits = splitF.getNumHeadingDigits();
        final int numTrailingDigits = splitF.getNumTrailingDigits();

        final boolean oneDotAtTail = splitF.getParsedPrice().indexOf('.') >=0 &&
                splitF.getParsedPrice().indexOf('.') == splitF.getParsedPrice().lastIndexOf('.');

        final boolean oneDollarSignAtTail = splitF.getParsedPrice().indexOf('$') >=0 &&
                splitF.getParsedPrice().indexOf('$') == splitF.getParsedPrice().lastIndexOf('$');

        final int[] digitsChars = StringCommon.countDigitAndChars(splitF.getParsedName());
        final int numCharsAtMiddle = digitsChars[1];

        return NumberNamePriceFeaturesData.fromHeadMiddleTailDotDollar(
                numHeadingDigits,
                numCharsAtMiddle,
                numTrailingDigits,
                oneDotAtTail,
                oneDollarSignAtTail);
    }
}
