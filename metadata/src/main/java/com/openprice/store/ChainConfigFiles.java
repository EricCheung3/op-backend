package com.openprice.store;


/**
 * store dependent config files
 * These files are under a folder named by a chain code
 */
public class ChainConfigFiles {

    private static String PREFIX="/";

    //catalog
    private static final String CATALOG_DATA_JSON = "catalog-data.json";
    //branches
    private static final String BRANCH_DATA_JSON = "branch-data.json";
    //identification field that can be used to find stores
    private static final String IDENTIFY_FIELD_DATA_JSON = "identify.json";

    private static final String CATEGORY_FILE_NAME = "category-in-store-receipt.json";
    private static final String CATALOG_BLACK_LIST_FILE_NAME="notCatalogItemNames.json";
    private static final String CATALOG_NOTATION_FILE_NAME="notations.json";
    private static final String SKIP_BEFORE_FILE_NAME = "skipBeforeItemsFinish.json";
    private static final String SKIP_AFTER_FILE_NAME = "skipAfterItemsFinish.json";

    private static String getChainFile(final String chainCode, final String fileName){
            return PREFIX + chainCode + "/" + fileName;
    }

    public static String getBranches(final String chainCode){
        return getChainFile(chainCode, BRANCH_DATA_JSON);
    }

    public static String getCatalog(final String chainCode){
        return getChainFile(chainCode, CATALOG_DATA_JSON);
    }

    public static String getIdentify(final String chainCode){
        return getChainFile(chainCode, IDENTIFY_FIELD_DATA_JSON);
    }

    public static String getCategoriesOfStore(final String chainCode){
        return getChainFile(chainCode, CATEGORY_FILE_NAME);
    }

    public static String getBlackList(final String chainCode){
        return getChainFile(chainCode, CATALOG_BLACK_LIST_FILE_NAME);
    }

    public static String getNotations(final String chainCode){
        return getChainFile(chainCode, CATALOG_NOTATION_FILE_NAME);
    }

    public static String getSkipBefore(final String chainCode){
        return getChainFile(chainCode, SKIP_BEFORE_FILE_NAME);
    }

    public static String getSkipAfter(final String chainCode){
        return getChainFile(chainCode, SKIP_AFTER_FILE_NAME);
    }
}
