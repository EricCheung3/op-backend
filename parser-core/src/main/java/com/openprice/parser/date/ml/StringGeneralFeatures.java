package com.openprice.parser.date.ml;

import com.openprice.common.StringCommon;

import lombok.Value;

@Value
public class StringGeneralFeatures {

    String str;
    int numDigits;
    int numChars;
//    int numSplitter;
    int length;
    int trimLength;//length of the trimmed string

    public static StringGeneralFeatures strDigitsCharsSplittersLengthTrimLength(
            String str,
            int numDigits,
            int numChars,
//            int numSplitter,
            int length,
            int trimLength) {
        return new StringGeneralFeatures(
                str,
                numDigits,
                numChars,
//                numSplitter,
                length,
                trimLength);
    }

    public static StringGeneralFeatures fromString(final String str) {
        final int[] digitsChars = StringCommon.countDigitAndAlphabets(str);
        return strDigitsCharsSplittersLengthTrimLength(
                str,
                digitsChars[0],
                digitsChars[1],
                str.length(),
                str.trim().length());
    }
}
