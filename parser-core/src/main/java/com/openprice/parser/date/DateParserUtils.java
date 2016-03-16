package com.openprice.parser.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;
import com.openprice.parser.data.StringInt;
import com.openprice.parser.ml.api.predictor.LinePredictor;
import com.openprice.parser.ml.line.SimpleLinePredictor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateParserUtils {

    //oldest receipts allowed
    private static final int OLDEST_RECEIPT_IN_DAYS = 20 * 365;

    private static final LinePredictor LINE_PREDICTOR = new SimpleLinePredictor();

    @Getter
    private static MonthLiterals monthLiterals = new MonthLiterals();

    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy" +DateConstants.DATE_SPLITTER_UNIFORM+
            "M" +DateConstants.DATE_SPLITTER_UNIFORM+
            "d");

    //TODO in case there are dates in multiple lines, it makes sense to keep all the date variants found by different patterns in a line; and then take intersection
    public static StringInt findDateInLinesAndSelect(final List<String> origLines, final int start){
        for(int i = start; i < origLines.size(); i++){
            long startTime = System.currentTimeMillis();
            final String dateString = findDateInALine(origLines, i);
//            if(LINE_PREDICTOR.classify(dateString) != LineType.Date)
//                continue;
            long endTime = System.currentTimeMillis();
            long spentTime = endTime - startTime;
            //System.out.println("@@line "+i + origLines.get(i));
            //System.out.println("@@@@@time for findDateInALine is "+ spentTime+" ms \n");

            if(dateString.isEmpty()) continue;
            //TODO select the date parsed from the first line
            return finalFormat(dateString, i);
        }
        return StringInt.emptyValue();
    }

    public static StringInt finalFormat(final String dateString, final int lineNumber) {
        return new StringInt(DateUtils.localDateToString(parseToLocalDate(dateString)), lineNumber);
    }

    public static LocalDate parseToLocalDate(final String dateStr) {
        final String[] words=dateStr.split("_|-|\\.|/");//this is dependent on the DateConstants.DATE_SPLITTER
        String yMD="";
        LocalDate result = null;
        if(words[0].length()==4)
            yMD=words[0]+DateConstants.DATE_SPLITTER_UNIFORM+words[1]+DateConstants.DATE_SPLITTER_UNIFORM+words[2];
        else if(words[2].length()==4)
            yMD=words[2]+DateConstants.DATE_SPLITTER_UNIFORM+words[0]+DateConstants.DATE_SPLITTER_UNIFORM+words[1];
        else{ //either "05/31/15" or 15/05/31";
            final int monthOrYear =  Integer.valueOf(StringCommon.removeAllSpaces(words[0]));
            final int yearOrDay = Integer.valueOf(StringCommon.removeAllSpaces(words[2]));
            if(monthOrYear > 12 || yearOrDay > DateUtils.getCurrentYearInTwoDigits()){//must be Year Month Day
                yMD = "20" + monthOrYear +DateConstants.DATE_SPLITTER_UNIFORM
                           + words[1]+DateConstants.DATE_SPLITTER_UNIFORM + yearOrDay;
            }else{//note "12/05/12" will default 2012/Dec/05
                yMD="20"+words[2]+DateConstants.DATE_SPLITTER_UNIFORM
                    +words[0]+DateConstants.DATE_SPLITTER_UNIFORM
                    +words[1];
            }
        }
//        //log.debug("yMD="+yMD);
//        //log.debug("parsing using simpledateformatter: "+SIMPLE_DATE_FORMATTER.parse(yMD));
        result = LocalDate.parse(yMD, DATE_FORMATTER);//cannot handle single digit month or day
        //if(DateUtils.getToday().isBefore(result)) //prefer a parsed date that is before yesterday
            //log.warn("something is probably wrong. the date parsed is after today: "+ result);
        return result;
    }

    /**matching date patterns
     * the following order is important
     * first matching the format "2015/12/02"
     * then matching "12/02/2015"
     * then matching "12/02/15"
     * then matching "5/13/15"
     * @param str
     * @return
     */
    private final static Year4MonthDay y4md = new Year4MonthDay();
    private final static MonthDayYear4 mdy4 = new MonthDayYear4();
    private final static MonthDayYear2 mdy2 = new MonthDayYear2();
    private final static Year2MonthDay y2md = new Year2MonthDay();
    private final static DayMonthYear4 dmy4 = new DayMonthYear4();
    private final static DayMonthYear2 dmy2 = new DayMonthYear2();
    private final static LiteralMonthDayYear4 literalmdy4 = new LiteralMonthDayYear4();
    private final static LiteralMonthDayYear2 literalmdy2 = new LiteralMonthDayYear2();
    private final static DayLiteralMonthYear4 dLiteralMY4 = new DayLiteralMonthYear4();

    public static String findDateInALine(final String str){
        final List<String> lines = new ArrayList<String>();
        lines.add(str);
        return findDateInALine(lines, 0);
    }
    public static String findDateInALine(final List<String> lines, final int lineNumber){
//        final LocalDate localDate = findDateInALineLocalDate(lines, lineNumber);
        final LocalDate localDate = selectDateInALine(lines, lineNumber);
        if(localDate == null)
            return StringCommon.EMPTY;
        return DateUtils.localDateToString(localDate);
    }

    //This can be implemented as ML
    public static LocalDate selectDateInALine(final List<String> lines, final int lineNumber){
        long startTime = System.currentTimeMillis();
        final Map<DateStringFormat, LocalDateFeatures> map = allPossibleDatesInALine(lines.get(lineNumber));
//        //System.out.println(">>line : "+lines.get(lineNumber));
//        //System.out.println(">>>>time for allPossibleDatesInALine(): "+ (System.currentTimeMillis()-startTime)+"ms \n" );
        if(map.containsKey(DateStringFormat.Year4MonthDay)){
            return map.get(DateStringFormat.Year4MonthDay).getDate();
        }

        if(map.containsKey(DateStringFormat.MonthDayYear4)){
            return map.get(DateStringFormat.MonthDayYear4).getDate();
        }

        if(map.containsKey(DateStringFormat.MonthDayYear2)){
            return map.get(DateStringFormat.MonthDayYear2).getDate();
        }

        if(map.containsKey(DateStringFormat.Year2MonthDay)){
            return map.get(DateStringFormat.Year2MonthDay).getDate();
        }

        if(map.containsKey(DateStringFormat.DayMonthYear4)){
            return map.get(DateStringFormat.DayMonthYear4).getDate();
        }

        if(map.containsKey(DateStringFormat.DayMonthYear2)){
            return map.get(DateStringFormat.DayMonthYear2).getDate();
        }

        if(map.containsKey(DateStringFormat.MonthDayYear2)){
            return map.get(DateStringFormat.MonthDayYear2).getDate();
        }

        if(map.containsKey(DateStringFormat.LiteralMonthDayYear4)){
            return map.get(DateStringFormat.LiteralMonthDayYear4).getDate();
        }

        if(map.containsKey(DateStringFormat.DayLiteralMonthYear4)){
            return map.get(DateStringFormat.DayLiteralMonthYear4).getDate();
        }

        if(map.containsKey(DateStringFormat.LiteralMonthDayYear2)){
            return map.get(DateStringFormat.LiteralMonthDayYear2).getDate();
        }

        //log.debug("not date pattern is matched.");
        return null;
    }

    public static Map<DateStringFormat, LocalDateFeatures> allPossibleDatesInALine(final String str){
        final Map<DateStringFormat, LocalDateFeatures> result = new HashMap<>();
        log.debug("line string is "+str+"\n");
//        long t1 = System.currentTimeMillis();
        LocalDateFeatures dateFeatures = y4md.parseWithSpaces(str);
        if(dateFeatures !=null ){
            log.debug("y4md:" + dateFeatures.getDate());
            result.put(DateStringFormat.Year4MonthDay, dateFeatures);
        }
//        long t2 = System.currentTimeMillis();
//        if(t2-t1>50)
            //System.out.println("cpu for y4md is "+ (t2-t1));

        dateFeatures =  mdy4.parseWithSpaces(str);
        if(dateFeatures !=null){
            log.debug("mdy4:"+dateFeatures.getDate());
            result.put(DateStringFormat.MonthDayYear4, dateFeatures);
        }
//        long t3 = System.currentTimeMillis();
//        if(t3-t2>50)
            //System.out.println("cpu for mdy4 is "+ (t3-t2));

        dateFeatures =  mdy2.parseWithSpaces(str);
        if(dateFeatures !=null){
            log.debug("mdy2:"+dateFeatures.getDate());
            result.put(DateStringFormat.MonthDayYear2, dateFeatures);
        }
//        long t4 = System.currentTimeMillis();
//        if(t4-t3>50)
            //System.out.println("cpu for mdy2 is "+ (t4-t3));

        dateFeatures =  y2md.parseWithSpaces(str);
        if(dateFeatures !=null){
            log.debug("y2md:"+dateFeatures.getDate());
            result.put(DateStringFormat.Year2MonthDay, dateFeatures);
        }
//        long t5 = System.currentTimeMillis();
//        if(t5-t4>50)
            //System.out.println("cpu for y2md is "+ (t5-t4));

        dateFeatures =  dmy4.parseWithSpaces(str);
        if(dateFeatures !=null){
            log.debug("dmy4:"+dateFeatures.getDate());
            result.put(DateStringFormat.DayMonthYear4, dateFeatures);
        }
//        long t6 = System.currentTimeMillis();
//        if(t6-t5>50)
            //System.out.println("cpu for dmy4 is "+ (t6-t5));

        dateFeatures =  dmy2.parseWithSpaces(str);
        if(dateFeatures !=null){
            log.debug("dmy2:"+dateFeatures.getDate());
            result.put(DateStringFormat.DayMonthYear2, dateFeatures);
        }
//        long t7 = System.currentTimeMillis();
//        if(t7-t6>50)
           //System.out.println("cpu for dmy2 is "+ (t7-t6));

        dateFeatures =  mdy2.parseWithSpaces(str);
        if(dateFeatures !=null){
            log.debug("mdy2:"+dateFeatures.getDate());
            result.put(DateStringFormat.MonthDayYear2, dateFeatures);
        }
//        long t8 = System.currentTimeMillis();
//        if(t8-t7>50)
            //System.out.println("cpu for mdy2 is "+ (t8-t7));

        dateFeatures = literalmdy4.parseWithSpaces(str);
        //System.out.println("literalmdy4 finished ");
        if(dateFeatures !=null){
            log.debug("literalmdy4:"+dateFeatures.getDate());
            result.put(DateStringFormat.LiteralMonthDayYear4, dateFeatures);
        }
//        long t9 = System.currentTimeMillis();
//        if(t9-t8>50)
            //System.out.println("cpu for literalmdy4 is "+ (t9-t8));

        dateFeatures = dLiteralMY4.parseWithSpaces(str);
        if(dateFeatures !=null){
            log.debug("dLiteralMY4:"+dateFeatures.getDate());
            result.put(DateStringFormat.DayLiteralMonthYear4, dateFeatures);
        }
//        long t10 = System.currentTimeMillis();
//        if(t10-t9>50)
            //System.out.println("cpu for literalmdy2 is "+ (t10-t9));

        dateFeatures = literalmdy2.parseWithSpaces(str);
        //System.out.println("literalmdy2 finished ");
        if(dateFeatures !=null){
            log.debug("literalmdy2:"+dateFeatures.getDate());
            result.put(DateStringFormat.LiteralMonthDayYear2, dateFeatures);
        }
//        long t11 = System.currentTimeMillis();
//        if(t11-t10>50)
            //System.out.println("cpu for literalmdy2 is "+ (t11-t10));
        return result;
    }

    public static boolean isGoodDateBestGuess(final LocalDate date) {
        return date != null &&
               (date.isBefore(DateUtils.getToday()) || date.equals(DateUtils.getToday())) &&
               Math.abs(ChronoUnit.DAYS.between(date, DateUtils.getToday())) <= OLDEST_RECEIPT_IN_DAYS;
    }

    public static String pruneDateStringWithMatch(String strOrig, final Pattern pattern){
        final String str = StringCommon.reduceSpaces(strOrig);//reduce spaces so that regular expression will be faster
//        final String str = StringCommon.removeAllSpaces(strOrig);
        final Matcher match=pattern.matcher(str);
        log.debug("pruneDateStringWithMatch:"+str);
        long start = System.currentTimeMillis();
        if(match.find()){
            return match.group(0);
        }
        log.debug("pruneDateStringWithMatch spent " + (System.currentTimeMillis() - start));
        return StringCommon.EMPTY;
    }

    /**
     * select a date string with a good confidence from a list of candidates
     * @param list
     * @return
     */
    public static String selectDateString(final List<String> list){
//        //log.debug("all date strings are:\n");
//        list.forEach(str->//log.debug(str+"\n"));
        return list.get(0);
    }

    //note all spaces in the returned strings are all removed
    public static List<String> getMeaningfulDateWords(final String[] words){
        final List<String> cleanWords = new ArrayList<String>();
        for(String w: words){
            if(DateConstants.DATE_SPLITTERS.contains(w))
                continue;
            cleanWords.add(StringCommon.removeAllSpaces(w));
        }
        return cleanWords;
    }

}
