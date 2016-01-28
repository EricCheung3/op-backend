package com.openprice.parser.generic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigFilesTest {

    private static final String CHAIN="Safeway";
    private static final String PARSER="Safeway1";

    @Test
    public void getFileUnderChainTest(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/1.txt", ConfigFiles.getFileUnderChain(CHAIN, "1.txt"));
    }

    @Test
    public void getFileUnderChainParserTest(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/1.txt", ConfigFiles.getFileUnderChainParser(CHAIN, PARSER, "1.txt"));
    }

    @Test
    public void blackListTest1(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/"+ConfigFiles.CATALOG_BLACK_LIST_FILE_NAME,
                ConfigFiles.blackListFile(CHAIN, PARSER));
    }
    @Test
    public void blackListTest2(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+ConfigFiles.CATALOG_BLACK_LIST_FILE_NAME,
                ConfigFiles.blackListFile(CHAIN));
    }

    @Test
    public void branchTest1(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/"+ConfigFiles.BRANCH_FILE_NAME,
                ConfigFiles.branchFile(CHAIN, PARSER));
    }
    @Test
    public void branchTest2(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+ConfigFiles.BRANCH_FILE_NAME,
                ConfigFiles.branchFile(CHAIN));
    }

    @Test
    public void categoryTest1(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/"+ConfigFiles.CATEGORY_FILE_NAME,
                ConfigFiles.categoryFile(CHAIN, PARSER));
    }
    @Test
    public void categoryTest2(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+ConfigFiles.CATEGORY_FILE_NAME,
                ConfigFiles.categoryFile(CHAIN));
    }

    @Test
    public void catalogTest1(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/"+ConfigFiles.CATALOG_FILE_NAME,
                ConfigFiles.catalogFile(CHAIN, PARSER));
    }
    @Test
    public void catalogTest2(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+ConfigFiles.CATALOG_FILE_NAME,
                ConfigFiles.catalogFile(CHAIN));
    }

    @Test
    public void identifyTest1(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/"+ConfigFiles.IDENTIFY_FIELD_FILE_NAME,
                ConfigFiles.identifyFile(CHAIN, PARSER));
    }
    @Test
    public void identifyTest2(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+ConfigFiles.IDENTIFY_FIELD_FILE_NAME,
                ConfigFiles.identifyFile(CHAIN));
    }

    @Test
    public void notationTest1(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/"+ConfigFiles.CATALOG_NOTATION_FILE_NAME,
                ConfigFiles.notationFile(CHAIN, PARSER));
    }
    @Test
    public void notationTest2(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+ConfigFiles.CATALOG_NOTATION_FILE_NAME,
                ConfigFiles.notationFile(CHAIN));
    }

    @Test
    public void skipBeforeTest1(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/"+ConfigFiles.SKIP_BEFORE_FILE_NAME,
                ConfigFiles.skipBeforeFile(CHAIN, PARSER));
    }
    @Test
    public void skipBeforeTest2(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+ConfigFiles.SKIP_BEFORE_FILE_NAME,
                ConfigFiles.skipBeforeFile(CHAIN));
    }

    @Test
    public void skipAfterTest1(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/"+ConfigFiles.SKIP_AFTER_FILE_NAME,
                ConfigFiles.skipAfterFile(CHAIN, PARSER));
    }
    @Test
    public void skipAfterTest2(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+ConfigFiles.SKIP_AFTER_FILE_NAME,
                ConfigFiles.skipAfterFile(CHAIN));
    }

    @Test
    public void configPropertyTest1(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/"+ConfigFiles.BASE_CONFIG_FILE_NAME,
                ConfigFiles.configPropertyFile(CHAIN, PARSER));
    }
    @Test
    public void configPropertyTest2(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+ConfigFiles.BASE_CONFIG_FILE_NAME,
                ConfigFiles.configPropertyFile(CHAIN));
    }

    @Test
    public void headerTest1(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+PARSER+"/"+ConfigFiles.HEADER_CONFIG_FILE_NAME,
                ConfigFiles.headerFile(CHAIN, PARSER));
    }
    @Test
    public void headerTest2(){
        assertEquals(ConfigFiles.CONFIG_PATH_PREFIX+CHAIN+"/"+ConfigFiles.HEADER_CONFIG_FILE_NAME,
                ConfigFiles.headerFile(CHAIN));
    }


}
