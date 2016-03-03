package com.openprice.parser.itempredictor;

import com.openprice.parser.ml.api.Features;

import lombok.Value;

@Value
public class NumberNamePriceFeatures implements Features{

    int numHeadingDigits;
    int numCharsAtMiddle;
    int numTrailingDigits;

    boolean oneDotAtTail;
    boolean oneDollarSignAtTail;

    public static NumberNamePriceFeatures fromHeadMiddleTailDotDollar(
            final int headDigits,
            final int middleChars,
            final int trailingDigits,
            final boolean dotAtTail,
            final boolean dollarAtTail){
        return new NumberNamePriceFeatures(
                headDigits,
                middleChars,
                trailingDigits,
                dotAtTail,
                dollarAtTail);
    }

    @Override
    public int size() {
        return 5;
    }
}
