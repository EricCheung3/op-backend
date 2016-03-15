package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

/**
 * "literal month day year" format
 * day could be one or two digits
 * year is four digits
 */
//format like "OCT.08’ 15"
//TODO also inherit DateParserRegularExpression
//@Slf4j
public class LiteralMonthDayYear2RE extends DateParserRegularExpression{

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

    @Override
    public LocalDateFeatures parseWithSpaces(String origLine) {
        final String nonSpaceLower = StringCommon.removeAllSpaces(origLine).toLowerCase();
//        System.out.println(MONTH_LITERALS.monthLiterals().stream().anyMatch(m -> nonSpaceLower.contains(m.toLowerCase())));
        if(MONTH_LITERALS.monthLiterals().stream().anyMatch(m -> nonSpaceLower.contains(m.toLowerCase())))
            return selectAccordingToWideSpace(origLine, getDateSubString(origLine), DateStringFormat.LiteralMonthDayYear2);
        return null;
    }

    //TODO similar to DataParserUtils.getMeaningfulWords?
    public static List<String> literalMonthDayYearSplit(final String dateString, final int numYearDigits){
        //Why ’ cannot be replaced?
        final String noSpaceNoSplitter = StringCommon.removeAllSpaces(dateString).replaceAll("\\s+|\\.|_|-|,|\\s|’|'", "");
        if(noSpaceNoSplitter.isEmpty()) return null;
        //log.debug("noSpaceNoSplitter="+noSpaceNoSplitter);
        final String yearDigits = StringCommon.lastDigits(noSpaceNoSplitter, numYearDigits);
        //log.debug("yearDigits="+yearDigits);
        final int indexOfYear = noSpaceNoSplitter.lastIndexOf(yearDigits);
        String dayDigits = "";
        String literalMonth = "";
        if(indexOfYear > 0) {
            final String literalMonthDay = noSpaceNoSplitter.substring(0, indexOfYear);
            //log.debug("literalMonthDay="+literalMonthDay);
            dayDigits = StringCommon.lastDigits(literalMonthDay, 2);
            //log.debug("dayDigits="+dayDigits);
            if(!dayDigits.isEmpty()) {
                literalMonth = noSpaceNoSplitter.substring(0, noSpaceNoSplitter.length() - yearDigits.length() - dayDigits.length());
            }
        }
        return Arrays.asList(new String[]{literalMonth, dayDigits, yearDigits});

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

    @Override
    public LocalDate parseToDate(final String literalMDY2) {
       final List<String> words = literalMonthDayYearSplit(literalMDY2, 2);
       if(words == null) return null;
       //log.debug("words.size()="+words.size());
       for(String str: words)
           //log.debug(str);
       if(words.size() < 3)
          return null;
       try{
          return DateUtils.fromDayMonthYear(
                  words.get(1),
                  DateParserUtils.getMonthLiterals().getMonthNumber(words.get(0))+"",
                  "20" + words.get(2)
                  );
       }catch(Exception e){
          //log.warn(e.getMessage());
       }
       return null;
    }

    @Override
    public String getDateSubString(String line) {
        return DateParserUtils.pruneDateStringWithMatch(line, pattern);
    }

}
