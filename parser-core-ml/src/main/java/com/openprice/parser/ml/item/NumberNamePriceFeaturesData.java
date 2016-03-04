package com.openprice.parser.ml.item;

import com.openprice.parser.ml.data.Features;

import lombok.Value;

@Value
public class NumberNamePriceFeaturesData implements Features{

    int numHeadingDigits;
    int numCharsAtMiddle;
    int numTrailingDigits;

    boolean oneDotAtTail;
    boolean oneDollarSignAtTail;

    public static NumberNamePriceFeaturesData fromHeadMiddleTailDotDollar(
            final int headDigits,
            final int middleChars,
            final int trailingDigits,
            final boolean dotAtTail,
            final boolean dollarAtTail){
        return new NumberNamePriceFeaturesData(
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
