package com.openprice.parser.linepredictor;

import com.openprice.common.StringCommon;

public class FeatureGenerator {

    public LineFeatures getFeatures(String str){
        final int[] digitsLetters = StringCommon.countDigitAndAlphabets(str);
        final int digits = digitsLetters[0];
        final int chars = digitsLetters[1];
        final int non = StringCommon.removeAllSpaces(str).length() - digits - chars;
        final int length = str.length();
        final int wideSpaces = 0;
        final double ratio = chars/Double.valueOf(chars+digits + 0.0);
        return LineFeatures.fromCharsDigitsNonLengthWideRatio(str, chars, digits, non, length, wideSpaces, ratio);
    }
}
