package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * "literal month day year" format
 * day could be one or two digits
 * year is four digits
 */
//format like "OCT.08’ 15"
//TODO also inherit DateParserRegularExpression
@Slf4j
public class LiteralMonthDayYear2 extends DateParserRegularExpression{

    //http://stackoverflow.com/questions/2655476/regex-to-match-month-name-followed-by-year
    public static final String LITERAL_MONTH = "\\b(?:Jan(?:uary)?|Feb(?:ruary)?||Mar(?:ch)?||Apr(?:il)?||May?"
            +"||JAN(?:UARY)?|FEB(?:RUARY)?||MAR(?:CH)?||APR(?:IL)?||MAY?"
            +"||Jun(?:e)?||Jul(?:y)?||Aug(?:ust)?||Sep(?:tember)?||Oct(?:ober)?||Nov(?:ember)?||Dec(?:ember)?"
            +"||JUN(?:E)?||JUL(?:Y)?||AUG(?:UST)?||SEP(?:TEMBER)?||OCT(?:OBER)?||NOV(?:EMBER)?||DEC(?:EMBER)?)";

  //TODO this repeats with DataConstants.DATE_SPLITTERS
    public static final String SPLITTER = "\\s*" + "(\\s*||,||\\.||_||'||’||-)"  + "\\s*";

    private static Pattern pattern=Pattern.compile(
          LITERAL_MONTH +
          SPLITTER +
          DateParserRegularExpression.DAY_MONTH_PATTERN +
          SPLITTER +
          DateParserRegularExpression.YEAR_2_PATTERN
          );

    @Override
    public LocalDateFeatures parseWithSpaces(String origLine) {
        return selectAccordingToWideSpace(origLine, pattern, DateStringFormat.LiteralMonthDayYear2);
    }

    //TODO similar to DataParserUtils.getMeaningfulWords?
    public static List<String> literalMonthDayYearSplit(final String dateString){
//      final String[] words = dateString.split("-|_|\\.|\\s+");//not correct. only one dilimiter is selected
//      http://stackoverflow.com/questions/3654446/java-regex-help-splitting-string-on-spaces-and-commas
      final String[] words = dateString.split("\\s*(\\.|_|-|,|\\s|’|')\\s*");
      final List<String> cleanWords = DateParserUtils.getMeaningfulDateWords(words);
//      log.debug("dateString="+dateString+", words.length="+words.length);
//      final List<String> list = new ArrayList<>();
//      for(String w : words){
//          if(w.length()==1
//              && !Character.isDigit(w.charAt(0))
//              && !Character.isLetter(w.charAt(0)))
//              continue;
//          list.add(StringCommon.removeAllSpaces(w));
//      }
//      return list;
      return cleanWords;
  }

    @Override
    public LocalDate parseToDate(final String literalMDY2) {
        final List<String> words = literalMonthDayYearSplit(literalMDY2);
      log.debug("words.size()="+words.size());
      for(String str: words)
          log.debug(str);
      if(words.size() < 3)
          return null;
      try{
          return DateUtils.fromDayMonthYear(
                  words.get(1),
                  DateParserUtils.getMonthLiterals().getMonthNumber(words.get(0))+"",
                  "20" + words.get(2)
                  );
      }catch(Exception e){
          log.warn(e.getMessage());
      }
      return null;
    }

}
