package com.openprice.parser.date;

/**
 * parse a line to get Date String
 */

public interface DateParser {

    LocalDateFeatures parseWithSpaces(String line);

    //get the date substring from the line
    String getDateSubString(final String line);
}
