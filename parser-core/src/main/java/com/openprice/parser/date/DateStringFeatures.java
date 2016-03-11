package com.openprice.parser.date;

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
        log.debug("words.length="+words.length);
        if(words.length != 2)
            return new DateStringFeatures(
                    false,
                    StringCommon.EMPTY,
                    false,
                    StringCommon.EMPTY,
                    false);
        return new DateStringFeatures(
                true,
                words[0],
                Character.isDigit(words[0].charAt(words[0].length()-1)),
                words[1],
                Character.isDigit(words[1].charAt(0))
                );

    }
}
