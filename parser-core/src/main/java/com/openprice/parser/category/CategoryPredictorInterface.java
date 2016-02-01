package com.openprice.parser.category;

public interface CategoryPredictorInterface {

    //get the the most matching category, e.g, given a query name like "appl", returns fruit
    String mostMatchingCategory(final String queryName);

}
