package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

import lombok.Getter;

public class DayMonthYear2 implements DateParser{

    // day (one or two digits), month(one or two digits), 4-digit year
     @Getter
     public static Pattern pattern = Pattern.compile(
              "\\d{1,2}\\s*[" + DateConstants.DATE_SPLITTERS
              + "]\\s*\\d{1,2}\\s*[" + DateConstants.DATE_SPLITTERS+"]\\s*\\d\\s*\\d\\s*"
              );

      @Override
      public LocalDate parseNoSpaces(final String line) {
          final String dateStr=DateParserUtils.pruneDateStringWithMatch(StringCommon.removeAllSpaces(line), pattern);
          return parseToDate(dateStr);
      }

      @Override
      public LocalDate parseWithSpaces(final String line) {
          final String dateStr=DateParserUtils.pruneDateStringWithMatch(line, pattern);
          return parseToDate(dateStr);
      }

      private static LocalDate parseToDate(final String dateStr) {
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

}
