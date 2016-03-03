package com.openprice.parser.itempredictor;

import com.openprice.common.StringCommon;
import com.openprice.parser.linesplitter.NumberNamePriceSplitting;

import lombok.Value;

/**
 * item features
 */
@Value
public class ItemFeatures {

    public NumberNamePriceFeatures getFeatures(final String line){
        final NumberNamePriceSplitting splitF = new NumberNamePriceSplitting(line);
        final int numHeadingDigits = splitF.getNumHeadingDigits();
        final int numTrailingDigits = splitF.getNumTrailingDigits();

        final String[] words = splitF.getSplits();
        final boolean oneDotAtTail = words[2].indexOf('.') >=0 &&
                          words[2].indexOf('.') == words[2].lastIndexOf('.');

        final boolean oneDollarSignAtTail = words[2].indexOf('$') >=0 &&
                                 words[2].indexOf('$') == words[2].lastIndexOf('$');

        final int[] digitsChars = StringCommon.countDigitAndChars(words[1]);
        final int numCharsAtMiddle = digitsChars[1];

        return NumberNamePriceFeatures.fromHeadMiddleTailDotDollar(
                numHeadingDigits,
                numCharsAtMiddle,
                numTrailingDigits,
                oneDotAtTail,
                oneDollarSignAtTail);
    }
}
