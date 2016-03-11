package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.openprice.parser.date.ml.StringGeneralFeatures;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MonthDayYear2 implements DateParser{

    //month(one or two digits) and day (one or two digits), 2-digit year
    public static Pattern patternMonthDayYear2= Pattern.compile(
            Year4MonthDay.DAY_MONTH_PATTERN
                + "["+ DateConstants.DATE_SPLITTERS + "]" +
            Year4MonthDay.DAY_MONTH_PATTERN
                + "["+ DateConstants.DATE_SPLITTERS +"]" +
            Year2MonthDay.YEAR_2_PATTERN
            );


    @Override
    public LocalDateFeatures parseWithSpaces(String line) {
        final String dateStr=DateParserUtils.pruneDateStringWithMatch(line, patternMonthDayYear2);
        final LocalDateFeatures fromWholeString = parseToFeatures(dateStr);
//        log.debug("localDate="+localDate);
        if(fromWholeString != null && !fromWholeString.getDateStringFeatures().isContainsWideSpace()){
           log.warn("parsing from whole string success.");
           return fromWholeString;
        }
        final DateStringFeatures wholeStringFeatures = DateStringFeatures.fromString(dateStr);
        final LocalDateFeatures fromBeforeSpace = parseToFeatures(wholeStringFeatures.getBeforeWideSpace());
        if(fromBeforeSpace != null){
            log.warn("parsing from before string success.");
            return fromBeforeSpace;
        }
        final LocalDateFeatures fromAfterSpace = parseToFeatures(wholeStringFeatures.getAfterWideSpace());
//        if(fromAfterSpace != null){
            log.warn("parsing from after string success.");
//        }
        return fromAfterSpace;
    }

    public static LocalDateFeatures parseToFeatures(final String str){
        final LocalDate date = parseToDate(str);
        if(date != null)
            return new LocalDateFeatures(
                date,
                DateStringFormat.MonthDayYear2,
                str,
                StringGeneralFeatures.fromString(str),
                DateStringFeatures.fromString(str)
                );
        return null;
    }

    private static LocalDate parseToDate(final String dateStr) {
        log.debug("dateStr="+dateStr);
        final String[] mDY2 = dateStr.split("[" + DateConstants.DATE_SPLITTERS +"]");
        if(mDY2.length < 3)
            return null;
        final List<String> clean = DateParserUtils.getMeaningfulDateWords(mDY2);
        return DateUtils.fromDayMonthYear(
                clean.get(1).trim(),
                clean.get(0).trim(),
                "20" + clean.get(2).trim()
                );
    }



}
