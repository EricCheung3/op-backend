package com.openprice.helper;

public class PrintCodeForMetadataLoaderTest {

    public static void main(String[] args){
        testCodes("planetorganic");
    }

    public static void testCodes(final String chain){
        final String CODE = chain.toUpperCase() + "_CODE";
        final String indent="    ";
        final String s = "private static final String " + CODE + "= \""+chain+"\";\n"
              + "@Test \n"
              + "public void loadMetadataFor" +CODE + "() throws Exception {\n"
              + indent+ "assertNotNull(metadata.getStoreChainByCode(" + CODE + "));\n"
              + indent+ "assertTrue(validateConfigProperties(" + CODE + "));\n"
              + indent+ "assertTrue(validateHeaders(" + CODE + "));\n"
              + indent+ "assertTrue(validateSkipBefore(" + CODE + "));\n"
              + indent+ "assertTrue(validateSkipAfter(" + CODE + "));\n"
              + indent+ "assertTrue(validateIdentify(" + CODE + "));\n"
              + indent+ "assertTrue(validateLoadingNotCatalogItemNames(" + CODE + "));\n"
              + "}\n";

        System.out.println(s);

    }
}
