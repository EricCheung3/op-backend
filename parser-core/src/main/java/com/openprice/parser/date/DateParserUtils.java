package com.openprice.parser.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy" +DateConstants.DATE_SPLITTER_UNIFORM+
            "MM" +DateConstants.DATE_SPLITTER_UNIFORM+
            "dd");

    //TODO in case there are dates in multiple lines, it makes sense to keep all the date variants found by different patterns in a line; and then take intersection
    public static StringInt findDate(final List<String> origLines, final int start){
//        log.debug("date line searching from line "+start+":"+origLines.get(start)+"\n");
        for(int i=start; i<origLines.size();i++){
            final String dateString=findDateInALine(origLines.get(i));
            if(dateString.isEmpty()) continue;
            try{
                return new StringInt(DateUtils.formatDateString(toDateFromDigitalFormat(dateString)), i);
            }catch(Exception e){
//                log.debug("dateString="+dateString+", toDate(dateString) error.");
            }
        }
        return StringInt.emptyValue();
    }

    public static Date toDateFromDigitalFormat(final String dateStr) throws Exception {
        final String[] words=dateStr.split("_|-|\\.|/");//this is dependent on the DateConstants.DATE_SPLITTER
        String yMD="";
        Date result = null;
        if(words[0].length()==4)
            yMD=words[0]+DateConstants.DATE_SPLITTER_UNIFORM+words[1]+DateConstants.DATE_SPLITTER_UNIFORM+words[2];
        else if(words[2].length()==4)
            yMD=words[2]+DateConstants.DATE_SPLITTER_UNIFORM+words[0]+DateConstants.DATE_SPLITTER_UNIFORM+words[1];
        else{ //either "05/31/15" or 15/05/31";
            final int monthOrYear =  Integer.valueOf(StringCommon.removeAllSpaces(words[0]));
            final int yearOrDay = Integer.valueOf(StringCommon.removeAllSpaces(words[2]));
            if(monthOrYear > 12 || yearOrDay > DateUtils.getCurrentYear()){//must be Year Month Day
                yMD = "20" + monthOrYear +DateConstants.DATE_SPLITTER_UNIFORM
                           + words[1]+DateConstants.DATE_SPLITTER_UNIFORM + yearOrDay;
            }else{//note "12/05/12" will default 2012/Dec/05
                yMD="20"+words[2]+DateConstants.DATE_SPLITTER_UNIFORM
                    +words[0]+DateConstants.DATE_SPLITTER_UNIFORM
                    +words[1];
            }
        }
        result = DATE_FORMAT.parse(yMD);
        if(DateUtils.getToday().before(result)) //prefer a parsed date that is before yesterday
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
    private final static Year2MonthDay y2md = new Year2MonthDay();
    private final static LiteralMonthDayYear4 literalmdy4 = new LiteralMonthDayYear4();
    public static String findDateInALine(final String str){
        final String strNoSpace=StringCommon.removeAllSpaces(str);
//        log.debug("line string is "+str+"\n");
        Calendar result = y4md.parse(strNoSpace);
        if (result!=null){
            log.debug("found y4md format."+result+"\n");
            return DateUtils.formatDateString(result);
        }

        result = mdy4.parse(strNoSpace);
        if(result != null){
            log.debug("found mDY4 format."+result+"\n");
            return DateUtils.formatDateString(result);
        }

        result = mdy2.parse(strNoSpace);
        if(result != null){
            log.debug("found mDY2 format."+result+"\n");
            return DateUtils.formatDateString(result);
        }

        result = y2md.parse(strNoSpace);
        if(result != null){
            log.debug("found Y2MD format."+result+"\n");
            return DateUtils.formatDateString(result);
        }

        //note it's str not strNoSpace
        result=literalmdy4.parse(str);
        if(result != null){
            log.debug("found literalMonthDayYear format."+result);
            return DateUtils.formatDateString(result);
        }

        log.debug("not date pattern is matched.");
        return StringCommon.EMPTY;
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

}
