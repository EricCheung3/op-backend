package com.openprice.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.openprice.store.data.StoreChainData;

import lombok.extern.slf4j.Slf4j;

/**
 *  TODO printing test code for a store.
 */
@Slf4j
public class MetadataLoaderTest {
    private final StoreMetadata metadata = MetadataLoader.loadMetadata();

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

    private static final String SPORTCHEK_CODE= "sportchek";
    @Test
    public void loadMetadataForSPORTCHEK_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(SPORTCHEK_CODE));
        assertTrue(validateConfigProperties(SPORTCHEK_CODE));
        assertTrue(validateHeaders(SPORTCHEK_CODE));
//        assertTrue(validateSkipBefore(SPORTCHEK_CODE));
        assertTrue(validateSkipAfter(SPORTCHEK_CODE));
        assertTrue(validateIdentify(SPORTCHEK_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(SPORTCHEK_CODE));
    }


    private static final String SHARED_CONFIG_FILE_ROOT= "sharedConfigurationFilesShouldNotCoincideWithAnyStoreNames";
    @Test
    public void loadMetadataForSHAREDCONFIGURATIONFILESSHOULDNOTCOINCIDEWITHANYSTORENAMES_CODE() throws Exception {
        assertTrue(metadata.getStoreChainByCode(SHARED_CONFIG_FILE_ROOT)==null);

        final Properties nonHeader = MetadataLoader.loadObjectFromJsonResource(ChainConfigFiles.getNonHeaderProperties(SHARED_CONFIG_FILE_ROOT), Properties.class);
        assertTrue(nonHeader != null &&  nonHeader.size() > 0);

        final Properties header = MetadataLoader.loadObjectFromJsonResource(ChainConfigFiles.getHeaders(SHARED_CONFIG_FILE_ROOT), Properties.class);
        assertTrue(header != null &&  header.size() > 0);

        final String[] skipAfter = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getSkipAfter(SHARED_CONFIG_FILE_ROOT), String[].class);
        assertTrue( skipAfter != null &&  skipAfter.length > 0);

        final String[] notItemsList = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getNotCatalogItemNames(SHARED_CONFIG_FILE_ROOT), String[].class);
        assertTrue( notItemsList != null &&  notItemsList.length > 0);
    }


    private static final String REXALL_CODE= "rexall";
    @Test
    public void loadMetadataForREXALL_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(REXALL_CODE));
        assertTrue(validateConfigProperties(REXALL_CODE));
        assertTrue(validateHeaders(REXALL_CODE));
        assertTrue(validateSkipBefore(REXALL_CODE));
        assertTrue(validateSkipAfter(REXALL_CODE));
        assertTrue(validateIdentify(REXALL_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(REXALL_CODE));
    }

    private static final String PETROCANADA_CODE= "petrocanada";
    @Test
    public void loadMetadataForPETROCANADA_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(PETROCANADA_CODE));
        assertTrue(validateConfigProperties(PETROCANADA_CODE));
        assertTrue(validateHeaders(PETROCANADA_CODE));
        assertTrue(validateSkipBefore(PETROCANADA_CODE));
        assertTrue(validateSkipAfter(PETROCANADA_CODE));
        assertTrue(validateIdentify(PETROCANADA_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(PETROCANADA_CODE));
    }



    private static final String _99SUPERMARKET_CODE= "_99supermarket";
    @Test
    public void loadMetadataFor_99SUPERMARKET_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(_99SUPERMARKET_CODE));
        assertTrue(validateConfigProperties(_99SUPERMARKET_CODE));
        assertTrue(validateHeaders(_99SUPERMARKET_CODE));
//        assertTrue(validateSkipBefore(_99SUPERMARKET_CODE));
        assertTrue(validateSkipAfter(_99SUPERMARKET_CODE));
        assertTrue(validateIdentify(_99SUPERMARKET_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(_99SUPERMARKET_CODE));
    }

    private static final String IKEA_CODE= "ikea";
    @Test
    public void loadMetadataForIKEA_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(IKEA_CODE));
        assertTrue(validateConfigProperties(IKEA_CODE));
        assertTrue(validateHeaders(IKEA_CODE));
