package com.openprice.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.openprice.store.data.StoreChainData;

public class MetadataLoaderTest {

    @Test
    public void testLoadNotCatalogItemNamesForSafeway() throws Exception {
        final String[] notItemsList = MetadataLoader.loadFromJsonResource("/safeway/not-catalog-item-names.json", String[].class);
        assertNotNull(notItemsList);
        assertTrue(notItemsList.length>0);
    }

    @Test
    public void testLoadNotCatalogItemNamesForRCSS() throws Exception {
        final String[] notItemsList = MetadataLoader.loadFromJsonResource("/rcss/not-catalog-item-names.json", String[].class);
        assertNotNull(notItemsList);
        assertTrue(notItemsList.length>0);
    }

    @Test
    public void testLoadNotCatalogItemNamesForEdoJapn() throws Exception {
        final String[] notItemsList = MetadataLoader.loadFromJsonResource("/edojapan/not-catalog-item-names.json", String[].class);
        assertNotNull(notItemsList);
        assertTrue(notItemsList.length>0);
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
