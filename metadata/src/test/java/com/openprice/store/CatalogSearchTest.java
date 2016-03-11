package com.openprice.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class CatalogSearchTest {

    @Test
    public void findMatchingProducts_rcssTest_appls() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("appls", "rcss", 10);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0);
         assertEquals("Apples", result.get(0).getNaturalName());
    }

    @Test
    public void findMatchingProducts_rcssTest_app() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("app", "rcss", 10);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0);
         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void findMatchingProducts_rcssTest_App1() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("App", "rcss", 10);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0);
         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_b_AllStartsWithb() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("b", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue("Baseline".toLowerCase().startsWith("b".trim()));
        assertTrue(result.stream().anyMatch(c -> c.getName().toLowerCase().startsWith("b")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_H_AllStartsWithH() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("H", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().toLowerCase().startsWith("h")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_f_AllStartsWithf() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("f", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().toLowerCase().startsWith("f")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_S_AllStartsWithf() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("S", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().toLowerCase().startsWith("s")));
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

    @Test
    public void findMatchingStoreChainByNameTest_Sup() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("Sup", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("Superstore")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_9() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("9", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("99 SUPERMARKET")));
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("Lucky 97")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_97() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("97", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("Lucky 97")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_Bay() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("Bay", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("Hudson's Bay")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_bes_lower() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("bes", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("Best Buy")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_bes_upper() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("Bes", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("Best Buy")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_be() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("be", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("Best Buy")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_sa() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("sa", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("Safeway")));
    }

    @Test
    public void findMatchingStoreChainByNameTest_saf() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
        List<StoreChain> result = metadata.findMatchingStoreChainByName("saf", 10);
        result.stream().forEach( c -> System.out.println(c.getName()));
        assertTrue(result.size() > 0);
        assertTrue(result.stream().anyMatch(c -> c.getName().equals("Safeway")));
    }
}
