package com.openprice.parser.common;

import java.util.List;

import com.openprice.common.StringCommon;
import com.openprice.parser.data.ProductConstant;

public class ParserUtils {

    public static String getParserRoot(final String chainName){
        return "/config/"+chainName+"1";
    }

    public static boolean containsSubString(final List<String> skipSub, final String str) throws Exception {
        String noSpace = str.replaceAll("\\s+", "");
        for (int i = 0; i < skipSub.size(); i++) {
            if (noSpace.contains(skipSub.get(i).replaceAll("\\s+", "")))
                return true;
        }
        return false;
    }

    // TODO remove after price parser
    public static boolean isItem(final String name) {
        final String noSpace = name.replaceAll("\\s+", "");
        if (noSpace.length() <= 1)
            return false;

        int[] count = StringCommon.countDigitAndAlphabets(noSpace);
        if (count[1] < 1) {
            return false;
        }
        return !StringCommon.containsOneOnlyOneLetter(noSpace);
    }

    public static String cleanField(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        return str.replaceAll(ProductConstant.SPLITTER, "");
    }

    public static boolean ignoreLine(final String lineStr) {
        String str = lineStr.replaceAll("\\s+", "");
        if (str.length() < 5)
            return true;
        String sub = StringCommon.getOnlyLettersDigits(str);
        if (sub.length() < 5)
            return true;

        return false;
    }

}
