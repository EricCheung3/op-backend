package com.openprice.parser.date.ml;

import com.openprice.common.StringCommon;
import com.openprice.parser.date.MonthLiterals;
import com.openprice.parser.ml.data.Features;

import lombok.Value;

@Value
public class DateTokenFeatures implements Features {

    private final static int INVALID = -1;
    private final static MonthLiterals monthLiterals= new MonthLiterals();

    String str;//original String

    int intValue;//int value of str, -1 if not a value

    int numDigits;

    int numChars;

    int length;//length of str.trim()

    double mostSimMonthLiteralScore;//biggest similarity to all month literals
    String mostSimMonthLiteral;

    public static DateTokenFeatures fromString(final String str){
        final String trim = str.trim();
        int intValue = INVALID;
        try{
            intValue = Integer.valueOf(trim);
        }catch(Exception e){
            intValue = INVALID;
        }
        final int[] digitsChars = StringCommon.countDigitAndChars(trim);

        return new DateTokenFeatures(
                trim,
                intValue,
                digitsChars[0],
                digitsChars[1],
                str.trim().length(),
                monthLiterals.mostSimilarMonthLiteralScore(trim),
                monthLiterals.mostSimilarMonthLiteral(trim));
    }

    @Override
    public int size() {
        return 6;
    }
}
