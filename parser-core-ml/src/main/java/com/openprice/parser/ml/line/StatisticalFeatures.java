package com.openprice.parser.ml.line;

import com.openprice.common.StringCommon;
import com.openprice.parser.ml.data.Features;

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

    public static StatisticalFeatures fromString(final String str) {
        final int[] digitsLetters = StringCommon.countDigitAndAlphabets(str);
        final int digits = digitsLetters[0];
        final int chars = digitsLetters[1];
        final int non = StringCommon.removeAllSpaces(str).length() - digits - chars;
        final int length = str.length();
        final int wideSpaces = 0;
        final double ratio = chars/Double.valueOf(chars+digits + 0.0);
        return StatisticalFeatures.fromCharsDigitsNonLengthWideRatio(str, chars, digits, non, length, wideSpaces, ratio);
    }

    @Override
    public int size() {
        return 6;
    }
}
