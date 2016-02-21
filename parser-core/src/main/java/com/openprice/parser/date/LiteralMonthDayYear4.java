package com.openprice.parser.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

import lombok.extern.slf4j.Slf4j;

/**
 * "literal month day year" format
 * day could be one or two digits
 * year is four digits
 */
@Slf4j
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
    public Calendar parse(String origLine) {
        final String literalMDY4 = DateParserUtils.pruneDateStringWithMatch(origLine,
                patternLiteralMonthDayYear4);
        final List<String> words = literalMonthDayYearSplit(literalMDY4);
        log.debug("words.length="+words.size());
        for(String str: words)
            log.debug(str);
        return DateParserUtils.getCalendar(words.get(1),
                DateParserUtils.getMonthLiterals().getMonthNumber(words.get(0))+"",
                words.get(2));
    }

    public static List<String> literalMonthDayYearSplit(final String dateString){
//      final String[] words = dateString.split("-|_|\\.|\\s+");//not correct. only one dilimiter is selected
//      http://stackoverflow.com/questions/3654446/java-regex-help-splitting-string-on-spaces-and-commas
      final String[] words = dateString.split("\\s*(\\.|_|-|,|\\s)\\s*");
//      log.debug("words.length="+words.length);
      final List<String> list = new ArrayList<>();
      for(String w : words){
          if(w.length()==1
              && !Character.isDigit(w.charAt(0))
              && !Character.isLetter(w.charAt(0)))
              continue;
          list.add(StringCommon.removeAllSpaces(w));
      }
      return list;
  }
}
