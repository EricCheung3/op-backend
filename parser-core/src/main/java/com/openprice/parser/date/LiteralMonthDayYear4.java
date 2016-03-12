package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;
import com.openprice.parser.date.ml.StringGeneralFeatures;

import lombok.extern.slf4j.Slf4j;

/**
 * "literal month day year" format
 * day could be one or two digits
 * year is four digits
 */
@Slf4j
public class LiteralMonthDayYear4 implements DateParser{

    //format like "Feb 9, 2015"
    private static Pattern pattern = Pattern.compile(
            LiteralMonthDayYear2.LITERAL_MONTH +
                LiteralMonthDayYear2.SPLITTER +
            DateParserRegularExpression.DAY_MONTH_PATTERN +
                LiteralMonthDayYear2.SPLITTER +
            DateParserRegularExpression.YEAR_4_PATTERN
            );

    @Override
    public LocalDateFeatures parseWithSpaces(String origLine) {
        final String literalMDY4 = DateParserUtils.pruneDateStringWithMatch(origLine, pattern);
        final List<String> words = literalMonthDayYearSplit(literalMDY4);
//        log.debug("words.size()="+words.size());
//        for(String str: words)
//            log.debug(str);
        if(words.size() < 3)
            return null;
        try{
            LocalDate date = DateUtils.fromDayMonthYear(
                    words.get(1),
                    DateParserUtils.getMonthLiterals().getMonthNumber(words.get(0))+"",
                    words.get(2)
                    );
            if(date != null)
                return new LocalDateFeatures(
                    date,
                    DateStringFormat.LiteralMonthDayYear4,
                    literalMDY4,
                    StringGeneralFeatures.fromString(literalMDY4),
                    DateStringFeatures.fromString(literalMDY4));
        }catch(Exception e){
            log.warn(e.getMessage());
        }
        return null;
    }

    //TODO similar to DataParserUtils.getMeaningfulWords?
    public static List<String> literalMonthDayYearSplit(final String dateString){
//      final String[] words = dateString.split("-|_|\\.|\\s+");//not correct. only one dilimiter is selected
//      http://stackoverflow.com/questions/3654446/java-regex-help-splitting-string-on-spaces-and-commas
      final String[] words = dateString.split("\\s*(\\.|_|-|,|\\s|’|')\\s*");
//      log.debug("dateString="+dateString+", words.length="+words.length);
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
