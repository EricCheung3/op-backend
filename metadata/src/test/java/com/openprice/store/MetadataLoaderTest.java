package com.openprice.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.openprice.store.data.StoreChainData;

public class MetadataLoaderTest {

    /**
     * validate store chains are well-formatted and nonRepeating, etc. see
     * https://github.com/opgt/op-backend/wiki/Store-Meta-data:-store-date.json
     */
    private static boolean validateStoreChainDataList(final List<StoreChainData> data) throws Exception{
        final Set<String> codeSet=new HashSet<String>();
        for(StoreChainData chain:data){
            if(chain.getCode().contains("\\s+"))
                throw new Exception("chain "+chain+"'s code should not contain spaces.");
            if(!chain.getCode().toLowerCase().equals(chain.getCode()))
                throw new Exception("chain "+chain+"'s code is not lowercased.");
            if(Character.isDigit(chain.getCode().charAt(0)))
                throw new Exception("chain "+chain+"'s code should not starts with a digit.");
            if(codeSet.contains(chain.getCode()))
                throw new Exception("repeating code. either a repeating chain or this chain's code has been used already:"+chain.getCode());
            codeSet.add(chain.getCode());
        }
        return true;
    }

    private static boolean validateStoreChainData(final StoreChainData[] data) throws Exception{
        return validateStoreChainDataList(Arrays.asList(data));
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeStartsWithNumber() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("1A").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData ex2=StoreChainData.builder().code("A").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1, ex2};
        validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeHasSpaces1() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("1 A").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1};
        validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeHasSpaces2() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code(" 1A").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1};
        validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeHasSpaces3() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("1A ").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1};
        validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeIsNotLowerCased() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("1 A").name("1 A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1};
        validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfCodeRepeats() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("AA").name("A A").category("afddfa").identity("afd").build();
        final StoreChainData ex2=StoreChainData.builder().code("AA").name("AA").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1, ex2};
        validateStoreChainData(example);
    }

    @Test(expected=Exception.class)
    public void exceptionThrownIfChainRepeats() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("AA").name("A A").category("afddfa").identity("afd").build();
        final StoreChainData ex1Copy=StoreChainData.builder().code("AA").name("A A").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1, ex1Copy};
        validateStoreChainData(example);
    }

    @Test
    public void validateNoException() throws Exception{
        final StoreChainData ex1=StoreChainData.builder().code("aa").name("A A").category("afddfa").identity("afd").build();
        final StoreChainData ex2=StoreChainData.builder().code("bb").name("BB").category("afddfa").identity("afd").build();
        final StoreChainData[] example=new StoreChainData[]{ex1, ex2};
        assertTrue(validateStoreChainData(example));
    }

    //validate after loading using loadFromJsonResource it is validate
    @Test
    public void validateLoadStoreChainData() throws Exception {
        final StoreChainData[] chainData = MetadataLoader.loadFromJsonResource(MetadataLoader.STORE_DATA_JSON, StoreChainData[].class);
        assertTrue(validateStoreChainData(chainData));
    }

    //validate after loading meta data the StoreChainData is validate
    @Test
    public void validate_after_loadMetadata() throws Exception {
        final StoreMetadata metadata= MetadataLoader.loadMetadata();
        final List<StoreChainData> dataSet=new ArrayList<StoreChainData>();
        metadata
        .getStoreChainMap()
        .entrySet()
        .stream()
        .forEach(e->dataSet.add(e.getValue().getStoreChainData()));
        assertTrue(validateStoreChainDataList(dataSet.stream().collect(Collectors.toList())));
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
        }

        // verify safeway
        {
            StoreChain safeway = metadata.getStoreChainByCode("safeway");
            assertNotNull(safeway);
            assertEquals("Safeway", safeway.getName());
            assertEquals(22, safeway.getBranches().size());
            assertEquals(608, safeway.getProducts().size());
        }

        // verify edojapan
        {
            StoreChain edojapan = metadata.getStoreChainByCode("edojapan");
            assertNotNull(edojapan);
            assertEquals("Edo Japan", edojapan.getName());
            assertEquals(28, edojapan.getBranches().size());
        }

    }
}
