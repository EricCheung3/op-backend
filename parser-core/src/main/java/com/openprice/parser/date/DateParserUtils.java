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

import lombok.Getter;

public class DateParserUtils {

    //oldest receipts allowed
    private static final int OLDEST_RECEIPT_IN_DAYS = 20 * 365;

    @Getter
    private static MonthLiterals monthLiterals = new MonthLiterals();

    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
            "yyyy" +DateConstants.DATE_SPLITTER_UNIFORM+
            "M" +DateConstants.DATE_SPLITTER_UNIFORM+
            "d");

    //TODO in case there are dates in multiple lines, it makes sense to keep all the date variants found by different patterns in a line; and then take intersection
    public static StringInt findDateInLinesAndSelect(final List<String> origLines, final int start){
        for(int i = start; i < origLines.size(); i++){
            final String dateString = findDateInALine(origLines, i);
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
        final Map<DateStringFormat, LocalDateFeatures> map = allPossibleDatesInALine(lines.get(lineNumber));
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

        if(map.containsKey(DateStringFormat.LiteralMonthDayYear2)){
            return map.get(DateStringFormat.LiteralMonthDayYear2).getDate();
        }

        //log.debug("not date pattern is matched.");
        return null;
    }

    public static Map<DateStringFormat, LocalDateFeatures> allPossibleDatesInALine(final String str){
        final Map<DateStringFormat, LocalDateFeatures> result = new HashMap<>();
//        //log.debug("line string is "+str+"\n");
        LocalDateFeatures dateFeatures = y4md.parseWithSpaces(str);
        if(dateFeatures !=null ){
            //log.debug("y4md:" + dateFeatures.getDate());
            result.put(DateStringFormat.Year4MonthDay, dateFeatures);
        }

        dateFeatures =  mdy4.parseWithSpaces(str);
        if(dateFeatures !=null){
            //log.debug("mdy4:"+dateFeatures.getDate());
            result.put(DateStringFormat.MonthDayYear4, dateFeatures);
        }

        dateFeatures =  mdy2.parseWithSpaces(str);
        if(dateFeatures !=null){
            //log.debug("mdy2:"+dateFeatures.getDate());
            result.put(DateStringFormat.LiteralMonthDayYear2, dateFeatures);
        }

        dateFeatures =  y2md.parseWithSpaces(str);
        if(dateFeatures !=null){
            //log.debug("y2md:"+dateFeatures.getDate());
            result.put(DateStringFormat.Year2MonthDay, dateFeatures);
        }

        dateFeatures =  dmy4.parseWithSpaces(str);
        if(dateFeatures !=null){
            //log.debug("dmy4:"+dateFeatures.getDate());
            result.put(DateStringFormat.DayMonthYear4, dateFeatures);
        }

        dateFeatures =  dmy2.parseWithSpaces(str);
        if(dateFeatures !=null){
            //log.debug("dmy2:"+dateFeatures.getDate());
            result.put(DateStringFormat.DayMonthYear2, dateFeatures);
        }

        dateFeatures =  mdy2.parseWithSpaces(str);
        if(dateFeatures !=null){
            //log.debug("mdy2:"+dateFeatures.getDate());
            result.put(DateStringFormat.MonthDayYear2, dateFeatures);
        }

        dateFeatures = literalmdy4.parseWithSpaces(str);
        if(dateFeatures !=null){
            //log.debug("literalmdy4:"+dateFeatures.getDate());
            result.put(DateStringFormat.LiteralMonthDayYear4, dateFeatures);
        }

        dateFeatures = literalmdy2.parseWithSpaces(str);
        if(dateFeatures !=null){
            //log.debug("literalmdy2:"+dateFeatures.getDate());
            result.put(DateStringFormat.LiteralMonthDayYear2, dateFeatures);
        }
        return result;
    }

    public static boolean isGoodDateBestGuess(final LocalDate date) {
        return date != null &&
               (date.isBefore(DateUtils.getToday()) || date.equals(DateUtils.getToday())) &&
               Math.abs(ChronoUnit.DAYS.between(date, DateUtils.getToday())) <= OLDEST_RECEIPT_IN_DAYS;
    }

    public static String pruneDateStringWithMatch(final String str, final Pattern pattern){
        final Matcher match=pattern.matcher(str);
        final List<String> allMatches=new ArrayList<>();
        while(match.find()){
            allMatches.add(match.group());
        }
        //log.debug("allMatches="+allMatches);
        if(allMatches.size()==0)
            return StringCommon.EMPTY;
        return selectDateString(allMatches);
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
