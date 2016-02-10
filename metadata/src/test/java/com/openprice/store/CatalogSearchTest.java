package com.openprice.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    public void listOfStringToJson() throws Exception{
        final List<String> list=new ArrayList<>();
        list.add("A");
        list.add("B");
        ObjectMapper mapper = new ObjectMapper();
        final String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
        System.out.println(jsonInString);
    }

}
