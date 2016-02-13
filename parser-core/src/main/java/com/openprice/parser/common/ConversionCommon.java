package com.openprice.parser.common;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.openprice.parser.data.StringInt;

public class ConversionCommon
{

    public static List<String> removeEmptyLines(final List<String> orig){
        return orig.stream()
                .filter(str -> !str.trim().isEmpty())
                .collect(Collectors.toList());
    }

    public static List<String> removeEmptyLinesCommentLines(final List<String> orig,
            final String skippedHeader){
        return orig.stream()
                .filter(str -> (!str.startsWith(skippedHeader) && !str.trim().isEmpty()) )
                .collect(Collectors.toList());
    }

    //remove lines that are "empty" (only having spaces) and lowercase
    public static List<String> removeEmptyLinesCommentLinesAndLowerCase(final List<String> orig,
            final String skippedHeader){
        return orig.stream()
                .filter(str -> (!str.startsWith(skippedHeader) && !str.trim().isEmpty()) )
                .map(str->str.toLowerCase())
                .collect(Collectors.toList());
    }

    //convert to another list of strings by removing strings starting with a comment header string
    public static List<String> removeCommentStrings(final List<String> orig, final String skippedHeader){
        return orig.stream()
                .filter(str-> !str.startsWith(skippedHeader))
                .collect(Collectors.toList());
    }

    public static List<String> removeCommentStringsAndLowerCase(final List<String> orig, final String skippedHeader){
        return orig.stream()
                .filter(str-> !str.startsWith(skippedHeader))
                .map(str->str.toLowerCase())
                .collect(Collectors.toList());
    }

    public static List<String> lowercaseAll(final List<String> orig){
        return orig.stream().map(str->str.toLowerCase()).collect(Collectors.toList());
    }

    //convert two field line (a line will be splitted into two fields) to map
    public static Map<String, String> twoFieldLineToMapFirstIsKey(final List<String> lines, final String splitter){
        return lines.stream()
                .map(str->str.split(splitter))
                .collect(Collectors.toMap(array->array[0], array->array[1]));
    }

    public static Map<String, String> twoFieldLineToMapSecondIsKey(final List<String> lines, final String splitter){
        return lines.stream()
                .map(str->str.split(splitter))
                .collect(Collectors.toMap(array->array[1], array->array[0]));
    }

    public static List<Map.Entry<String, Integer>> sortByValueDescending(final Map<String, Integer> map){
        return map
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

    public static List<Map.Entry<String, Integer>> sortByValueAscending(final Map<String, Integer> map){
        return map
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue())
                .collect(Collectors.toList());
    }

    public static List<StringInt> sortByIntAscending(final List<StringInt> list){
        final Comparator<StringInt> BY_INT =
                Comparator.comparing(StringInt::getLine);
        return list.stream().sorted(BY_INT).collect(Collectors.toList());
    }
}
