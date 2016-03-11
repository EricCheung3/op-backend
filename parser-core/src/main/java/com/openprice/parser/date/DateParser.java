package com.openprice.parser.date;

/**
 * parse a line to get Date String
 */

public interface DateParser {

    LocalDateFeatures parseWithSpaces(String line);

//    LocalDate parseNoSpaces(String lineWithNoSpace);
}
