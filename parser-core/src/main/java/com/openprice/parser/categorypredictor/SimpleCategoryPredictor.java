package com.openprice.parser.categorypredictor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.openprice.common.Levenshtein;
import com.openprice.common.StringCommon;
import com.openprice.common.TextResourceUtils;
import com.openprice.parser.common.ConversionCommon;
import com.openprice.parser.price.ThreeStrings;
import com.openprice.predictor.CategoryPredictor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * simple predictor for productCategory,
 * This uses best matched category from categoryClasses.txt
 */
@Slf4j
public class SimpleCategoryPredictor implements CategoryPredictor {

    private static final String CATEGORY_CLASSES_FILE="/config/categoryClasses.txt";
    private static final String CATEGORY_CLASSES_FILE_SPLITTER=":";
    private static final String CATEGORY_CLASSES_FILE_NAME_SPLITTER=",";

    //category to names
    @Getter
    private final Map<String, Set<String>> categoryToNames;

    public static SimpleCategoryPredictor fromConfig(){
        final List<String> lines=ConversionCommon
                .removeEmptyLinesCommentLines(TextResourceUtils.loadStringArray(CATEGORY_CLASSES_FILE), "#");

        //verify the categories are the same as icon names.
        final Set<String> allCategories=ConversionCommon
                .removeEmptyLinesCommentLines(TextResourceUtils.loadStringArray("/config/icons.txt"), "#")
                .stream()
                .collect(Collectors.toSet());
        final SimpleCategoryPredictor simple=new SimpleCategoryPredictor(lines);
        if(!allCategories.containsAll(simple.getCategoryToNames().keySet())){
            log.warn("Not-allowed-categories are:");
            simple
            .getCategoryToNames()
            .keySet()
            .stream()
            .filter(k -> !allCategories.contains(k))
            .forEachOrdered(outlier->log.warn(outlier));
            //throw new Exception("The file "+CATEGORY_CLASSES_FILE+" contains categories that do not have a corresponding icon name.");
        }
        return simple;
    }

    //note the cateogry itself is also counted as a "name"
    public SimpleCategoryPredictor(final List<String> lines){
        categoryToNames= new HashMap<String, Set<String>>();
        lines
        .stream()
        .map(str->str.split(CATEGORY_CLASSES_FILE_SPLITTER))
        .forEach(array->{
            final String category=array[0].trim();
            if(categoryToNames.containsKey(category))
                log.warn("Repeating category detectected:"+category);
            else{
                final List<String> names=Arrays.asList(array[1].split(CATEGORY_CLASSES_FILE_NAME_SPLITTER));
                final Set<String> set = names.stream().map(str->str.trim()).collect(Collectors.toSet());
                set.add(category);
                categoryToNames.put(category, set);
                }
            }
        );
    }

    public ThreeStrings mostMatchingCategoryReturnThree(String queryName) {
        return categoryToNames
                .entrySet()
                .stream()
                .map(e->{
                    final double score = Levenshtein.mostSimilarScoreInSetTwoWay(queryName, e.getValue());
                    final String matchedName = Levenshtein.mostSimilarInSetTwoWay(queryName, e.getValue());
//                    log.debug("matchedName=" + matchedName+", score="+score);
                    return new ThreeStrings(e.getKey(), matchedName, score+StringCommon.EMPTY);})
                .reduce((p1,p2) -> {
                    if(Double.valueOf(p1.getThird()) > Double.valueOf(p2.getThird())) return p1;
                    if(Double.valueOf(p2.getThird()) > Double.valueOf(p1.getThird())) return p2;
                    final String s1=StringCommon.removeAllSpaces(p1.getSecond());
                    final String s2=StringCommon.removeAllSpaces(p2.getSecond());
                    final String q=StringCommon.removeAllSpaces(queryName);

                    //tie breaking
                    if(q.length()==s1.length()) return p1;
                    if(q.length()==s2.length()) return p2;
                    if(q.length()>s1.length() && q.length()>s2.length())
                        return s1.length()>s2.length()?p1:p2;
                    if(q.length()<s1.length() && q.length()<s2.length())
                        return s1.length()<s2.length()?p1:p2;
                    return s1.length()>s2.length()?p1:p2;//prefer longer one is assumed to be more likely to be correct
                })
                .get();
    }

    @Override
    public String mostMatchingCategory(String queryName) {
       return mostMatchingCategoryReturnThree(queryName).getFirst();
    }

    @Override
    public String toString(){
        return categoryToNames.toString();
    }

}
