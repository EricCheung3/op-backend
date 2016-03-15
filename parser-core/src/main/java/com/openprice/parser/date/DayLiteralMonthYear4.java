package com.openprice.parser.date;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DayLiteralMonthYear4 extends LiteralMonthParser {

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


    /**
     * @param dateString like "9-Feb-2015"
     * @param numYearDigits how many digits in year format (2 or 4)
     * @return list = Feb, 9, 2015
     */
    @Override
    public List<String> splitToLiteralMonthDayYear4(final String dateString){
        final String noSpaceNoSplitter = StringCommon.removeAllSpaces(dateString).replaceAll("\\s+|\\.|_|-|,|\\s|’|'", "");
        if(noSpaceNoSplitter.isEmpty()) return null;
        log.debug("noSpaceNoSplitter="+noSpaceNoSplitter);
        final String yearDigits = StringCommon.lastContinuousDigitChunk(noSpaceNoSplitter, 4);//TODO should actually use last consecutive digits
        log.debug("yearDigits="+yearDigits);
        final int indexOfYear = noSpaceNoSplitter.lastIndexOf(yearDigits);
        String dayDigits = "";
        String literalMonth = "";
        if(indexOfYear > 0) {
            final String dayAndLiteralMonth = noSpaceNoSplitter.substring(0, indexOfYear);
            dayDigits = StringCommon.firstContinuousDigitChunkBetween(dayAndLiteralMonth, 0, dayAndLiteralMonth.length(), 2);
            if( !dayDigits.isEmpty()) {
                final int startOfMonth = noSpaceNoSplitter.indexOf(dayDigits) + dayDigits.length();
                literalMonth = noSpaceNoSplitter.substring(startOfMonth,  indexOfYear);
                log.debug("literalMonth="+literalMonth);
            }
        }
        return Arrays.asList(new String[]{literalMonth, dayDigits, yearDigits});

    }
}
