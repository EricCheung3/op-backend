//package com.openprice.parser.date;
//
//import java.time.LocalDate;
//import java.util.List;
//
///**
// * "literal month day year" format
// * day could be one or two digits
// * year is four digits
// *
// * Use month literals to find
// */
////format like "OCT.08’ 15"
////TODO also inherit DateParserRegularExpression
////@Slf4j
//public class LiteralMonthDayYear2 implements DateParser{
//
//    //TODO this repeats with DataConstants.DATE_SPLITTERS
//    public static final String SPLITTER = "\\s*" + "(\\s*||,||\\.||_||'||’||-)"  + "\\s*";
//
//    @Override
//    public LocalDateFeatures parseWithSpaces(String origLine) {
//        return selectAccordingToWideSpace(origLine, getDateSubString(origLine), DateStringFormat.LiteralMonthDayYear2);
//    }
//
//    @Override
//    public LocalDate parseToDate(final String literalMDY2) {
//       final List<String> words = literalMonthDayYearSplit(literalMDY2, 2);
//       if(words == null) return null;
//       //log.debug("words.size()="+words.size());
//       for(String str: words)
//           //log.debug(str);
//       if(words.size() < 3)
//          return null;
//       try{
//          return DateUtils.fromDayMonthYear(
//                  words.get(1),
//                  DateParserUtils.getMonthLiterals().getMonthNumber(words.get(0))+"",
//                  "20" + words.get(2)
//                  );
//       }catch(Exception e){
//          //log.warn(e.getMessage());
//       }
//       return null;
//    }
//
//    @Override
//    public String getDateSubString(String line) {
//
//    }
//
//}
