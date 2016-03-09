package com.openprice.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class CatalogSearchTest {

    @Test
    public void findMatchingProducts_rcssTest() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("appls", "rcss", 10);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0);
         assertEquals("Apples", result.get(0).getNaturalName());
    }

    @Test
    public void findMatchingStoreChainByNameTest_b() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("b", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
    }

    @Test
    public void findMatchingStoreChainByNameTest_f() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("f", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
    }

    @Test
    public void findMatchingStoreChainByNameTest_S() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("S", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
    }

    @Test
    public void findMatchingStoreChainByNameTest_Su() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("Su", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
    }

    @Test
    public void findMatchingStoreChainByNameTest_St() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("St", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
    }


}
