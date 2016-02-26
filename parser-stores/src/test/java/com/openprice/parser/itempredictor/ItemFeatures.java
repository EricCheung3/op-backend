package com.openprice.parser.itempredictor;

import com.openprice.common.StringCommon;
import com.openprice.parser.price.NumberNameNumberSplitting;

import lombok.Value;

/**
 * item features
 */
@Value
public class ItemFeatures {

    int numHeadingDigits;
    int numCharsAtMiddle;
    int numTrailingDigits;

    boolean oneDotAtTail;
    boolean oneDollarSignAtTail;

    public ItemFeatures(final String line){
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(line);
        numHeadingDigits = splitF.getNumHeadingDigits();
        numTrailingDigits = splitF.getNumTrailingDigits();

        final String[] words = splitF.getSplits();
        oneDotAtTail = words[2].indexOf('.') >=0 &&
                          words[2].indexOf('.') == words[2].lastIndexOf('.');

        oneDollarSignAtTail = words[2].indexOf('$') >=0 &&
                                 words[2].indexOf('$') == words[2].lastIndexOf('$');

        final int[] digitsChars = StringCommon.countDigitAndChars(words[1]);
        numCharsAtMiddle = digitsChars[1];
    }
}
