package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import lombok.Getter;

public class DayMonthYear2 extends DateParserRegularExpression{

    // day (one or two digits), month(one or two digits), 4-digit year
     @Getter
     public static Pattern pattern = Pattern.compile(
             DAY_MONTH_PATTERN +
                 "[" + DateConstants.DATE_SPLITTERS+ "]" +
             DAY_MONTH_PATTERN +
                 "[" + DateConstants.DATE_SPLITTERS + "]" +
             YEAR_2_PATTERN
             );

      @Override
      public LocalDateFeatures parseWithSpaces(final String line) {
          return selectAccordingToWideSpace(line, getDateSubString(line), DateStringFormat.DayMonthYear2);
      }

      @Override
      public LocalDate parseToDate(final String dateStr) {
          final String[] mDY2 = dateStr.split("[" + DateConstants.DATE_SPLITTERS +"]");
          if(mDY2.length < 3)
              return null;
          final List<String> cleanWords = DateParserUtils.getMeaningfulDateWords(mDY2);
          return DateUtils.fromDayMonthYear(
                  cleanWords.get(0),
                  cleanWords.get(1),
                  "20" + cleanWords.get(2)
                  );
      }

    @Override
    public String getDateSubString(String str) {
        return DateParserUtils.pruneDateStringWithMatch(str, pattern);
    }

}
