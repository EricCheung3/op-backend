//package com.openprice.parser.generic;
//
//
///**
// * configuration file names
// */
//public class ConfigFiles {
//
////    public static final String CONFIG_PATH_PREFIX = "classpath:config/";
//    public static final String CONFIG_PATH_PREFIX = "/config/";
//
////    public static final String BRANCH_FILE_NAME = "branch.csv";
////    public static final String CATEGORY_FILE_NAME = "category.txt";
////    public static final String IDENTIFY_FIELD_FILE_NAME = "identify.txt";
////    public static final String CATALOG_FILE_NAME = "catalog.txt";
//
////    public static final String CATALOG_BLACK_LIST_FILE_NAME="notCatalogItemNames.txt";
////    public static final String CATALOG_NOTATION_FILE_NAME="notations.txt";
////
////    public static final String SKIP_BEFORE_FILE_NAME = "skipBeforeItemsFinish.txt";
////    public static final String SKIP_AFTER_FILE_NAME = "skipAfterItemsFinish.txt";
//
//    public static final String CONFIG_PROPERTY_FILE_NAME = "config.properties";
//    public static final String HEADER_CONFIG_FILE_NAME = "headerConfig.properties";
//
//    private static String getChainRoot(final String chain){
//        return CONFIG_PATH_PREFIX+chain+"/";
//    }
//
//    public static String getFileUnderChain(final String chain, final String fileName){
//        return getChainRoot(chain)+fileName;
//    }
//    public static String getFileUnderChainParser(final String chain, final String parser, final String fileName){
//        return getChainRoot(chain) + parser+"/"+fileName;
//    }
//
//    public static String blackListFile(final String chain, final String parser){
//        return getChainRoot(chain)+parser+"/"+CATALOG_BLACK_LIST_FILE_NAME;
//    }
//    public static String blackListFile(final String chain){
//        return getChainRoot(chain) + CATALOG_BLACK_LIST_FILE_NAME;
//    }
//
////    public static String branchFile(final String chain, final String parser){
////        return getChainRoot(chain)+parser+"/"+BRANCH_FILE_NAME;
////    }
////    public static String branchFile(final String chain){
////        return getChainRoot(chain)+BRANCH_FILE_NAME;
////    }
//
//    public static String categoryFile(final String chain, final String parser){
//        return getChainRoot(chain)+parser+"/"+CATEGORY_FILE_NAME;
//    }
//    public static String categoryFile(final String chain){
//        return getChainRoot(chain)+ CATEGORY_FILE_NAME;
//    }
//
////    public static String catalogFile(final String chain, final String parser){
////        return getChainRoot(chain)+parser+"/"+CATALOG_FILE_NAME;
////    }
////    public static String catalogFile(final String chain){
////        return getChainRoot(chain)+ CATALOG_FILE_NAME;
////    }
////
////    public static String identifyFile(final String chain, final String parser){
////        return getChainRoot(chain)+parser+"/"+IDENTIFY_FIELD_FILE_NAME;
////    }
////    public static String identifyFile(final String chain){
////        return getChainRoot(chain)+ IDENTIFY_FIELD_FILE_NAME;
////    }
//
//    public static String notationFile(final String chain, final String parser){
//        return getChainRoot(chain)+parser+"/"+CATALOG_NOTATION_FILE_NAME;
//    }
//    public static String notationFile(final String chain){
//        return getChainRoot(chain) + CATALOG_NOTATION_FILE_NAME;
//    }
//
//    public static String skipBeforeFile(final String chain, final String parser){
//        return getChainRoot(chain)+parser+"/"+SKIP_BEFORE_FILE_NAME;
//    }
//    public static String skipBeforeFile(final String chain){
//        return getChainRoot(chain) + SKIP_BEFORE_FILE_NAME;
//    }
//
//    public static String skipAfterFile(final String chain, final String parser){
//        return getChainRoot(chain)+parser+"/"+SKIP_AFTER_FILE_NAME;
//    }
//    public static String skipAfterFile(final String chain){
//        return getChainRoot(chain) + SKIP_AFTER_FILE_NAME;
//    }
//
//    public static String configPropertyFile(final String chain, final String parser){
//        return getChainRoot(chain)+parser+"/"+CONFIG_PROPERTY_FILE_NAME;
//    }
//    public static String configPropertyFile(final String chain){
//        return getChainRoot(chain) + CONFIG_PROPERTY_FILE_NAME;
//    }
//
//    public static String headerFile(final String chain, final String parser){
//        return getChainRoot(chain)+parser+"/"+HEADER_CONFIG_FILE_NAME;
//    }
//    public static String headerFile(final String chain){
//        return getChainRoot(chain) + HEADER_CONFIG_FILE_NAME;
//    }
//}
