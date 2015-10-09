package com.openprice.parser.store;

import java.util.ArrayList;
import java.util.List;

import com.openprice.parser.LineFinder;
import com.openprice.parser.common.Levenshtein;

import lombok.extern.slf4j.Slf4j;

/**
 * category operations
 */
@Slf4j
public class Category {
    private final LineFinder lineFinder;
    private final StoreConfig config;

    public Category(final LineFinder lineFinder, final StoreConfig config) {
        this.lineFinder = lineFinder;
        this.config = config;
    }

    /*
     * find the category of each item
     */
    public List<String> categoryOfItem(final List<String> category, final List<Integer> lineOfBlock,
            final List<Integer> lineOfCategory) throws Exception {
        final List<String> cat = new ArrayList<String>();
        if (category.size() == 0) {
            assert lineOfCategory.size() == 0;
            return cat;
        }
        int catCounter = 0;// start with the first category
        log.info("lineOfBlock.size=" + lineOfBlock.size());
        for (int i = 0; i < lineOfBlock.size(); i++) {
            int lineNum = lineOfBlock.get(i);// line number of current item
            if (catCounter + 1 < lineOfCategory.size() && lineNum < lineOfCategory.get(catCounter + 1)) {
                // this lineNum(item) belongs to the current category
            } else {
                if (catCounter + 1 < lineOfCategory.size())
                    catCounter++;
                // this lineNum(item) belongs to the next category
            }
            lineFinder.lineNumberCheck(lineOfCategory.get(catCounter));
            String ctmp = Category.isCategory(category, lineFinder.getLines().get(lineOfCategory.get(catCounter)),
                    config.similarityThresholdOfTwoStrings());
            log.info("item block starting with " + lineFinder.getLines().get(lineNum) + " belongs to " + ctmp);
            cat.add(ctmp);
        }
        return cat;
    }

    // approximate match a category and return the correct category name
    public static String isCategory(final List<String> category, String line, final double threshold) {
        // line=line.trim();
        line = line.replaceAll("\\s+", "");
        double maxScore = -1;
        int found = -1;
        for (int i = 0; i < category.size(); i++) {
            // String c=category().get(i).trim();
            String c = category.get(i).replaceAll("\\s+", "");
            double score = Levenshtein.compare(c, line);
            if (score > maxScore) {
                maxScore = score;
                found = i;
            }
        }
        if (maxScore > threshold)
            return category.get(found);
        else
            return "";// denote not found
    }

}