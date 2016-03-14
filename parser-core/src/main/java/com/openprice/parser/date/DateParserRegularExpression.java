package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;
import com.openprice.parser.date.ml.StringGeneralFeatures;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DateParserRegularExpression implements DateParser{

    //pattern for month and day
//    public final static String DAY_MONTH_PATTERN = "\\s*(0?\\s*\\d|\\d\\s*\\d|\\d{1,2})\\s*";
    public final static String DAY_MONTH_PATTERN = "\\s*(\\d\\s*\\d|\\d{1,2})\\s*";
    public final static String YEAR_4_PATTERN = "\\s*(1\\s*9|2\\s*0)\\s*\\d\\s*\\d\\s*";
    public final static String YEAR_2_PATTERN = "\\s*\\d\\s*\\d\\s*";//"\\d\\d";//"(\\d{2})(?=\\D|$)");

    public abstract LocalDate parseToDate(final String dateStr);

    public LocalDateFeatures parseToFeatures(final String str, DateStringFormat format){
        final LocalDate date = parseToDate(str);
        if(date != null)
            return new LocalDateFeatures(
                date,
                format,
                str,
                StringGeneralFeatures.fromString(str),
                DateStringFeatures.fromString(str)
                );
        return null;
    }

    public LocalDateFeatures selectAccordingToWideSpace(final String line,
            final Pattern pattern,
            final DateStringFormat format) {
        final String dateStr=DateParserUtils.pruneDateStringWithMatch(line, pattern);
        log.debug("dateStr="+dateStr);
        final String[] words = dateStr.split("\\s+");
        for(String w: words) {
            final LocalDateFeatures features = selectAccordingToSpace(w, pattern, format);
            if(features != null && DateParserUtils.isGoodDateBestGuess(features.getDate())) {
                log.debug("parsing from no space word and got "+ features.getDate());
                return features;
            }
        }

        final LocalDateFeatures fromWholeString = parseToFeatures(dateStr, format);
        log.debug("fromWholeString="+fromWholeString.getDate());
        if(fromWholeString != null && !fromWholeString.getDateStringFeatures().isContainsWideSpace()
                && DateParserUtils.isGoodDateBestGuess(fromWholeString.getDate())){
           log.debug("parsing from whole string success.");
           return fromWholeString;
        }

        final DateStringFeatures wholeStringFeatures = DateStringFeatures.fromString(dateStr);
        final LocalDateFeatures fromBeforeSpace = parseToFeatures(wholeStringFeatures.getBeforeWideSpace(), format);
        if(fromBeforeSpace != null
                && DateParserUtils.isGoodDateBestGuess(fromBeforeSpace.getDate())){
            log.debug("parsing from before string success.");
            return fromBeforeSpace;
        }
        final LocalDateFeatures fromAfterSpace = parseToFeatures(wholeStringFeatures.getAfterWideSpace(), format);
        if(fromAfterSpace != null
                && DateParserUtils.isGoodDateBestGuess(fromAfterSpace.getDate())){
            log.debug("parsing from after string success.");
        }

        if(fromWholeString != null
                && DateParserUtils.isGoodDateBestGuess(fromWholeString.getDate())){
           log.debug("parsing from whole string allowing wide spaces success.");
           return fromWholeString;
        }

        //last try: removing all spaces?
        final LocalDateFeatures noSpaceLocalDate = parseToFeatures(StringCommon.removeAllSpaces(dateStr), format);
        if(noSpaceLocalDate != null
                && DateParserUtils.isGoodDateBestGuess(noSpaceLocalDate.getDate())){
            log.debug("parsing without space successes");
            return noSpaceLocalDate;
        }
        return fromAfterSpace;
    }

    public LocalDateFeatures selectAccordingToSpace(final String str,
            final Pattern pattern,
            final DateStringFormat format) {
        final String dateStr=DateParserUtils.pruneDateStringWithMatch(str, pattern);
        final LocalDateFeatures fromWholeString = parseToFeatures(dateStr, format);
//        log.debug("localDate="+localDate);
        if(fromWholeString != null){
//           log.debug("parsing from whole string success.");
           return fromWholeString;
        }
        return null;
    }
}
