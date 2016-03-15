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
}
