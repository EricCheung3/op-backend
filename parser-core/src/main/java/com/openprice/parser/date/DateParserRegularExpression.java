package com.openprice.parser.date;

import java.time.LocalDate;

import com.openprice.parser.date.ml.StringGeneralFeatures;

//@Slf4j
public abstract class DateParserRegularExpression implements DateParser{

    //pattern for month and day
//    public final static String DAY_MONTH_PATTERN = "\\s*(0?\\s*\\d|\\d\\s*\\d|\\d{1,2})\\s*";
    public final static String DAY_MONTH_PATTERN = "\\s*(\\d\\s*\\d|\\d{1,2})\\s*";

    //this leads to matcher very slow
//    public final static String YEAR_4_PATTERN = "\\s*(1\\s*9|2\\s*0)\\s*\\d\\s*\\d\\s*";
    public final static String YEAR_4_PATTERN = "20[0|1]\\d";

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
            final String dateStr,
            final DateStringFormat format) {
        //log.debug("selectAccordingToWideSpace, dateStr="+dateStr);
        if(dateStr.isEmpty()) return null;
        final String[] words = dateStr.split("\\s+");
        for(String w: words) {
            final LocalDateFeatures features = selectAccordingToSpace(w, getDateSubString(w), format);
            if(features != null && DateParserUtils.isGoodDateBestGuess(features.getDate())) {
                //log.debug("parsing from no space word and got "+ features.getDate());
                return features;
            }
        }
        long t3 = System.currentTimeMillis();
//        System.out.println("selectAccordingToSpace spends "+ (t3-t2));

        final LocalDateFeatures fromWholeString = parseToFeatures(dateStr, format);
//        //log.debug("fromWholeString="+fromWholeString.getDate());
        if(fromWholeString != null && !fromWholeString.getDateStringFeatures().isContainsWideSpace()
                && DateParserUtils.isGoodDateBestGuess(fromWholeString.getDate())){
           //log.debug("parsing from whole string success.");
           return fromWholeString;
        }
        long t4 = System.currentTimeMillis();
//        System.out.println("parseToFeatures fromWholeString spends "+ (t4-t3));

        final DateStringFeatures wholeStringFeatures = DateStringFeatures.fromString(dateStr);
        final LocalDateFeatures fromBeforeSpace = parseToFeatures(wholeStringFeatures.getBeforeWideSpace(), format);
        if(fromBeforeSpace != null
                && DateParserUtils.isGoodDateBestGuess(fromBeforeSpace.getDate())){
            //log.debug("parsing from before string success.");
            return fromBeforeSpace;
        }
        long t5 = System.currentTimeMillis();
//        System.out.println("parseToFeatures fromBeforeSpace spends "+ (t5-t4));

        final LocalDateFeatures fromAfterSpace = parseToFeatures(wholeStringFeatures.getAfterWideSpace(), format);
        if(fromAfterSpace != null
                && DateParserUtils.isGoodDateBestGuess(fromAfterSpace.getDate())){
            //log.debug("parsing from after string success.");
        }
        long t6 = System.currentTimeMillis();
//        System.out.println("parseToFeatures fromAfterSpace spends "+ (t6-t5));

        if(fromWholeString != null
                && DateParserUtils.isGoodDateBestGuess(fromWholeString.getDate())){
           //log.debug("parsing from whole string allowing wide spaces success.");
           return fromWholeString;
        }
//        long endTime = System.currentTimeMillis();
//        long spentTime = endTime - startTime;
//        //log.debug("selectAccordingToWideSpace: spent time is "+spentTime);
        //last try: removing all spaces?
//        final LocalDateFeatures noSpaceLocalDate = parseToFeatures(StringCommon.removeAllSpaces(dateStr), format);
//        if(noSpaceLocalDate != null
//                && DateParserUtils.isGoodDateBestGuess(noSpaceLocalDate.getDate())){
//            //log.debug("parsing without space successes");
//            return noSpaceLocalDate;
//        }
        return fromAfterSpace;
    }

    public LocalDateFeatures selectAccordingToSpace(final String str,
            final String dateStr,
            final DateStringFormat format) {
        final LocalDateFeatures fromWholeString = parseToFeatures(dateStr, format);
//        //log.debug("localDate="+localDate);
        if(fromWholeString != null){
//           //log.debug("parsing from whole string success.");
           return fromWholeString;
        }
        return null;
    }
}
