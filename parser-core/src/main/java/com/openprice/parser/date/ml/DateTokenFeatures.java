package com.openprice.parser.date.ml;

import com.openprice.common.StringCommon;
import com.openprice.parser.date.MonthLiterals;
import com.openprice.parser.ml.data.Features;

import lombok.Value;

@Value
public class DateTokenFeatures implements Features {

    private final static int INVALID = -1;
    private final static MonthLiterals MONTH_LITERALS= new MonthLiterals();

    String str;//original String

    int intValue;//int value of str, -1 if not a value

    int numDigits;

    int numChars;

    int trimLength;//length of str.trim()

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
                MONTH_LITERALS.mostSimilarMonthLiteralScore(trim),
                MONTH_LITERALS.mostSimilarMonthLiteral(trim));
    }

    @Override
    public int size() {
        return 6;
    }

    public String toString(final String splitter) {
        return  intValue + splitter +
                numDigits + splitter +
                numChars + splitter +
                trimLength+ splitter +
                mostSimMonthLiteral + splitter+
                mostSimMonthLiteralScore;

    }

    public String toString(final String featureSplitter, final String valueSplitter) {
        return  formatFeatureValue(1, intValue+"", valueSplitter)   + featureSplitter +
                formatFeatureValue(2, numDigits+"", valueSplitter)  + featureSplitter +
                formatFeatureValue(3, numChars+"", valueSplitter)   + featureSplitter +
                formatFeatureValue(4, trimLength+"", valueSplitter)  + featureSplitter +
                formatFeatureValue(5, mostSimMonthLiteralScore+"", valueSplitter) ;
    }

    public String formatFeatureValue(int index, final String value, final String splitter) {
        return index + splitter + value;
    }
}
