package com.openprice.parser.date;


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
public class LiteralMonthDayYear2 extends LiteralMonthParser{

    //http://stackoverflow.com/questions/2655476/regex-to-match-month-name-followed-by-year
    public static final String LITERAL_MONTH = "\\b(?:Jan(?:uary)?|Feb(?:ruary)?||Mar(?:ch)?||Apr(?:il)?||May?"
            +"||JAN(?:UARY)?|FEB(?:RUARY)?||MAR(?:CH)?||APR(?:IL)?||MAY?"
            +"||Jun(?:e)?||Jul(?:y)?||Aug(?:ust)?||Sep(?:tember)?||Oct(?:ober)?||Nov(?:ember)?||Dec(?:ember)?"
            +"||JUN(?:E)?||JUL(?:Y)?||AUG(?:UST)?||SEP(?:TEMBER)?||OCT(?:OBER)?||NOV(?:EMBER)?||DEC(?:EMBER)?)";

  //TODO this repeats with DataConstants.DATE_SPLITTERS
    public static final String SPLITTER = "\\s*" + "(\\s*||,||\\.||_||'||’||-)"  + "\\s*";

    private static Pattern pattern = Pattern.compile(
          LITERAL_MONTH +
          SPLITTER +
          DateParserRegularExpression.DAY_MONTH_PATTERN +
          SPLITTER +
          DateParserRegularExpression.YEAR_2_PATTERN
          );

    public LiteralMonthDayYear2() {
        super(pattern, DateStringFormat.LiteralMonthDayYear2);
    }

    @Override
    public List<String> splitToLiteralMonthDayYear4(final String dateString) {
        final List<String> result = LiteralMonthParser.splitToLiteralMonthDayYear2OrYear4(dateString, 2);
        if(result == null || result.size() <3 )
            return null;
        result.set(2, "20"+result.get(2));//add "20" before year digits like "15"
        return result;

//      final String[] words = dateString.split("-|_|\\.|\\s+");//not correct. only one dilimiter is selected
//      http://stackoverflow.com/questions/3654446/java-regex-help-splitting-string-on-spaces-and-commas
//      final String[] words = dateString.split("\\s*(\\.|_|-|,|\\s|’|')\\s*");
//      final List<String> cleanWords = DateParserUtils.getMeaningfulDateWords(words);
//      if(cleanWords.size()==4
//              && cleanWords.get(2).length() == 1
//              && cleanWords.get(3).length() ==1){
//          //merge the last two list
//          cleanWords.set(2, cleanWords.get(2) + cleanWords.get(3));
//          cleanWords.remove(3);
//      }
//      //log.debug("dateString="+dateString+", cleanWords="+cleanWords);
//      final List<String> list = new ArrayList<>();
//      for(String w : words){
//          if(w.length()==1
//              && !Character.isDigit(w.charAt(0))
//              && !Character.isLetter(w.charAt(0)))
//              continue;
//          list.add(StringCommon.removeAllSpaces(w));
//      }
//      return list;
//      return cleanWords;
  }

//    @Override
//    public LocalDate parseToDate(final String literalMDY2) {
//       final List<String> words = literalMonthDayYearSplit(literalMDY2, 2);
//       if(words == null) return null;
//       //log.debug("words.size()="+words.size());
//       for(String str: words)
//           //log.debug(str);
//       if(words.size() < 3)
//          return null;
//       try{
//          return DateUtils.fromDayMonthYear(
//                  words.get(1),
//                  DateParserUtils.getMonthLiterals().getMonthNumber(words.get(0))+"",
//                  "20" + words.get(2)
//                  );
//       }catch(Exception e){
//          //log.warn(e.getMessage());
//       }
//       return null;
//    }


}
