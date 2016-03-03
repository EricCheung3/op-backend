package com.openprice.parser.itempredictor;

import com.openprice.parser.ml.api.Features;

import lombok.Value;

@Value
public class SplittingFeatures implements Features{

    int numHeadingDigits;
    int numCharsAtMiddle;
    int numTrailingDigits;

    boolean oneDotAtTail;
    boolean oneDollarSignAtTail;

    public static SplittingFeatures fromHeadMiddleTailDotDollar(
            final int headDigits,
            final int middleChars,
            final int trailingDigits,
            final boolean dotAtTail,
            final boolean dollarAtTail){
        return new SplittingFeatures(
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
