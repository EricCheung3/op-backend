package com.openprice.common;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Levenshtein distance (https://en.wikipedia.org/wiki/Levenshtein_distance) for fuzzy string matching.
 *
 * See http://www.codeproject.com/Articles/162790/Fuzzy-String-Matching-with-Edit-Distance
 */


public class Levenshtein
{
    //decay factor for an ordered list of words. "A B C". The decay is applied to "A", "B", "C" in order
    final static double WORD_POSITION_DECAY = 0.8;

    public static String mostSimilarInSetLevenshtein(final String key, final Set<String> set)
            throws IllegalArgumentException{
        if(set.size()==0) throw new IllegalArgumentException("set.size should not be 0.");
        return set.stream().reduce((p1,p2) ->
            Levenshtein.compare(StringCommon.removeAllSpaces(p1), StringCommon.removeAllSpaces(key)) >
            Levenshtein.compare(StringCommon.removeAllSpaces(p2), StringCommon.removeAllSpaces(key)) ? p1:p2).get();
  }

    public static String mostSimilarInSetOneWay(final String key, final Set<String> set) throws IllegalArgumentException{
        if(set.size()==0) throw new IllegalArgumentException("set.size should not be 0.");
        return set.stream().reduce((p1,p2) ->
            StringCommon.matchStringToSubString(key, p1) > StringCommon.matchStringToSubString(key, p2) ? p1:p2).get();
  }

    public static String mostSimilarInSetTwoWay(final String key, final Set<String> set) throws IllegalArgumentException{
        if(set.size()==0) throw new IllegalArgumentException("set.size should not be 0.");
        return set.stream().reduce((p1,p2) ->
            StringCommon.matchStringToSubStringTwoWay(key, p1) >
            StringCommon.matchStringToSubStringTwoWay(key, p2) ? p1:p2).get();
    }

    /**
     * giving priority to the matching scores of first words in the list
     * @param key
     * @param words
     * @return
     * @throws IllegalArgumentException
     */
    public static double weightedScoreByPositionOrder(final String key, final List<String> words) throws IllegalArgumentException{
        if(words.size()==0) throw new IllegalArgumentException("set.size() should not be 0.");
        double weight = 1;
        double max = Double.MIN_VALUE;
        for(String w: words){
            final double score = StringCommon.matchStringToSubStringTwoWay(key, w) * weight;
            if(score > max){
                max = score;
            }
            weight *= WORD_POSITION_DECAY;
        }
        return max;
    }

    public static double mostSimilarScoreInSetTwoWay(final String key, final Set<String> set) throws IllegalArgumentException{
        if(set.size()==0) throw new IllegalArgumentException("set.size should not be 0.");
        Optional<Double> maxDouble=set
               .stream()
               .map(str -> StringCommon.matchStringToSubStringTwoWay(key,str))
               .max(Comparator.comparing(d->d));
         if(maxDouble.isPresent())
              return  maxDouble.get();
         return 0.0;
    }

    public static double mostSimilarScoreInSetOneWay(final String key, final Set<String> set) throws IllegalArgumentException{
        if(set.size()==0) throw new IllegalArgumentException("set.size should not be 0.");
        Optional<Double> maxDouble=set
               .stream()
               .map(str -> StringCommon.matchStringToSubString(key,str))
               .max(Comparator.comparing(d->d));
         if(maxDouble.isPresent())
              return  maxDouble.get();
         return 0.0;
    }

    public static double mostSimilarScoreInSetLevenshtein(final String key, final Set<String> set) throws IllegalArgumentException{
        if(set.size()==0) throw new IllegalArgumentException("set.size should not be 0.");
        Optional<Double> maxDouble=set
               .stream()
               .map(str -> Levenshtein.compare(StringCommon.removeAllSpaces(str),
                                               StringCommon.removeAllSpaces(key)))
               .max(Comparator.comparing(d->d));
         if(maxDouble.isPresent())
              return  maxDouble.get();
         return 0.0;
    }

    //the string matches the list
    public static boolean matchList(final String given, final List<String> list, final double threshold) {
        for(String s2:list){
            if(Levenshtein.compare(StringCommon.removeAllSpaces(given),
                    StringCommon.removeAllSpaces(s2)) >=threshold){
                return true;
            }
        }
        return false;
    }

    //there is non empty overlap match for two list
    public static boolean nonEmptyOverlapMatch(final List<String> list1,
            final List<String> list2, final double threshold) {
        for(String s1:list1) {
            if(matchList(s1, list2, threshold))
                return true;
        }
        return false;
    }


    public static double compareOnlyLettersDigits(final List<String> list1, final List<String> list2){
        return compare(StringCommon.getOnlyLettersDigits(StringCommon.flatten(list1, "")),
                       StringCommon.getOnlyLettersDigits(StringCommon.flatten(list2, "")));
    }


    public static double compare(final List<String> list1, final List<String> list2) {
        return compare(StringCommon.flatten(list1, ""),
                       StringCommon.flatten(list2, ""));
    }

    public static double compareIgnoreSpaces(final List<String> list1, final List<String> list2) {
        return compare(StringCommon.flatten(list1, "").replaceAll("\\s+", ""),
                       StringCommon.flatten(list2, "").replaceAll("\\s+", ""));
    }

    //number of matching chars
    public static int matchingChars(final String s1, final String s2) {
        int retval = 0;
        final int n = s1.length();
        final int m = s2.length();
        if (0 == n && m==0) {
            retval = 0;
        }
        else {
            retval = Math.max(n, m) - (int)compare(s1, n, s2, m);
        }
        return retval;
    }

    public static double compare(final String s1, final String s2) {
        double retval = 0.0;
        final int n = s1.length();
        final int m = s2.length();
        if (0 == n && m==0) {
            retval = 1.0;//m;
        }
        else {
            retval = 1.0 - (compare(s1, n, s2, m) / (Math.max(n, m)));
        }
        /*
        if(s1.contains("Sale price")|| s1.contains("Saleprice")){
           System.out.println(s1+":"+s2+", levenshtein score="+retval);
        }
         */
        //         if(s1.contains("@$/.")|| s1.contains("Saleprice")){
        //           System.out.println(s1+":"+s2+", levenshtein score="+retval);
        //        }
        return retval;
    }

    public static double compare(final String s1,
                                 final int n,
                                 final String s2,
                                 final int m) {
        int matrix[][] = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            matrix[i][0] = i;
        }
        for (int i = 0; i <= m; i++) {
            matrix[0][i] = i;
        }

        for (int i = 1; i <= n; i++) {
            int s1i = s1.codePointAt(i - 1);
            for (int j = 1; j <= m; j++) {
                int s2j = s2.codePointAt(j - 1);
                final int cost = s1i == s2j ? 0 : 1;
                matrix[i][j] = min3(matrix[i-1][j] + 1,
                                    matrix[i][j-1] + 1,
                                    matrix[i-1][j-1] + cost);
            }
        }
        return matrix[n][m];
    }

    private static int min3(final int a, final int b, final int c) {
        return Math.min(Math.min(a, b), c);
    }
}
