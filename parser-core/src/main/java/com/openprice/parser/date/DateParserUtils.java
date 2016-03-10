package com.openprice.parser.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.openprice.common.StringCommon;
import com.openprice.parser.data.StringInt;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateParserUtils {

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
        log.debug("yMD="+yMD);
//        log.debug("parsing using simpledateformatter: "+SIMPLE_DATE_FORMATTER.parse(yMD));
        result = LocalDate.parse(yMD, DATE_FORMATTER);//cannot handle single digit month or day
        if(DateUtils.getToday().isBefore(result)) //prefer a parsed date that is before yesterday
            log.warn("something is probably wrong. the date parsed is after today: "+ result);
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
//    private final static Month1DayYear2 m1dy2 = new Month1DayYear2();
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
        final LocalDate localDate = findDateInALineLocalDateWithSpaces(lines, lineNumber);
        if(localDate == null)
            return StringCommon.EMPTY;
        return DateUtils.localDateToString(localDate);
    }

//    public static LocalDate findDateInALineLocalDate(final List<String> lines, final int lineNumber){
//        final String str = lines.get(lineNumber);
////        log.debug("line string is "+str+"\n");
//        LocalDate result = y4md.parseNoSpaces(str);
//        if (result!=null && result.isBefore(DateUtils.getToday())){
//            log.debug("found y4md format without space."+result+"\n");
//            return result;//DateUtils.formatDateString(result);
//        }
//
//        //first prefer to find valid date with space
//        result = mdy4.parseWithSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found mDY4 format from string (with space)."+result+"\n");
//            return result;//DateUtils.formatDateString(result);
//        }
//
//        result = mdy4.parseNoSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found mDY4 format without space."+result+"\n");
//            return result;//DateUtils.formatDateString(result);
//        }
//
//        System.out.println("str="+str);
//        result = mdy2.parseWithSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found mDY2 format from string (with space)."+result+"\n");
//            return result;//DateUtils.formatDateString(result);
//        }
//
//        result = y2md.parseWithSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found Y2MD format with space."+result+"\n");
//            return result;//DateUtils.formatDateString(result);
//        }
//
//        result = dmy4.parseNoSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found dmy4 format without space."+result+"\n");
//            return result;//DateUtils.formatDateString(result);
//        }
//
//        result = dmy2.parseNoSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found dmy2 format without space."+result+"\n");
//            return result;//DateUtils.formatDateString(result);
//        }
//
//        result = m1dy2.parseWithSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found m1dy2 format with space."+result);
//            return result;//DateUtils.formatDateString(result);
//        }
//
//        result = mdy2.parseNoSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found mDY2 format without space."+result+"\n");
//            return result;//DateUtils.formatDateString(result);
//        }
//
//        result = m1dy2.parseNoSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found m1dy2 format without space."+result);
//            return result;//DateUtils.formatDateString(result);
//        }
//
//        //note it's str not strNoSpace
//        result=literalmdy4.parseWithSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found literalMonthDayYear4 format with space."+result);
//            return result;// DateUtils.formatDateString(result);
//        }
//
//        result=literalmdy2.parseWithSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found literalMonthDayYear2 format with space."+result);
//            return result;// DateUtils.formatDateString(result);
//        }
//
//        log.debug("not date pattern is matched.");
//        return null;//StringCommon.EMPTY;
//    }

    public static LocalDate findDateInALineLocalDateWithSpaces(final List<String> lines, final int lineNumber){
        final String str = lines.get(lineNumber);
//        log.debug("line string is "+str+"\n");
        LocalDate result = y4md.parseWithSpaces(str);
        if (result!=null && result.isBefore(DateUtils.getToday())){
            log.debug("found y4md format with space."+result+"\n");
            return result;//DateUtils.formatDateString(result);
        }

        //first prefer to find valid date with space
        result = mdy4.parseWithSpaces(str);
        if(result != null && result.isBefore(DateUtils.getToday())){
            log.debug("found mDY4 format from string (with space)."+result+"\n");
            return result;//DateUtils.formatDateString(result);
        }

        System.out.println("str="+str);
        result = mdy2.parseWithSpaces(str);
        if(result != null && result.isBefore(DateUtils.getToday())){
            log.debug("found mDY2 format from string (with space)."+result+"\n");
            return result;//DateUtils.formatDateString(result);
        }

        result = y2md.parseWithSpaces(str);
        if(result != null && result.isBefore(DateUtils.getToday())){
            log.debug("found Y2MD format with space."+result+"\n");
            return result;//DateUtils.formatDateString(result);
        }

        result = dmy4.parseWithSpaces(str);
        if(result != null && result.isBefore(DateUtils.getToday())){
            log.debug("found dmy4 format with space."+result+"\n");
            return result;//DateUtils.formatDateString(result);
        }

        result = dmy2.parseWithSpaces(str);
        if(result != null && result.isBefore(DateUtils.getToday())){
            log.debug("found dmy2 format with space."+result+"\n");
            return result;//DateUtils.formatDateString(result);
        }

//        result = m1dy2.parseWithSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found m1dy2 format with space."+result);
//            return result;//DateUtils.formatDateString(result);
//        }

        result = mdy2.parseWithSpaces(str);
        if(result != null && result.isBefore(DateUtils.getToday())){
            log.debug("found mDY2 format with space."+result+"\n");
            return result;//DateUtils.formatDateString(result);
        }

//        result = m1dy2.parseWithSpaces(str);
//        if(result != null && result.isBefore(DateUtils.getToday())){
//            log.debug("found m1dy2 format with space."+result);
//            return result;//DateUtils.formatDateString(result);
//        }

        //note it's str not strNoSpace
        result=literalmdy4.parseWithSpaces(str);
        if(result != null && result.isBefore(DateUtils.getToday())){
            log.debug("found literalMonthDayYear4 format with space."+result);
            return result;// DateUtils.formatDateString(result);
        }

        result=literalmdy2.parseWithSpaces(str);
        if(result != null && result.isBefore(DateUtils.getToday())){
            log.debug("found literalMonthDayYear2 format with space."+result);
            return result;// DateUtils.formatDateString(result);
        }

        log.debug("not date pattern is matched.");
        return null;//StringCommon.EMPTY;
    }

    public static String pruneDateStringWithMatch(final String str, final Pattern pattern){
        final Matcher match=pattern.matcher(str);
        final List<String> allMatches=new ArrayList<>();
        while(match.find()){
            allMatches.add(match.group());
        }
        log.debug("allMatches="+allMatches);
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
        log.debug("all date strings are:\n");
        list.forEach(str->log.debug(str+"\n"));
        return list.get(0);
    }

    //all spaces should be removed
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
