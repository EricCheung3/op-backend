package com.openprice.store;

//These are universal config files that don't depend on a specific store
public class UniversalConfigFiles {

    private static String PREFIX="/";

    //generic, store-independent data.
    private static final String STORES_DATA_JSON = "store-data.json";
    private static final String CATEGORY_DATA_JSON = "category-data.json";//our own categories

    private static String getFile(final String fileName){
        return PREFIX + fileName;
    }

    public static String getStoresFile(){
        return getFile(STORES_DATA_JSON);
    }

    public static String getCategoyFile(){
        return getFile(CATEGORY_DATA_JSON);
    }
}
