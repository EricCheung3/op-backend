package com.openprice.parser.date;

import java.util.regex.Pattern;

/**
 * "literal month day year" format
 * day could be one or two digits
 * year is four digits
 */
public class LiteralMonthDayYear4 implements DateParser{

    //format like "Feb 9, 2015"
    //http://stackoverflow.com/questions/2655476/regex-to-match-month-name-followed-by-year
    private static Pattern patternLiteralMonthDayYear4=Pattern.compile(
//            "\\b(?:Jan(?:uary)?|Feb(?:ruary)?||Mar(?:ch)?||Apr(?:il)?||May?"
//            +"||Jun(?:e)?||Jul(?:y)?||Aug(?:ust)?||Sep(?:tember)?||Oct(?:ober)?||Nov(?:ember)?||Dec(?:ember)?) (?:19[7-9]\\d|2\\d{3})(?=\\D|$)");
            "\\b(?:Jan(?:uary)?|Feb(?:ruary)?||Mar(?:ch)?||Apr(?:il)?||May?"
          +"||Jun(?:e)?||Jul(?:y)?||Aug(?:ust)?||Sep(?:tember)?||Oct(?:ober)?||Nov(?:ember)?||Dec(?:ember)?)"
          + "\\s*"
          + "([1-9]|0[1-9]|[12][0-9]|3[01])"
          + "\\s*"
          + "(\\s*||,||\\.||_)"
          + "\\s*"
          + "(?:19[7-9]\\d|2\\d{3})(?=\\D|$)");

    @Override
    public Date parse(String origLine) {
        return DateParserUtils.pruneDateStringWithMatch(origLine, patternLiteralMonthDayYear4);
    }

}
