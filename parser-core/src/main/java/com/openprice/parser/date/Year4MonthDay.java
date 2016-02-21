package com.openprice.parser.date;

import java.util.regex.Pattern;

/**
 * "year (4-digit) month day" format
 */

public class Year4MonthDay implements DateParser{

    //4-digit year, month(one  two digits) and day (two digits)
    private static Pattern patternYear4MonthDay2=Pattern.compile("(19|20)\\d\\d["+DATE_SPLITTER+"]([1-9]|0[1-9]|1[012])["+DATE_SPLITTER+"](0[1-9]|[12][0-9]|3[01])");

    @Override
    public String parse(String line) {
        return null;
    }


}
