package com.openprice.parser.linepredictor;

import com.openprice.parser.ml.api.Features;

import lombok.Value;

@Value
public class StatisticalFeatures implements Features{

    String str;
    int numberOfChars;
    int numberOfDigits;
    int numberOfNonDigitNonDigitChars;
    int length;
    int numberOfWideSpaces;
    double charsToCharsAndLetters;//ratio

    public static StatisticalFeatures fromCharsDigitsNonLengthWideRatio(
            String str,
            final int chars,
            final int digits,
            final int non,
            final int length,
            final int wideSpaces,
            final double charsToCharsAndLetters){
        return new StatisticalFeatures(
                str,
                chars,
                digits,
                non,
                length,
                wideSpaces,
                charsToCharsAndLetters);
    }

    @Override
    public int size() {
        return 6;
    }
}
