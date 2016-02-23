package com.openprice.parser.date;

import java.time.LocalDate;

/**
 * parse a line to get Date String
 */

public interface DateParser {

    LocalDate parseWithSpaces(String line);

    LocalDate parseNoSpaces(String lineWithNoSpace);
}
