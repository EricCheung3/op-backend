package com.openprice.parser.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.openprice.parser.data.ValueLine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateParserUtils {


    //    private static Pattern datePattern= Pattern.compile("(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d");

    //month(one or two digits) and day (one or two digits), 4-digit year
    private static Pattern patternMonthDayYear4= Pattern.compile("([1-9]|0[1-9]|1[012])[- /.]([1-9]|0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d");

    //month(one or two digits) and day (one or two digits), 2-digit year
    private static Pattern patternMonthDayYear2= Pattern.compile("([1-9]|0[1-9]|1[012])[- /.]([1-9]|0[1-9]|[12][0-9]|3[01])[- /.]\\d\\d");

    //4-digit year, month(one  two digits) and day (two digits)
    private static Pattern patternYear4MonthDay2=Pattern.compile("(19|20)\\d\\d[-/.]([1-9]|0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])");

    //    4-digit year, month(one  two digits) and day (one or two digits)
    private static Pattern patternYear4MonthDay1=Pattern.compile("(19|20)\\d\\d[-/.]([1-9]|0[1-9]|1[012])[- /.]([1-9]|0[1-9]|[12][0-9]|3[01])");

    public static ValueLine findDateStringAfterLine(final List<String> origLines, final int start){
        log.debug("date line searching from line "+start+":"+origLines.get(start)+"\n");
        for(int i=start; i<origLines.size();i++){
            final String dateString=pruneDateString(origLines.get(i));
            if( !dateString.isEmpty())
                return ValueLine.builder().value(dateString).line(i).build();
        }
        return ValueLine.defaultValueLine();
    }

    /**matching date patterns
     * the following order is important
     * first matching the format "2015/12/02"
     * then matching "12/02/2015"
     * then matching "12/02/15"
     * @param str
     * @return
     */
    public static String pruneDateString(final String str){
        final String strNoSpace=StringCommon.removeAllSpaces(str);
        log.debug("line string is "+str+"\n");
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
