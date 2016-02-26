package com.openprice.parser.itempredictor;

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

    boolean hasOneDotAtTail;
    boolean hasOneDollarSignAtTail;

    public ItemFeatures(final String line){
        final NumberNameNumberSplitting splitF = new NumberNameNumberSplitting(line);
        numHeadingDigits = splitF.getNumHeadingDigits();
        numTrailingDigits = splitF.getNumTrailingDigits();
        numCharsAtMiddle = line.trim().length() - numHeadingDigits - numTrailingDigits;

        final String[] words = splitF.getSplits();
        hasOneDotAtTail = words[2].indexOf('.') >=0 &&
                          words[2].indexOf('.') == words[2].lastIndexOf('.');

        hasOneDollarSignAtTail = words[2].indexOf('$') >=0 &&
                                 words[2].indexOf('$') == words[2].lastIndexOf('$');
    }
}
