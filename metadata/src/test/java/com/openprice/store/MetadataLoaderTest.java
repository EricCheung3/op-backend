package com.openprice.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.openprice.store.data.StoreChainData;

/**
 *  TODO printing test code for a store.
 */
public class MetadataLoaderTest {
    private static final String RCSS_CODE="rcss";
    private static final String SAFEWAY_CODE="safeway";
    private static final String EDO_JAPAN_CODE="edojapan";
    private static final String COSTCO_CODE="costco";
    private static final String TIM_HORTONS_CODE="timhortons";
    private static final String SOBEYS_CODE="sobeys";
    private static final String TAndT_CODE="tandt";
    private static final String SHOPPERS_CODE="shoppers";
    private static final String LONDON_DRUGS_CODE="londondrugs";
    private static final String MCDONALDS_CODE="mcdonalds";

    @Test
    public void configProperties() throws Exception {
        assertTrue(validateConfigProperties(EDO_JAPAN_CODE));
        assertTrue(validateConfigProperties(RCSS_CODE));
        assertTrue(validateConfigProperties(SAFEWAY_CODE));
        assertTrue(validateConfigProperties(COSTCO_CODE));
        assertTrue(validateConfigProperties(TIM_HORTONS_CODE));
        assertTrue(validateConfigProperties(SOBEYS_CODE));
        assertTrue(validateConfigProperties(TAndT_CODE));
        assertTrue(validateConfigProperties(SHOPPERS_CODE));
        assertTrue(validateConfigProperties(LONDON_DRUGS_CODE));
        assertTrue(validateConfigProperties(MCDONALDS_CODE));

    }

    public boolean validateHeaders(final String chainCode){
        final Properties prop = MetadataLoader.loadObjectFromJsonResource(ChainConfigFiles.getHeaders(chainCode), Properties.class);
        return prop != null &&  prop.size() > 0;
    }

    @Test
    public void headerProperties() throws Exception {
        assertTrue(validateHeaders(EDO_JAPAN_CODE));
        assertTrue(validateHeaders(RCSS_CODE));
        assertTrue(validateHeaders(SAFEWAY_CODE));
        assertTrue(validateHeaders(COSTCO_CODE));
        assertTrue(validateHeaders(TIM_HORTONS_CODE));
        assertTrue(validateHeaders(SOBEYS_CODE));
        assertTrue(validateHeaders(TAndT_CODE));
        assertTrue(validateHeaders(SHOPPERS_CODE));
        assertTrue(validateHeaders(LONDON_DRUGS_CODE));
        assertTrue(validateHeaders(MCDONALDS_CODE));
    }

    @Test
    public void skipAfter() throws Exception {
        assertTrue(validateSkipAfter(EDO_JAPAN_CODE));
        assertTrue(validateSkipAfter(RCSS_CODE));
        assertTrue(validateSkipAfter(SAFEWAY_CODE));
        assertTrue(validateSkipAfter(COSTCO_CODE));
        assertTrue(validateSkipAfter(TIM_HORTONS_CODE));
        assertTrue(validateSkipAfter(SOBEYS_CODE));
        assertTrue(validateSkipAfter(TAndT_CODE));
        assertTrue(validateSkipAfter(SHOPPERS_CODE));
        assertTrue(validateSkipAfter(LONDON_DRUGS_CODE));
        assertTrue(validateSkipAfter(MCDONALDS_CODE));
    }

    @Test
    public void skipBefore() throws Exception {
        //assertTrue(validateSkipBefore(EDO_JAPAN_CODE));
        assertTrue(validateSkipBefore(RCSS_CODE));
        assertTrue(validateSkipBefore(SAFEWAY_CODE));
        assertTrue(validateSkipBefore(COSTCO_CODE));
        assertTrue(validateSkipBefore(SOBEYS_CODE));
        assertTrue(validateSkipBefore(TAndT_CODE));
        assertTrue(validateSkipBefore(LONDON_DRUGS_CODE));
    }

