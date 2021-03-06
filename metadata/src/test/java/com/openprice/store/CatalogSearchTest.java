package com.openprice.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class CatalogSearchTest {

    private static int LIMIT = 10;

    @Test
    public void emptyQueryReturnsEmptyResult() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("", "rcss", LIMIT);
         assertTrue(result.size() == 0);
    }

    @Test
    public void spaceQueryReturnsEmptyResult() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("  ", "rcss", LIMIT);
         assertTrue(result.size() == 0);
    }

    @Test
    public void findMatchingProducts_rcssTest_appls() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("appls", "rcss", LIMIT);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
         assertEquals("Apples", result.get(0).getNaturalName());
    }

    @Test
    public void findMatchingProducts_rcssTest_app() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("app", "rcss", LIMIT);
         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void findMatchingProducts_rcssTest_App1() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("App", "rcss", LIMIT);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void findMatchingProducts_rcssTest_2percent() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("2%", "rcss", LIMIT);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
//         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void findMatchingProducts_rcssTest_1percent() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("1%", "rcss", LIMIT);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
//         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void findMatchingProducts_rcssTest_Beatrice() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("Beatrice", "rcss", LIMIT);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
//         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void findMatchingProducts_rcssTest_Bea() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("Bea", "rcss", LIMIT);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
//         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void findMatchingProducts_rcssTest_milk() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("milk", "rcss", LIMIT);

         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
//         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void findMatchingProducts_rcssTest_lem() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("lem", "rcss", LIMIT);
         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
//         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void creamSafeway() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("cream", "rcss", LIMIT);
         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
//         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }

    @Test
    public void cheeseSafeway() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();
         List<CatalogProduct> result = metadata.findMatchingProducts("cheese", "rcss", LIMIT);
         result.stream().forEach( p -> System.out.println(p.getNaturalName()));
         assertTrue(result.size() > 0 && result.size() <= LIMIT);
//         assertTrue(result.stream().anyMatch(p -> p.getNaturalName().equals("Apples")));
    }
}
