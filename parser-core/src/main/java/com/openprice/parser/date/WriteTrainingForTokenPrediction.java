package com.openprice.parser.date;

import java.util.ArrayList;

import antlr.collections.List;

/**
 * write training data for token prediction
 */
public class WriteTrainingForTokenPrediction {

    private static final int DAY_CODE = 1;
    private static final int MONTH_CODE = 1;

    public static void main(String[] args) {
        writeDays();
        writeMonths();
        writeYears();
        writeDayOrMonth();
        writeDayOrMonthOrYear();
        writeUnpredictable();
    }

    public static writeDays() {
        final List<String> dayLines = new ArrayList<>();
        for(int i=13; i<=31; i++){
            dayLines.add(i+"");
        }
    }
}
