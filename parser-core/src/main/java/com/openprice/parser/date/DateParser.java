package com.openprice.parser.date;

import java.util.Calendar;

/**
 * parse a line to get Date String
 */

public interface DateParser {

    Calendar parse(String line);
}
