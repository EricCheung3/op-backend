package com.openprice.parser.date.ml;

/**
 * predict a string token like "Feb" is a day, month or year
 */
public interface DateTokenPredictor {

    DateTokenType predict(final String token);
}
