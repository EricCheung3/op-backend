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

    //categories appear on this chain's receipt: note it is different from our own categories
    private static final String CATEGORY_FILE_NAME = "receipt-categories.json";

    //black list of names that are not item names
    private static final String CATALOG_BLACK_LIST_FILE_NAME="not-catalog-item-names.json";

    //special notations used by chains. Like "MRJ" used by RCSS
    private static final String CATALOG_NOTATION_FILE_NAME="notations.json";

    //strings/names that we should skip BEFORE items BEGIN on a receipt
    private static final String SKIP_BEFORE_FILE_NAME = "skip-before-items-begin.json";

    //strings/names that we should skip AFTER items FINISH on a receipt (usually these are the noises after the total line)
    private static final String SKIP_AFTER_FILE_NAME = "skip-after-items-finish.json";

    //config properties (non-header)
    private static final String NON_HEADER_PROPERTIES_FILE_NAME = "non-header-properties.json";

    //headers
    private static final String HEADER_PROPERTIES_FILE_NAME = "headers.json";

    private static String getChainFile(final String chainCode, final String fileName){
            return PREFIX + chainCode + "/" + fileName;
    }

    public static String getNonHeaderProperties(final String chainCode){
        return getChainFile(chainCode, NON_HEADER_PROPERTIES_FILE_NAME);
    }

    public static String getHeaders(final String chainCode){
        return getChainFile(chainCode, HEADER_PROPERTIES_FILE_NAME);
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
