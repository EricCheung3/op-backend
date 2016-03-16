package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    /**
     * is a literal month date format
     * @param str
     * @return
     */
    //TODO in the future this can parse the date out, should replace Regular Expression
    public boolean isLiteralMonthFormat(final String str) {
        log.debug("format="+format);
        final String nonSpaceLower = StringCommon.removeAllSpaces(str).toLowerCase();
        final List<String> literalMonths = MONTH_LITERALS
        .monthLiterals()
        .stream()
        .filter(m -> nonSpaceLower.contains(m.toLowerCase()))
        .collect(Collectors.toList());

        if ( literalMonths.size() <=0 )
            return false;

        final String matchedMonth = literalMonths.get(0); //just get the first matched literal month
        if(format ==  DateStringFormat.LiteralMonthDayYear2 ) {
            int monthStarts = nonSpaceLower.indexOf(matchedMonth);
            final String afterLiteralMonth = str.substring(monthStarts + matchedMonth.length());
            log.debug("afterLiteralMonth="+afterLiteralMonth);
            final int[] digitsAfterLiteralMonths = StringCommon.countDigitAndAlphabets(afterLiteralMonth);
            return digitsAfterLiteralMonths[0] >= 3;
        }

        if(format ==  DateStringFormat.LiteralMonthDayYear4 ) {
            int monthStarts = nonSpaceLower.indexOf(matchedMonth);
            final String afterLiteralMonth = str.substring(monthStarts + matchedMonth.length());
            log.debug("afterLiteralMonth="+afterLiteralMonth);
            final int[] digitsAfterLiteralMonths = StringCommon.countDigitAndAlphabets(afterLiteralMonth);
            return digitsAfterLiteralMonths[0] >= 5;
        }

        if(format ==  DateStringFormat.DayLiteralMonthYear2 ) {
            int monthStarts = nonSpaceLower.indexOf(matchedMonth);
            final String beforeLiteralMonth = str.substring(0, monthStarts);
            log.debug("beforeLiteralMonth="+beforeLiteralMonth);
            final int[] digitsBeforeLiteralMonths = StringCommon.countDigitAndAlphabets(beforeLiteralMonth);

            final String afterLiteralMonth = str.substring(monthStarts + matchedMonth.length());
            log.debug("afterLiteralMonth="+afterLiteralMonth);
            final int[] digitsAfterLiteralMonths = StringCommon.countDigitAndAlphabets(beforeLiteralMonth);
            return digitsBeforeLiteralMonths[0] >= 1 && digitsAfterLiteralMonths[0] >= 2;
        }


        if(format ==  DateStringFormat.DayLiteralMonthYear4 ) {
            int monthStarts = nonSpaceLower.indexOf(matchedMonth);
            final String beforeLiteralMonth = str.substring(0, monthStarts);
            log.debug("beforeLiteralMonth="+beforeLiteralMonth);
            final int[] digitsBeforeLiteralMonths = StringCommon.countDigitAndAlphabets(beforeLiteralMonth);

            final String afterLiteralMonth = str.substring(monthStarts + matchedMonth.length());
            log.debug("afterLiteralMonth="+afterLiteralMonth);
            final int[] digitsAfterLiteralMonths = StringCommon.countDigitAndAlphabets(afterLiteralMonth);
            log.debug("digitsBeforeLiteralMonths[0]="+digitsBeforeLiteralMonths[0]);
            log.debug("digitsAfterLiteralMonths[0]="+digitsAfterLiteralMonths[0]);
            return digitsBeforeLiteralMonths[0] >= 1 && digitsAfterLiteralMonths[0] >= 4;
        }

        return false;
    }

    @Override
    public LocalDateFeatures parseWithSpaces(String origLine) {
        if (isLiteralMonthFormat(origLine)) {
            return selectAccordingToWideSpace(origLine, getDateSubString(origLine), format);
        }
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
//          log.warn(e.getMessage());
      }
      return null;
    }

    //split "Jan 19  1 5" into list: Jan, 08, 2015
    public static List<String> formatMonthDayYearToList(final String dateString, final int numYearDigits) {
        if(numYearDigits != 2 && numYearDigits != 4)
            throw new RuntimeException("numYearDigits must be 2 or 4");
        final String noSpaceNoSplitter = StringCommon.removeAllSpaces(dateString).replaceAll("\\s+|\\.|_|-|,|\\s|’|'", "");
        if(noSpaceNoSplitter.isEmpty()) return null;
        final String yearDigits = StringCommon.lastDigits(noSpaceNoSplitter, numYearDigits);
//        log.debug("yearDigits="+yearDigits);
        final int indexOfYear = noSpaceNoSplitter.lastIndexOf(yearDigits);
        String dayDigits = "";
        String literalMonth = "";
        if(indexOfYear > 0) {
            final String literalMonthDay = noSpaceNoSplitter.substring(0, indexOfYear);
//            log.debug("literalMonthDay="+literalMonthDay);
            dayDigits = StringCommon.lastContinuousDigitChunk(literalMonthDay, 2);
//            log.debug("dayDigits="+dayDigits);
            if(!dayDigits.isEmpty()) {
                final int startOfMonth = 0;
                literalMonth = noSpaceNoSplitter.substring(startOfMonth, noSpaceNoSplitter.lastIndexOf(dayDigits));
//                log.debug("literalMonth="+literalMonth);
            }
        }
        return Arrays.asList(new String[]{literalMonth, dayDigits, yearDigits});
    }
}
