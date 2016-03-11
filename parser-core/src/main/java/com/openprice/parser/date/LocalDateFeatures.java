package com.openprice.parser.date;

import java.time.LocalDate;

import com.openprice.parser.date.ml.StringGeneralFeatures;

import lombok.Value;

/**
 * features for local date
 */
@Value
public class LocalDateFeatures {
    final LocalDate date;

    //feature: format
    final DateStringFormat format;

    final String dateString;//date string from which the date is parsed
    final StringGeneralFeatures dateStringFeatures;
}
