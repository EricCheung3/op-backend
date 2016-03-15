package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class LiteralMonthParser extends DateParserRegularExpression {

    //format like "Feb 9, 2015"
    private final Pattern pattern;
    private final DateStringFormat format;

    public LiteralMonthParser(final Pattern pattern, final DateStringFormat format) {
        this.pattern = pattern;
        this.format = format;
    }

    @Override
    public LocalDateFeatures parseWithSpaces(String origLine) {
        final String nonSpaceLower = StringCommon.removeAllSpaces(origLine).toLowerCase();
        if(MONTH_LITERALS.monthLiterals().stream().anyMatch(m -> nonSpaceLower.contains(m.toLowerCase())))
            return selectAccordingToWideSpace(origLine, getDateSubString(origLine), format);
        return null;
    }

    @Override
    public String getDateSubString(String line) {
        return DateParserUtils.pruneDateStringWithMatch(line, pattern);
    }

    //split a date string and format into a string list: literalMonth, day, Year
    abstract List<String> splitToLiteralMonthDayYear4(String dateString);

    @Override
    public LocalDate parseToDate(final String literalMDY2) {
       final List<String> words = splitToLiteralMonthDayYear4(literalMDY2);
       if(words == null) return null;
//       log.debug("words.size()="+words.size());
//       for(String str: words)
//           log.debug(str);
       if(words.size() < 3)
           return null;
       try{
           return DateUtils.fromDayMonthYear(
                  words.get(1),
                  DateParserUtils.getMonthLiterals().getMonthNumber(words.get(0))+"",
                  words.get(2)
                  );
       }catch(Exception e){
//           e.printStackTrace();
          log.warn(e.getMessage());
      }
      return null;
    }

    //split "Jan 19  1 5" into list: Jan, 08, 2015
    public static List<String> splitToLiteralMonthDayYear2OrYear4(final String dateString, final int numYearDigits) {
        if(numYearDigits != 2 && numYearDigits != 4)
            throw new RuntimeException("numYearDigits must be 2 or 4");
        final String noSpaceNoSplitter = StringCommon.removeAllSpaces(dateString).replaceAll("\\s+|\\.|_|-|,|\\s|’|'", "");
        if(noSpaceNoSplitter.isEmpty()) return null;
        final String yearDigits = StringCommon.lastDigits(noSpaceNoSplitter, numYearDigits);
        log.debug("yearDigits="+yearDigits);
        final int indexOfYear = noSpaceNoSplitter.lastIndexOf(yearDigits);
        String dayDigits = "";
        String literalMonth = "";
        if(indexOfYear > 0) {
            final String literalMonthDay = noSpaceNoSplitter.substring(0, indexOfYear);
//            log.debug("literalMonthDay="+literalMonthDay);
            dayDigits = StringCommon.lastContinuousDigitChunk(literalMonthDay, 2);
            log.debug("dayDigits="+dayDigits);
            if(!dayDigits.isEmpty()) {
                final int startOfMonth = 0;
                literalMonth = noSpaceNoSplitter.substring(startOfMonth, noSpaceNoSplitter.lastIndexOf(dayDigits));
                log.debug("literalMonth="+literalMonth);
            }
        }
        return Arrays.asList(new String[]{literalMonth, dayDigits, yearDigits});
    }
}
