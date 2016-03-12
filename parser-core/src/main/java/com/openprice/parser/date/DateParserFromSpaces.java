package com.openprice.parser.date;

import java.time.LocalDate;
import java.util.regex.Pattern;

import com.openprice.parser.date.ml.StringGeneralFeatures;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DateParserFromSpaces implements DateParser{

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
        final LocalDateFeatures fromWholeString = parseToFeatures(dateStr, format);
        if(fromWholeString != null && !fromWholeString.getDateStringFeatures().isContainsWideSpace()){
           log.warn("parsing from whole string success.");
           return fromWholeString;
        }
        final DateStringFeatures wholeStringFeatures = DateStringFeatures.fromString(dateStr);
        final LocalDateFeatures fromBeforeSpace = parseToFeatures(wholeStringFeatures.getBeforeWideSpace(), format);
        if(fromBeforeSpace != null){
            log.warn("parsing from before string success.");
            return fromBeforeSpace;
        }
        final LocalDateFeatures fromAfterSpace = parseToFeatures(wholeStringFeatures.getAfterWideSpace(), format);
        if(fromAfterSpace != null){
            log.warn("parsing from after string success.");
        }
        if(fromAfterSpace == null) {
            final String[] words = dateStr.split("\\s+");
            for(String w: words) {
                final LocalDateFeatures features = selectAccordingToSpace(w, pattern, format);
                if(features != null) {
                    return features;
                }
            }
        }

        //last try: removing all spaces?

        return fromAfterSpace;
    }

    public LocalDateFeatures selectAccordingToSpace(final String str,
            final Pattern pattern,
            final DateStringFormat format) {
        final String dateStr=DateParserUtils.pruneDateStringWithMatch(str, pattern);
        final LocalDateFeatures fromWholeString = parseToFeatures(dateStr, format);
//        log.debug("localDate="+localDate);
        if(fromWholeString != null){
           log.warn("parsing from whole string success.");
           return fromWholeString;
        }
        return null;
    }
}
