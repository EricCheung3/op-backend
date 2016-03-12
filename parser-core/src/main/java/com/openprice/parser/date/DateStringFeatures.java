package com.openprice.parser.date;

import java.util.ArrayList;
import java.util.List;

import com.openprice.common.StringCommon;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

//features that encode a date string like "6                                     3 /05/ 16 "
//assuming only one wide spaces. So input "6                                     3 /05/ 16        sdfasdas" are not allowed
@Value
@Slf4j
public class DateStringFeatures {

    public static String WIDE_SPACE = "    ";

    //beginning and end of wide space
    boolean containsWideSpace;

    String beforeWideSpace;
    boolean beforeWideSpaceEndsWithDigit;//for "6                                     3 /05/ 16 " will be true

    String afterWideSpace;
   //for "6                                     3 /05/ 16 " and "3 /05/ 16      2" will be both true
    boolean afterWideSpaceStartsWithDigit;

    public static DateStringFeatures fromString(final String str) {
        final String[] words = str.split("\\s{"+WIDE_SPACE.length()+",}");
        final List<String> nonEmptyStrings = new ArrayList<>();
        for(String w: words)
            if(!w.trim().isEmpty())
                nonEmptyStrings.add(w);
        if(nonEmptyStrings.size()== 1){
            return new DateStringFeatures(
                    true,
                    nonEmptyStrings.get(0),
                    Character.isDigit(nonEmptyStrings.get(0).charAt(nonEmptyStrings.get(0).length()-1)),
                    StringCommon.EMPTY,
                    false
                    );
        }
        if(nonEmptyStrings.size()== 2){
            return new DateStringFeatures(
                    true,
                    nonEmptyStrings.get(0),
                    Character.isDigit(nonEmptyStrings.get(0).charAt(nonEmptyStrings.get(0).length()-1)),
                    nonEmptyStrings.get(1),
                    Character.isDigit(nonEmptyStrings.get(1).charAt(0))
                    );
        }

        if(nonEmptyStrings.size() < 2){
            return new DateStringFeatures(
                    false,
                    StringCommon.EMPTY,
                    false,
                    StringCommon.EMPTY,
                    false);
        }

        //TODO find most likely date strings to be "beforeWideSpace" and "afterWideSpace"; here either taking the last

        final String last = nonEmptyStrings.get(nonEmptyStrings.size()-1);
        final String lastSecond = nonEmptyStrings.get(nonEmptyStrings.size()-2);
        return new DateStringFeatures(
                true,
                lastSecond,
                Character.isDigit(lastSecond.charAt(lastSecond.length()-1)),
                last,
                Character.isDigit(last.charAt(last.length()-1))
                );
    }

    @Override
    public String toString() {
        return "containsWideSpace:"+containsWideSpace +"\n" +
                "beforeWideSpace:" +beforeWideSpace +"\n" +
                "beforeWideSpaceEndsWithDigit:" +beforeWideSpaceEndsWithDigit+"\n" +
                "afterWideSpace:" +afterWideSpace+"\n" +
                "afterWideSpaceStartsWithDigit:" +afterWideSpaceStartsWithDigit;
    }
 }
