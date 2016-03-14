package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * "literal month day year" format
 * day could be one or two digits
 * year is four digits
 *
 * This one can take 1-4 seconds for a simple line like  "TOTAL                                                  85.50"
 The bottleneck is the matcher.matches
 */
@Slf4j
public class LiteralMonthDayYear4RE  extends DateParserRegularExpression {

    //format like "Feb 9, 2015"
    private static final Pattern pattern = Pattern.compile(
            LiteralMonthDayYear2RE.LITERAL_MONTH +
                LiteralMonthDayYear2RE.SPLITTER +
            DAY_MONTH_PATTERN +
                LiteralMonthDayYear2RE.SPLITTER +
            YEAR_4_PATTERN
            );

    @Override
    public LocalDateFeatures parseWithSpaces(String origLine) {
        return selectAccordingToWideSpace(origLine, getDateSubString(origLine), DateStringFormat.LiteralMonthDayYear4);
    }

    @Override
    public LocalDate parseToDate(final String literalMDY2) {
       final List<String> words = LiteralMonthDayYear2RE.literalMonthDayYearSplit(literalMDY2, 4);
       if(words == null) return null;
//       log.debug("words.size()="+words.size());
       for(String str: words)
           log.debug(str);
       if(words.size() < 3)
           return null;
       try{
           return DateUtils.fromDayMonthYear(
                  words.get(1),
                  DateParserUtils.getMonthLiterals().getMonthNumber(words.get(0))+"",
                  words.get(2)
                  );
       }catch(Exception e){
          log.warn(e.getMessage());
      }
      return null;
    }

    @Override
    public String getDateSubString(String line) {
        return DateParserUtils.pruneDateStringWithMatch(line, pattern);
    }

}
