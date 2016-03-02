package com.openprice.parser.linepredictor;

import lombok.Value;

@Value
public class LineFeatures {

    String str;
    int numberOfChars;
    int numberOfDigits;
    int numberOfNonDigitNonDigitChars;
    int length;
    int numberOfWideSpaces;
    double charsToCharsAndLetters;//ratio

    public static LineFeatures fromCharsDigitsNonLengthWideRatio(
            String str,
            final int chars,
            final int digits,
            final int non,
            final int length,
            final int wideSpaces,
            final double charsToCharsAndLetters){
        return new LineFeatures(
                str,
                chars,
                digits,
                non,
                length,
                wideSpaces,
                charsToCharsAndLetters);
    }
}
