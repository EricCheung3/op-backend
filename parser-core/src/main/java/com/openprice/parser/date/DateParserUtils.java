package com.openprice.parser.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    //    private static Pattern datePattern= Pattern.compile("(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d");


    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy"+DateConstants.DATE_SPLITTER_UNIFORM+
            "MM"+DateConstants.DATE_SPLITTER_UNIFORM+
            "dd");

    //TODO in case there are dates in multiple lines, it makes sense to keep all the date variants found by different patterns in a line; and then take intersection
    public static StringInt findDate(final List<String> origLines, final int start){
//        log.debug("date line searching from line "+start+":"+origLines.get(start)+"\n");
        for(int i=start; i<origLines.size();i++){
            final String dateString=findDateInALine(origLines.get(i));
            if(dateString.isEmpty()) continue;
            try{
                if(isLiteralDateFormat(dateString))
                    return new StringInt(formatDateString(toDateFromLiteralFormat(dateString)), i);
                else
                    return new StringInt(formatDateString(toDateFromDigitalFormat(dateString)), i);
            }catch(Exception e){
//                log.debug("dateString="+dateString+", toDate(dateString) error.");
            }
        }
        return StringInt.emptyValue();
    }

    public static boolean isLiteralDateFormat(final String dateString){
        return monthLiterals
                .monthLiterals()
                .stream()
                .anyMatch(month->dateString.contains(month));
    }

    public static String formatDateString(final Date date){
        final int[] yMD=getYearMonthDay(date);
        return    yMD[0] + DateConstants.DATE_SPLITTER_UNIFORM
                + yMD[1] + DateConstants.DATE_SPLITTER_UNIFORM
                + yMD[2];
    }

    /**
     * get the year month day in integer from a Date object
     * @param date
     * @return
     */
    public static int[] getYearMonthDay(final Date date){
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getYearMonthDay(cal);
    }
    public static int[] getYearMonthDay(final Calendar cal){
        return new int[]{
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH)+1,
                cal.get(Calendar.DAY_OF_MONTH)};
    }

    /**
     * convert a digital date string to Date
     * two order are allowed:
     * Month Day Year or Year Month Day
     * @param dateStr
     * @return
     */
    public static Calendar getCalendar(final int day, final int month, final int year) throws Exception{
        Calendar date = new GregorianCalendar();
        //setting allows invalid dates
        date.setLenient(false);
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month - 1);
        date.set(Calendar.DAY_OF_MONTH, day);

        //getting will throw Excepiton in Lenient false mode
        try {
            date.getTime();
        }
        catch (Exception e) {
            throw e;
        }
        return date;
    }

    public static Calendar getCalendar(final String day, final String month, final String year) {
        try{
            return getCalendar(Integer.valueOf(day), Integer.valueOf(month), Integer.valueOf(year));
        }catch(Exception e){
            log.warn(e.getMessage());
        }
        return null;
    }

    public static Calendar getCalendar(final Date date) throws Exception{
        final int[] yMD = getYearMonthDay(date);
        return getCalendar(yMD[2], yMD[1], yMD[0]);
    }

    public static Date getDate(final Calendar cal){
        return cal.getTime();
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
            if(monthOrYear > 12 || yearOrDay > DateConstants.CURRENT_YEAR
){//must be Year Month Day
                yMD = "20" + monthOrYear +DateConstants.DATE_SPLITTER_UNIFORM
                           + words[1]+DateConstants.DATE_SPLITTER_UNIFORM + yearOrDay;
            }else{//note "12/05/12" will default 2012/Dec/05
                yMD="20"+words[2]+DateConstants.DATE_SPLITTER_UNIFORM
                    +words[0]+DateConstants.DATE_SPLITTER_UNIFORM
                    +words[1];
            }
        }
        result = DATE_FORMAT.parse(yMD);
        if(DateConstants.TODAY.compareTo(getCalendar(result)) < 0) //prefer a parsed date that is before yesterday
            log.warn("something is probably wrong. the date parsed is after today: "+ result);
        return result;
    }


    //TODO right assuming the it's the LiteralMonth Day(digit) Year(digit) format, like "Feb 9 2015"
    public static Date toDateFromLiteralFormat(final String dateStr) throws Exception {
        final List<String> nonEmptyStrings = literalMonthDayYearSplit(dateStr);
//        log.debug("dateStr="+ dateStr+ ", nonEmptyStrings="+nonEmptyStrings+", nonEmptyStrings.size="+nonEmptyStrings.size());
        String yMD= nonEmptyStrings.get(2)  + DateConstants.DATE_SPLITTER_UNIFORM
                + monthLiterals.getMonthNumber(nonEmptyStrings.get(0)) + DateConstants.DATE_SPLITTER_UNIFORM
                + nonEmptyStrings.get(1);
//        log.debug("yMD="+yMD);
        return DATE_FORMAT.parse(yMD);
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
    public static String findDateInALine(final String str){
        final String strNoSpace=StringCommon.removeAllSpaces(str);
//        log.debug("line string is "+str+"\n");
        final String y4MD2=pruneDateStringWithMatch(strNoSpace, patternYear4MonthDay2);
        if (!y4MD2.isEmpty()){
            log.debug("found y4MD2 format."+y4MD2+"\n");
            return y4MD2;
        }

        final String y4MD1=pruneDateStringWithMatch(strNoSpace, patternYear4MonthDay1);
        if (!y4MD1.isEmpty()){
            log.debug("found y4MD1 format."+y4MD1+"\n");
            return y4MD1;
        }

        final String mDY4=pruneDateStringWithMatch(strNoSpace, patternMonthDayYear4);
        if(!mDY4.isEmpty()){
            log.debug("found mDY4 format."+mDY4+"\n");
            return mDY4;
        }
        final String mDY2=pruneDateStringWithMatch(strNoSpace, patternMonthDayYear2);
        if(!mDY2.isEmpty()){
            log.debug("found mDY2 format."+mDY2+"\n");
            return mDY2;
        }
        //note it's str not strNoSpace
        final String literalMonthDayYear=pruneDateStringWithMatch(str, patternLiteralMonthDayYearPattern);
        if(!literalMonthDayYear.isEmpty()){
            log.debug("found literalMonthDayYear format."+literalMonthDayYear);
            return literalMonthDayYear;
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
