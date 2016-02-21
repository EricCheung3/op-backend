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
import com.openprice.parser.common.DateLiterals;
import com.openprice.parser.data.StringInt;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateParserUtils {

    private static DateLiterals dateLiterals = new DateLiterals();

    //    private static Pattern datePattern= Pattern.compile("(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d");

    private static final String DATE_SPLITTER="-/.";//date splitter between day month year

    private static final String DATE_SPLITTER_UNIFORM="/";//uniformly used

    @Getter
    //month(one or two digits) and day (one or two digits), 4-digit year
    private static Pattern patternMonthDayYear4= Pattern.compile("([1-9]|0[1-9]|1[012])["+DATE_SPLITTER+"]([1-9]|0[1-9]|[12][0-9]|3[01])[" + DATE_SPLITTER+ "](19|20)\\d\\d");

    @Getter
    //month(one or two digits) and day (one or two digits), 2-digit year
    private static Pattern patternMonthDayYear2= Pattern.compile("([1-9]|0[1-9]|1[012])["+DATE_SPLITTER+"]([1-9]|0[1-9]|[12][0-9]|3[01])["+DATE_SPLITTER+"]\\d\\d");

    @Getter
    //4-digit year, month(one  two digits) and day (two digits)
    private static Pattern patternYear4MonthDay2=Pattern.compile("(19|20)\\d\\d["+DATE_SPLITTER+"]([1-9]|0[1-9]|1[012])["+DATE_SPLITTER+"](0[1-9]|[12][0-9]|3[01])");

    @Getter
    //4-digit year, month(one  two digits) and day (one or two digits)
    private static Pattern patternYear4MonthDay1=Pattern.compile("(19|20)\\d\\d["+DATE_SPLITTER+"]([1-9]|0[1-9]|1[012])["+DATE_SPLITTER+"]([1-9]|0[1-9]|[12][0-9]|3[01])");

    @Getter
    //format like "Feb 9, 2015"
    //http://stackoverflow.com/questions/2655476/regex-to-match-month-name-followed-by-year
    private static Pattern patternLiteralMonthDayYearPattern=Pattern.compile(
//            "\\b(?:Jan(?:uary)?|Feb(?:ruary)?||Mar(?:ch)?||Apr(?:il)?||May?"
//            +"||Jun(?:e)?||Jul(?:y)?||Aug(?:ust)?||Sep(?:tember)?||Oct(?:ober)?||Nov(?:ember)?||Dec(?:ember)?) (?:19[7-9]\\d|2\\d{3})(?=\\D|$)");
            "\\b(?:Jan(?:uary)?|Feb(?:ruary)?||Mar(?:ch)?||Apr(?:il)?||May?"
          +"||Jun(?:e)?||Jul(?:y)?||Aug(?:ust)?||Sep(?:tember)?||Oct(?:ober)?||Nov(?:ember)?||Dec(?:ember)?)"
          + "\\s*"
          + "([1-9]|0[1-9]|[12][0-9]|3[01])"
          + "\\s*"
          + "(\\s*||,||\\.||_)"
          + "\\s*"
          + "(?:19[7-9]\\d|2\\d{3})(?=\\D|$)");

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy"+DATE_SPLITTER_UNIFORM+
            "MM"+DATE_SPLITTER_UNIFORM+
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
        return dateLiterals
                .monthLiterals()
                .stream()
                .anyMatch(month->dateString.contains(month));
    }

    public static String formatDateString(final Date date){
        final int[] yMD=getYearMonthDay(date);
        return    yMD[0] + DATE_SPLITTER_UNIFORM
                + yMD[1] + DATE_SPLITTER_UNIFORM
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

    private static Calendar TODAY = Calendar.getInstance();
    private static int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR) - 2000;

    public static Calendar getCalendar(final int day, final int month, final int year) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month - 1);
        date.set(Calendar.DAY_OF_MONTH, day);
        return date;
    }

    public static Calendar getCalendar(final Date date) {
        final int[] yMD = getYearMonthDay(date);
        return getCalendar(yMD[2], yMD[1], yMD[0]);
    }

    public static Date toDateFromDigitalFormat(final String dateStr) throws Exception {
        final String[] words=dateStr.split("_|-|\\.|/");//this is dependent on the DATE_SPLITTER
        String yMD="";
        Date result = null;
        if(words[0].length()==4)
            yMD=words[0]+DATE_SPLITTER_UNIFORM+words[1]+DATE_SPLITTER_UNIFORM+words[2];
        else if(words[2].length()==4)
            yMD=words[2]+DATE_SPLITTER_UNIFORM+words[0]+DATE_SPLITTER_UNIFORM+words[1];
        else{ //either "05/31/15" or 15/05/31";
            final int monthOrYear =  Integer.valueOf(StringCommon.removeAllSpaces(words[0]));
            final int yearOrDay = Integer.valueOf(StringCommon.removeAllSpaces(words[2]));
            if(monthOrYear > 12 || yearOrDay > CURRENT_YEAR){//must be Year Month Day
                yMD = "20" + monthOrYear +DATE_SPLITTER_UNIFORM
                           + words[1]+DATE_SPLITTER_UNIFORM + yearOrDay;
            }else{//note "12/05/12" will default 2012/Dec/05
                yMD="20"+words[2]+DATE_SPLITTER_UNIFORM
                    +words[0]+DATE_SPLITTER_UNIFORM
                    +words[1];
            }
        }
        result = DATE_FORMAT.parse(yMD);
        if(TODAY.compareTo(getCalendar(result)) < 0) //prefer a parsed date that is before yesterday
            log.warn("something is probably wrong. the date parsed is after today: "+ result);
        return result;
    }

    public static List<String> literalMonthDayYearSplit(final String dateString){
//        final String[] words = dateString.split("-|_|\\.|\\s+");//not correct. only one dilimiter is selected
//        http://stackoverflow.com/questions/3654446/java-regex-help-splitting-string-on-spaces-and-commas
        final String[] words = dateString.split("\\s*(\\.|_|-|,|\\s)\\s*");
//        log.debug("words.length="+words.length);
        final List<String> list = new ArrayList<>();
        for(String w : words){
            if(w.length()==1
                && !Character.isDigit(w.charAt(0))
                && !Character.isLetter(w.charAt(0)))
                continue;
            list.add(StringCommon.removeAllSpaces(w));
        }
        return list;
    }

    //TODO right assuming the it's the LiteralMonth Day(digit) Year(digit) format, like "Feb 9 2015"
    public static Date toDateFromLiteralFormat(final String dateStr) throws Exception {
        final List<String> nonEmptyStrings = literalMonthDayYearSplit(dateStr);
//        log.debug("dateStr="+ dateStr+ ", nonEmptyStrings="+nonEmptyStrings+", nonEmptyStrings.size="+nonEmptyStrings.size());
        String yMD= nonEmptyStrings.get(2)  + DATE_SPLITTER_UNIFORM
                + dateLiterals.getMonthNumber(nonEmptyStrings.get(0)) + DATE_SPLITTER_UNIFORM
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
