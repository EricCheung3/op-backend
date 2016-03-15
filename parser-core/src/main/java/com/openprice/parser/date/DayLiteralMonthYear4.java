package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DayLiteralMonthYear4 extends LiteralMonthDateParser {

    //format like "9-Feb-2015"
    private static final Pattern pattern = Pattern.compile(
            DAY_MONTH_PATTERN +
                LiteralMonthDayYear2.SPLITTER +
            LiteralMonthDayYear2.LITERAL_MONTH +
                LiteralMonthDayYear2.SPLITTER +
            YEAR_4_PATTERN
            );

    public DayLiteralMonthYear4(){
        super(pattern, DateStringFormat.DayLiteralMonthYear4);
    }

    @Override
    public LocalDate parseToDate(final String literalMDY2) {
       final List<String> words = dayLiteralMonthYearSplit(literalMDY2, 4);
       if(words == null) return null;
//       log.debug("words.size()="+words.size());
       for(String str: words)
           log.debug(str);
       if(words.size() < 3)
           return null;
       try{
           return DateUtils.fromDayMonthYear(
                  words.get(0),
                  DateParserUtils.getMonthLiterals().getMonthNumber(words.get(1))+"",
                  words.get(2)
                  );
       }catch(Exception e){
          log.warn(e.getMessage());
      }
      return null;
    }

    //TODO just copied
    public static List<String> dayLiteralMonthYearSplit(final String dateString, final int numYearDigits){
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
}
