package com.openprice.parser.store;

public interface StoreParserSelector {
    static final String BRANCH_FILE_NAME = "branch.csv";
    static final String CATEGORY_FILE_NAME = "category.txt";
    static final String SKIP_BEFORE_FILE_NAME = "skipBeforeItemsFinish.txt";
    static final String SKIP_AFTER_FILE_NAME = "skipAfterItemsFinish.txt";

    static final String BASE_CONFIG_FILE_NAME = "config.properties";
    static final String HEADER_CONFIG_FILE_NAME = "headerConfig.properties";

    StoreParser selectParser();
}
