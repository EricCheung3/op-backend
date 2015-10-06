package com.openprice.parser;

import java.util.List;

import com.openprice.parser.common.StringCommon;

public class ParserUtils {

    private static final String SPLITTER = ",";

    public static boolean containsSubString(final List<String> skipSub, final String str) throws Exception {
        String noSpace = str.replaceAll("\\s+", "");
        for (int i = 0; i < skipSub.size(); i++) {
            if (noSpace.contains(skipSub.get(i).replaceAll("\\s+", "")))
                return true;
        }
        return false;
    }

    // detect new category for a line having two words with a space between it.
    // what if there are multiple spaces?
    // updated to approximate match
    // if return string is empty, then it is a category line;
    // if an exception is thrown, then it is possibly a new category that has
    // not been put into the category file for this store
    // otherwise, it return a fixed category
    //    public static String detectNewCategory(final List<String> category, final String raw,
    //            final int MinHeadSpacesBeforeCategory, final int WidthLowerBound, final double similarityThreshold)
    //                    throws Exception {
    //        String rawTrim = raw.trim();
    //        if (rawTrim.isEmpty())
    //            return "";
    //
    //        // assume category names.length()<=2
    //        if (raw.replaceAll("\\s+", "").length() <= 2)
    //            return "";
    //
    //        final String fixed = Category.isCategory(category, raw, similarityThreshold);
    //        // logger.info("fixed="+fixed);
    //        if (fixed.isEmpty()) {
    //            // detect if it's a new category we don't know from the category
    //            // file
    //            // logger.info("head spaces are "+StringCommon.headSpaces(raw));
    //            if (StringCommon.headSpaces(raw) > MinHeadSpacesBeforeCategory) {
    //                // logger.info("1");
    //                if (raw.length() < WidthLowerBound) {
    //                    throw new Exception("Likley that " + raw + " contains a new category that we don't know so far.");
    //                }
    //            }
    //        }
    //        return fixed;
    //    }

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
        return str.replaceAll(SPLITTER, "");
    }

}
