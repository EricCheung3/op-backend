package com.openprice.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.openprice.store.data.StoreChainData;

public class MetadataLoaderTest {

    public boolean validateNotation(final String chainCode){
        final String[] list = MetadataLoader.loadFromJsonResource(ChainConfigFiles.getNotations(chainCode), String[].class);
        return list != null &&  list.length > 0;
    }

    @Test
    public void notation() throws Exception {
        //assertTrue(validateNotation("edoJapan"));
        assertTrue(validateNotation("rcss"));
        assertTrue(validateNotation("safeway"));
    }

    @Test
    public void noNatationFileForEdoJapanNow() throws Exception {
        final String[] list = MetadataLoader.loadFromJsonResource(ChainConfigFiles.getNotations("edojapan"), String[].class);
        assertTrue(list == null);
    }

    public boolean validateSkipAfter(final String chainCode){
        final String[] list = MetadataLoader.loadFromJsonResource(ChainConfigFiles.getSkipAfter(chainCode), String[].class);
        return list != null &&  list.length > 0;
    }

    @Test
    public void skipAfter() throws Exception {
        assertTrue(validateSkipAfter("edoJapan"));
        assertTrue(validateSkipAfter("rcss"));
        assertTrue(validateSkipAfter("safeway"));
    }

    public boolean validateSkipBefore(final String chainCode){
        final String[] list = MetadataLoader.loadFromJsonResource(ChainConfigFiles.getSkipBefore(chainCode), String[].class);
        return list != null &&  list.length > 0;
    }

    @Test
    public void skipBefore() throws Exception {
        //assertTrue(validateSkipBefore("edoJapan"));
        assertTrue(validateSkipBefore("rcss"));
        assertTrue(validateSkipBefore("safeway"));
    }

    //This should pass.Empty file or non-empty file with empty lines should be allowed.
    @Test
    public void EmptySkipBeforeForEdoJapanNow() throws Exception {
        final String[] list = MetadataLoader.loadFromJsonResource(ChainConfigFiles.getSkipBefore("edojapan"), String[].class);
        assertTrue( list != null &&  list.length == 0);
    }

    public boolean validateIdentify(final String chainCode){
        final String[] list = MetadataLoader.loadFromJsonResource(ChainConfigFiles.getIdentify(chainCode), String[].class);
        return list != null &&  list.length > 0;
    }

    @Test
    public void identifyEdoJapan() throws Exception {
        assertTrue(validateIdentify("edoJapan"));
    }

    @Test
    public void identifySafeway() throws Exception {
        assertTrue(validateIdentify("safeway"));
    }

    @Test
    public void identifyRCSS() throws Exception {
        assertTrue(validateIdentify("rcss"));
    }

    public boolean validateLoadingCategory(final String chainCode){
        final String[] list = MetadataLoader.loadFromJsonResource(ChainConfigFiles.getCategoriesOfStore(chainCode), String[].class);
        return list != null &&  list.length > 0;
    }

    @Test
    public void testValidateLoadingCategoryForSafeway() throws Exception {
        assertTrue(validateLoadingCategory("safeway"));
    }

    @Test
    public void testValidateLoadingCategoryForRCSS() throws Exception {
        assertTrue(validateLoadingCategory("rcss"));
    }

    public boolean validateLoadingNotCatalogItemNames(final String chainCode){
        final String[] notItemsList = MetadataLoader.loadFromJsonResource("/"+chainCode+"/not-catalog-item-names.json", String[].class);
        return notItemsList != null &&  notItemsList.length > 0;
    }

    @Test
    public void testLoadNotCatalogItemNamesForSafeway() throws Exception {
        assertTrue(validateLoadingNotCatalogItemNames("safeway"));
    }
    @Test
    public void testLoadNotCatalogItemNamesForRCSS() throws Exception {
        assertTrue(validateLoadingNotCatalogItemNames("rcss"));
    }
    @Test
    public void testLoadNotCatalogItemNamesForEdoJapn() throws Exception {
        assertTrue(validateLoadingNotCatalogItemNames("edojapan"));
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeStartsWithNumber() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("1A").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData ex2=StoreChainData.builder().code("A").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1, ex2};
        MetadataLoader.validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeHasSpaces1() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("1 A").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1};
        MetadataLoader.validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeHasSpaces2() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code(" 1A").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1};
        MetadataLoader.validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeHasSpaces3() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("1A ").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1};
        MetadataLoader.validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeIsNotLowerCased() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("1 A").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1};
        MetadataLoader.validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeRepeats() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("AA").name("A A").category("afddfa").identity("afd").build();
        final StoreChainData ex2=StoreChainData.builder().code("AA").name("AA").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1, ex2};
        MetadataLoader.validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfChainRepeats() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("AA").name("A A").category("afddfa").identity("afd").build();
        final StoreChainData ex1Copy=StoreChainData.builder().code("AA").name("A A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1, ex1Copy};
        MetadataLoader.validateStoreChainData(example);
    }

    @Test
    public void validateNoException() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("aa").name("A A").category("afddfa").identity("afd").build();
        final StoreChainData ex2=StoreChainData.builder().code("bb").name("BB").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1, ex2};
        assertTrue(MetadataLoader.validateStoreChainData(example));
    }

    @Test
    public void loadMetadata_ShouldLoadStoreDb() throws Exception {
        final StoreMetadata metadata = MetadataLoader.loadMetadata();


        // verify product category
        {
            assertEquals(36, metadata.getCategoryMap().size());

            ProductCategory uncategorized = metadata.getProductCategoryByCode(ProductCategory.UNCATEGORIZED);
            assertNotNull(uncategorized);
            assertEquals("Uncategorized", uncategorized.getLabel());
        }

        // verify rcss
        {
            StoreChain rcss = metadata.getStoreChainByCode("rcss");
            assertNotNull(rcss);
            assertEquals("Superstore", rcss.getName());
            assertEquals(8, rcss.getBranches().size());
            assertEquals(323, rcss.getProducts().size());
            assertEquals(4, rcss.getIdentifyFields().size());
        }

        // verify safeway
        {
            StoreChain safeway = metadata.getStoreChainByCode("safeway");
            assertNotNull(safeway);
            assertEquals("Safeway", safeway.getName());
            assertEquals(22, safeway.getBranches().size());
            assertEquals(608, safeway.getProducts().size());
            assertEquals(2, safeway.getIdentifyFields().size());
        }

        // verify edojapan
        {
            StoreChain edojapan = metadata.getStoreChainByCode("edojapan");
            assertNotNull(edojapan);
            assertEquals("Edo Japan", edojapan.getName());
            assertEquals(28, edojapan.getBranches().size());
            assertEquals(1, edojapan.getIdentifyFields().size());
        }

    }
}
