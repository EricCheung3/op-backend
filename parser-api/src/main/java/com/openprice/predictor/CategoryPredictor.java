package com.openprice.predictor;

public interface CategoryPredictor {

    /**
     * get the the most matching category, e.g, given a query name like "appl", returns fruit
     * @param queryName
     * @return
     */
    String mostMatchingCategory(final String queryName);

}