//        assertTrue(validateSkipBefore(IKEA_CODE));
        assertTrue(validateSkipAfter(IKEA_CODE));
        assertTrue(validateIdentify(IKEA_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(IKEA_CODE));
    }

    private static final String RONA_CODE= "rona";
    @Test
    public void loadMetadataForRONA_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(RONA_CODE));
        assertTrue(validateConfigProperties(RONA_CODE));
        assertTrue(validateHeaders(RONA_CODE));
//        assertTrue(validateSkipBefore(RONA_CODE));
        assertTrue(validateSkipAfter(RONA_CODE));
        assertTrue(validateIdentify(RONA_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(RONA_CODE));
    }

    private static final String SUBWAY_CODE= "subway";
    @Test
    public void loadMetadataForSUBWAY_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(SUBWAY_CODE));
        assertTrue(validateConfigProperties(SUBWAY_CODE));
        assertTrue(validateHeaders(SUBWAY_CODE));
//        assertTrue(validateSkipBefore(SUBWAY_CODE));
        assertTrue(validateSkipAfter(SUBWAY_CODE));
        assertTrue(validateIdentify(SUBWAY_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(SUBWAY_CODE));
    }

    private static final String TOYSRUS_CODE= "toysrus";
    @Test
    public void loadMetadataForTOYSRUS_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(TOYSRUS_CODE));
        assertTrue(validateConfigProperties(TOYSRUS_CODE));
        assertTrue(validateHeaders(TOYSRUS_CODE));
//        assertTrue(validateSkipBefore(TOYSRUS_CODE));
        assertTrue(validateSkipAfter(TOYSRUS_CODE));
        assertTrue(validateIdentify(TOYSRUS_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(TOYSRUS_CODE));
    }



    private static final String PLANETORGANIC_CODE= "planetorganic";
    @Test
    public void loadMetadataForPLANETORGANIC_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(PLANETORGANIC_CODE));
        assertTrue(validateConfigProperties(PLANETORGANIC_CODE));
        assertTrue(validateHeaders(PLANETORGANIC_CODE));
//        assertTrue(validateSkipBefore(PLANETORGANIC_CODE));
        assertTrue(validateSkipAfter(PLANETORGANIC_CODE));
        assertTrue(validateIdentify(PLANETORGANIC_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(PLANETORGANIC_CODE));
    }


    private static final String NEWYORKFRIES_CODE= "newyorkfries";
    @Test
    public void loadMetadataForNEWYORKFRIES_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(NEWYORKFRIES_CODE));
        assertTrue(validateConfigProperties(NEWYORKFRIES_CODE));
        assertTrue(validateHeaders(NEWYORKFRIES_CODE));
