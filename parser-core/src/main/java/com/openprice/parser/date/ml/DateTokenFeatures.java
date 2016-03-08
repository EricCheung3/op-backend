package com.openprice.parser.date.ml;

import com.openprice.parser.ml.data.Features;

public class DateTokenFeatures implements Features {

    String str;//original String

    int intValue;//int value of str, -1 if not a value

    int numDigits;

    int numChars;

    double simToMonthLiteral;//likelihood of being a month literal

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }
}