    @Test
    public void identify() throws Exception {
        assertTrue(validateIdentify(EDO_JAPAN_CODE));
        assertTrue(validateIdentify(SAFEWAY_CODE));
        assertTrue(validateIdentify(RCSS_CODE));
        assertTrue(validateIdentify(COSTCO_CODE));
        assertTrue(validateIdentify(TIM_HORTONS_CODE));
        assertTrue(validateIdentify(SOBEYS_CODE));
        assertTrue(validateIdentify(TAndT_CODE));
        assertTrue(validateIdentify(LONDON_DRUGS_CODE));
        assertTrue(validateIdentify(MCDONALDS_CODE));
    }

    @Test
    public void notation() throws Exception {
        //assertTrue(validateNotation(EDO_JAPAN_CODE));
        assertTrue(validateNotation(RCSS_CODE));
        assertTrue(validateNotation(SAFEWAY_CODE));
    }


    @Test
    public void testValidateLoadingCategory() throws Exception {
        assertTrue(validateLoadingCategory(SAFEWAY_CODE));
        assertTrue(validateLoadingCategory(RCSS_CODE));
        assertTrue(validateLoadingCategory(TAndT_CODE));
    }

    @Test
    public void testLoadNotCatalogItemNames() throws Exception {
        assertTrue(validateLoadingNotCatalogItemNames(SAFEWAY_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(RCSS_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(EDO_JAPAN_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(COSTCO_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(TIM_HORTONS_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(SOBEYS_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(TAndT_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(SHOPPERS_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(LONDON_DRUGS_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(MCDONALDS_CODE));
    }

    @Test
    public void test1(){
        final List<String> list=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        list2.add("A");
        list.addAll(list2);
    }

    public boolean validateConfigProperties(final String chainCode){
        final Properties prop = MetadataLoader.loadObjectFromJsonResource(ChainConfigFiles.getNonHeaderProperties(chainCode), Properties.class);
        return prop != null &&  prop.size() > 0;
    }

    public boolean validateIdentify(final String chainCode){
        final String[] list = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getIdentify(chainCode), String[].class);
        return list != null &&  list.length > 0;
    }

    public boolean validateSkipBefore(final String chainCode){
        final String[] list = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getSkipBefore(chainCode), String[].class);
        return list != null &&  list.length > 0;
    }

    public boolean validateNotation(final String chainCode){
        final String[] list = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getNotations(chainCode), String[].class);
        return list != null &&  list.length > 0;
    }

    @Test
    public void noNatationFileForEdoJapanNow() throws Exception {
        final String[] list = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getNotations(EDO_JAPAN_CODE), String[].class);
        assertTrue(list == null);
    }

    public boolean validateSkipAfter(final String chainCode){
        final String[] list = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getSkipAfter(chainCode), String[].class);
        System.out.println(list);
        return list != null &&  list.length > 0;
    }

    public boolean validateLoadingCategory(final String chainCode){
        final String[] list = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getCategoriesOfStore(chainCode), String[].class);
        return list != null &&  list.length > 0;
    }

    public boolean validateLoadingNotCatalogItemNames(final String chainCode){
        final String[] notItemsList = MetadataLoader.loadArrayFromJsonResource("/"+chainCode+"/not-catalog-item-names.json", String[].class);
        return notItemsList != null &&  notItemsList.length > 0;
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
        assertTrue(metadata.getStoreChainMap().containsKey(RCSS_CODE));
        assertTrue(metadata.getStoreChainMap().containsKey(SAFEWAY_CODE));
        assertTrue(metadata.getStoreChainMap().containsKey(EDO_JAPAN_CODE));
        assertTrue(metadata.getStoreChainMap().containsKey(COSTCO_CODE));
        assertTrue(metadata.getStoreChainMap().containsKey(TIM_HORTONS_CODE));
        assertTrue(metadata.getStoreChainMap().containsKey(SOBEYS_CODE));
        assertTrue(metadata.getStoreChainMap().containsKey(TAndT_CODE));
        assertTrue(metadata.getStoreChainMap().containsKey(SHOPPERS_CODE));
        assertTrue(metadata.getStoreChainMap().containsKey(LONDON_DRUGS_CODE));
        assertTrue(metadata.getStoreChainMap().containsKey(MCDONALDS_CODE));

        assertNotNull(metadata.getStoreChainByCode(RCSS_CODE));
        assertNotNull(metadata.getStoreChainByCode(SAFEWAY_CODE));
        assertNotNull(metadata.getStoreChainByCode(EDO_JAPAN_CODE));
        assertNotNull(metadata.getStoreChainByCode(COSTCO_CODE));
        assertNotNull(metadata.getStoreChainByCode(TIM_HORTONS_CODE));
        assertNotNull(metadata.getStoreChainByCode(SOBEYS_CODE));
        assertNotNull(metadata.getStoreChainByCode(TAndT_CODE));
        assertNotNull(metadata.getStoreChainByCode(SHOPPERS_CODE));
        assertNotNull(metadata.getStoreChainByCode(LONDON_DRUGS_CODE));
        assertNotNull(metadata.getStoreChainByCode(MCDONALDS_CODE));

        assertTrue(metadata.getStoreChainByCode(RCSS_CODE).getBranches().size()>0);
        assertTrue(metadata.getStoreChainByCode(SAFEWAY_CODE).getBranches().size()>0);
        assertTrue(metadata.getStoreChainByCode(EDO_JAPAN_CODE).getBranches().size()>0);
        assertTrue(metadata.getStoreChainByCode(COSTCO_CODE).getBranches().size()==0);
        assertTrue(metadata.getStoreChainByCode(TIM_HORTONS_CODE).getBranches().size()==0);
        assertTrue(metadata.getStoreChainByCode(SOBEYS_CODE).getBranches().size()==0);
        assertTrue(metadata.getStoreChainByCode(TAndT_CODE).getBranches().size()==0);
        assertTrue(metadata.getStoreChainByCode(SHOPPERS_CODE).getBranches().size()==0);
        assertTrue(metadata.getStoreChainByCode(LONDON_DRUGS_CODE).getBranches().size()==0);
        assertTrue(metadata.getStoreChainByCode(MCDONALDS_CODE).getBranches().size()==0);

        assertNotNull(metadata.getStoreChainByCode(RCSS_CODE).getHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(SAFEWAY_CODE).getHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(EDO_JAPAN_CODE).getHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(COSTCO_CODE).getHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(TIM_HORTONS_CODE).getHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(SOBEYS_CODE).getHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(TAndT_CODE).getHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(SHOPPERS_CODE).getHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(LONDON_DRUGS_CODE).getHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(MCDONALDS_CODE).getHeaderProperties());

        assertNotNull(metadata.getStoreChainByCode(RCSS_CODE).getNonHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(SAFEWAY_CODE).getNonHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(EDO_JAPAN_CODE).getNonHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(COSTCO_CODE).getNonHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(TIM_HORTONS_CODE).getNonHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(SOBEYS_CODE).getNonHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(TAndT_CODE).getNonHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(SHOPPERS_CODE).getNonHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(LONDON_DRUGS_CODE).getNonHeaderProperties());
        assertNotNull(metadata.getStoreChainByCode(MCDONALDS_CODE).getNonHeaderProperties());

        assertTrue(metadata.getStoreChainByCode(RCSS_CODE).getNotCatalogItemNames().size()>0);
        assertTrue(metadata.getStoreChainByCode(SAFEWAY_CODE).getNotCatalogItemNames().size()>0);
        assertTrue(metadata.getStoreChainByCode(EDO_JAPAN_CODE).getNotCatalogItemNames().size()>0);
        assertTrue(metadata.getStoreChainByCode(COSTCO_CODE).getNotCatalogItemNames().size()>0);
        assertTrue(metadata.getStoreChainByCode(TIM_HORTONS_CODE).getNotCatalogItemNames().size()>0);
        assertTrue(metadata.getStoreChainByCode(SOBEYS_CODE).getNotCatalogItemNames().size()>0);
        assertTrue(metadata.getStoreChainByCode(TAndT_CODE).getNotCatalogItemNames().size()>0);
        assertTrue(metadata.getStoreChainByCode(SHOPPERS_CODE).getNotCatalogItemNames().size()>0);
        assertTrue(metadata.getStoreChainByCode(LONDON_DRUGS_CODE).getNotCatalogItemNames().size()>0);
        assertTrue(metadata.getStoreChainByCode(MCDONALDS_CODE).getNotCatalogItemNames().size()>0);

        assertTrue(metadata.getStoreChainByCode(RCSS_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(SAFEWAY_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(EDO_JAPAN_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(COSTCO_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(TIM_HORTONS_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(SOBEYS_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(TAndT_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(SHOPPERS_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(LONDON_DRUGS_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(MCDONALDS_CODE).getIdentifyFields().size()>0);

        assertTrue(metadata.getStoreChainByCode(RCSS_CODE).getReceiptCategories().size()>0);
        assertTrue(metadata.getStoreChainByCode(SAFEWAY_CODE).getReceiptCategories().size()>0);
        assertTrue(metadata.getStoreChainByCode(EDO_JAPAN_CODE).getReceiptCategories().size()==0);
        assertTrue(metadata.getStoreChainByCode(COSTCO_CODE).getReceiptCategories().size()==0);
        assertTrue(metadata.getStoreChainByCode(TIM_HORTONS_CODE).getReceiptCategories().size()==0);
        assertTrue(metadata.getStoreChainByCode(SOBEYS_CODE).getReceiptCategories().size()==0);
        assertTrue(metadata.getStoreChainByCode(TAndT_CODE).getReceiptCategories().size()>0);
        assertTrue(metadata.getStoreChainByCode(SHOPPERS_CODE).getReceiptCategories().size()==0);
        assertTrue(metadata.getStoreChainByCode(LONDON_DRUGS_CODE).getReceiptCategories().size()==0);
        assertTrue(metadata.getStoreChainByCode(MCDONALDS_CODE).getReceiptCategories().size()==0);

        assertTrue(metadata.getStoreChainByCode(RCSS_CODE).getNotations().size()>0);
        assertTrue(metadata.getStoreChainByCode(SAFEWAY_CODE).getSkipBefore().size()>0);
        assertTrue(metadata.getStoreChainByCode(EDO_JAPAN_CODE).getSkipBefore().size()==0);
        assertTrue(metadata.getStoreChainByCode(COSTCO_CODE).getSkipBefore().size()>0);
        assertTrue(metadata.getStoreChainByCode(TIM_HORTONS_CODE).getSkipBefore().size()==0);
        assertTrue(metadata.getStoreChainByCode(SOBEYS_CODE).getSkipBefore().size()>0);
        assertTrue(metadata.getStoreChainByCode(TAndT_CODE).getSkipBefore().size()>0);
        assertTrue(metadata.getStoreChainByCode(SHOPPERS_CODE).getSkipBefore().size()==0);
        assertTrue(metadata.getStoreChainByCode(LONDON_DRUGS_CODE).getSkipBefore().size()>0);
        assertTrue(metadata.getStoreChainByCode(MCDONALDS_CODE).getSkipBefore().size()==0);

        assertTrue(metadata.getStoreChainByCode(RCSS_CODE).getSkipAfter().size()>0);
        assertTrue(metadata.getStoreChainByCode(SAFEWAY_CODE).getSkipAfter().size()>0);
        assertTrue(metadata.getStoreChainByCode(EDO_JAPAN_CODE).getSkipAfter().size()>0);
        assertTrue(metadata.getStoreChainByCode(COSTCO_CODE).getSkipAfter().size()>0);
        assertTrue(metadata.getStoreChainByCode(TIM_HORTONS_CODE).getSkipAfter().size()>0);
        assertTrue(metadata.getStoreChainByCode(SOBEYS_CODE).getSkipAfter().size()>0);
        assertTrue(metadata.getStoreChainByCode(TAndT_CODE).getSkipAfter().size()>0);
        assertTrue(metadata.getStoreChainByCode(SHOPPERS_CODE).getSkipAfter().size()>0);
        assertTrue(metadata.getStoreChainByCode(LONDON_DRUGS_CODE).getSkipAfter().size()>0);
        assertTrue(metadata.getStoreChainByCode(MCDONALDS_CODE).getSkipAfter().size()>0);

        assertTrue(metadata.getStoreChainByCode(RCSS_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(SAFEWAY_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(EDO_JAPAN_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(COSTCO_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(TIM_HORTONS_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(SOBEYS_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(TAndT_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(SHOPPERS_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(LONDON_DRUGS_CODE).getIdentifyFields().size()>0);
        assertTrue(metadata.getStoreChainByCode(MCDONALDS_CODE).getIdentifyFields().size()>0);

        assertTrue(metadata.getStoreChainByCode(RCSS_CODE).getProducts().size()>0);
        assertTrue(metadata.getStoreChainByCode(SAFEWAY_CODE).getProducts().size()>0);
        assertTrue(metadata.getStoreChainByCode(EDO_JAPAN_CODE).getProducts().size()==0);//no catalog data for edo japan yet
        assertTrue(metadata.getStoreChainByCode(COSTCO_CODE).getProducts().size()==0);
        assertTrue(metadata.getStoreChainByCode(TIM_HORTONS_CODE).getProducts().size()==0);
        assertTrue(metadata.getStoreChainByCode(SOBEYS_CODE).getProducts().size()==0);
        assertTrue(metadata.getStoreChainByCode(TAndT_CODE).getProducts().size()==0);
        assertTrue(metadata.getStoreChainByCode(SHOPPERS_CODE).getProducts().size()==0);
        assertTrue(metadata.getStoreChainByCode(LONDON_DRUGS_CODE).getProducts().size()==0);
        assertTrue(metadata.getStoreChainByCode(MCDONALDS_CODE).getProducts().size()==0);

        // verify product category
        {
            assertEquals(36, metadata.getCategoryMap().size());

            ProductCategory uncategorized = metadata.getProductCategoryByCode(ProductCategory.UNCATEGORIZED);
            assertNotNull(uncategorized);
            assertEquals("Uncategorized", uncategorized.getLabel());
        }

        // verify rcss
        {
            StoreChain rcss = metadata.getStoreChainByCode(RCSS_CODE);
            assertNotNull(rcss);
            assertEquals("Superstore", rcss.getName());
            assertEquals(8, rcss.getBranches().size());
            assertEquals(323, rcss.getProducts().size());
            assertEquals(4, rcss.getIdentifyFields().size());
        }

        // verify safeway
        {
            StoreChain safeway = metadata.getStoreChainByCode(SAFEWAY_CODE);
            assertNotNull(safeway);
            assertEquals("Safeway", safeway.getName());
            assertEquals(22, safeway.getBranches().size());
            assertEquals(608, safeway.getProducts().size());
            assertEquals(2, safeway.getIdentifyFields().size());
        }

        // verify edojapan
        {
            StoreChain edojapan = metadata.getStoreChainByCode(EDO_JAPAN_CODE);
            assertNotNull(edojapan);
            assertEquals("Edo Japan", edojapan.getName());
            assertEquals(28, edojapan.getBranches().size());
            assertEquals(1, edojapan.getIdentifyFields().size());
        }

    }
}