//        assertTrue(validateSkipBefore(NEWYORKFRIES_CODE));
        assertTrue(validateSkipAfter(NEWYORKFRIES_CODE));
        assertTrue(validateIdentify(NEWYORKFRIES_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(NEWYORKFRIES_CODE));
    }


    private static final String LUCKY97_CODE= "lucky97";
    @Test
    public void loadMetadataForLUCKY97_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(LUCKY97_CODE));
        assertTrue(validateConfigProperties(LUCKY97_CODE));
        assertTrue(validateHeaders(LUCKY97_CODE));
        assertTrue(validateSkipBefore(LUCKY97_CODE));
        assertTrue(validateSkipAfter(LUCKY97_CODE));
        assertTrue(validateIdentify(LUCKY97_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(LUCKY97_CODE));
    }


    private static final String NOFRILLS_CODE= "nofrills";
    @Test
    public void loadMetadataForNOFRILLS_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(NOFRILLS_CODE));
        assertTrue(validateConfigProperties(NOFRILLS_CODE));
        assertTrue(validateHeaders(NOFRILLS_CODE));
        assertTrue(validateSkipBefore(NOFRILLS_CODE));
        assertTrue(validateSkipAfter(NOFRILLS_CODE));
        assertTrue(validateIdentify(NOFRILLS_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(NOFRILLS_CODE));
    }

    private static final String WALMART_CODE= "walmart";
    @Test
    public void loadMetadataForWALMART_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(WALMART_CODE));
        assertTrue(validateConfigProperties(WALMART_CODE));
        assertTrue(validateHeaders(WALMART_CODE));
        assertTrue(validateSkipBefore(WALMART_CODE));
        assertTrue(validateSkipAfter(WALMART_CODE));
        assertTrue(validateIdentify(WALMART_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(WALMART_CODE));
    }

    private static final String SEARS_CODE= "sears";
    @Test
    public void loadMetadataForSEARS_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(SEARS_CODE));
        assertTrue(validateConfigProperties(SEARS_CODE));
        assertTrue(validateHeaders(SEARS_CODE));
        assertTrue(validateSkipAfter(SEARS_CODE));
        assertTrue(validateIdentify(SEARS_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(SEARS_CODE));
    }

    private static final String THAIEXPRESS_CODE= "thaiexpress";
    @Test
    public void loadMetadataForTHAIEXPRESS_CODE() throws Exception {
        assertNotNull(metadata.getStoreChainByCode(THAIEXPRESS_CODE));
        assertTrue(validateConfigProperties(THAIEXPRESS_CODE));
        assertTrue(validateHeaders(THAIEXPRESS_CODE));
        assertTrue(validateSkipAfter(THAIEXPRESS_CODE));
        assertTrue(validateIdentify(THAIEXPRESS_CODE));
        assertTrue(validateLoadingNotCatalogItemNames(THAIEXPRESS_CODE));
    }

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
    public void catalog() {
        assertTrue(validateCatalog(SAFEWAY_CODE));
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

    public boolean validateHeaders(final String chainCode){
        return metadata.getStoreChainByCode(chainCode).getHeaderProperties().size() > 0;
    }

    public boolean validateConfigProperties(final String chainCode){
        return metadata.getStoreChainByCode(chainCode).getNonHeaderProperties().size() > 0;
    }

    public boolean validateIdentify(final String chainCode){
        return metadata.getStoreChainByCode(chainCode).getIdentifyFields().size() > 0;
    }

    public boolean validateSkipBefore(final String chainCode){
        return metadata.getStoreChainByCode(chainCode).getSkipBefore().size() > 0;
    }

    public boolean validateNotation(final String chainCode){
        return metadata.getStoreChainByCode(chainCode).getNotations().size() > 0;
    }

    @Test
    public void noNatationFileForEdoJapanNow() throws Exception {
        final String[] list = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getNotations(EDO_JAPAN_CODE), String[].class);
        assertTrue(list == null);
    }

    public boolean validateSkipAfter(final String chainCode){
        return metadata.getStoreChainByCode(chainCode).getSkipAfter().size() > 0;
    }

    public boolean validateCatalog(final String chainCode){
        log.debug("for chain "+chainCode+", there are "+metadata.getStoreChainByCode(chainCode).getProducts().size()+" products.");
        return metadata.getStoreChainByCode(chainCode).getProducts().size() > 0;
    }

    public boolean validateLoadingCategory(final String chainCode){
//        final String[] list = MetadataLoader.loadArrayFromJsonResource(ChainConfigFiles.getCategoriesOfStore(chainCode), String[].class);
//        return list != null &&  list.length > 0;
        return metadata.getStoreChainByCode(chainCode).getReceiptCategories().size() > 0;
    }

    public boolean validateLoadingNotCatalogItemNames(final String chainCode){
        return metadata.getStoreChainByCode(chainCode).getNotCatalogItemNames().size() > 0;
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
            assertEquals(321, rcss.getProducts().size());
            assertEquals(4, rcss.getIdentifyFields().size());
        }

        // verify safeway
        {
            StoreChain safeway = metadata.getStoreChainByCode(SAFEWAY_CODE);
            assertNotNull(safeway);
            assertEquals("Safeway", safeway.getName());
            assertEquals(22, safeway.getBranches().size());
            assertEquals(708, safeway.getProducts().size());
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
