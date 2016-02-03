package com.openprice.store;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MetadataLoaderTest {
    @Test
    public void loadMetadata_ShouldLoadStoreDb() throws Exception {
        StoreMetadata metadata = MetadataLoader.loadMetadata();

        // verify product category
        {
            assertEquals(36, metadata.getCategoryMap().size());

            ProductCategory uncategorized = metadata.getProductCategoryByCode("uncategorized");
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
