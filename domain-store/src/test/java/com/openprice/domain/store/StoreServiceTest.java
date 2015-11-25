package com.openprice.domain.store;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {
    private static final String TEST_CHAIN_ID = "CHAIN_TEST_ID";
    private static final String TEST_CHAIN_CODE = "test";
    private static final String TEST_CHAIN_NAME = "My TestStore";

    @Mock
    StoreChainRepository storeChainRepositoryMock;

    @Mock
    StoreBranchRepository storeBranchRepositoryMock;

    @Mock
    CatalogRepository catalogRepositoryMock;

    @Mock
    MultipartFile fileMock;

    @InjectMocks
    StoreService serviceToTest;

    @Test
    public void loadCatalog_ShouldAddNewCatalog_IfNotExist() throws Exception {
        final StoreChain chain = getTestStoreChain();
        final Catalog catalogInput = new Catalog("milk", "1234");
        when(catalogRepositoryMock.findByChainAndCode(chain, catalogInput.getCode())).thenReturn(null);

        serviceToTest.loadCatalog(chain, new Catalog[]{catalogInput});

        assertEquals(1, chain.getCatalogs().size());
        assertEquals("milk-1234", chain.getCatalogs().get(0).getCode());
        verify(catalogRepositoryMock, times(1)).findByChainAndCode(chain, catalogInput.getCode());
        verify(catalogRepositoryMock, times(1)).save(any(Catalog.class));
        verify(storeChainRepositoryMock, times(1)).save(chain);
    }

    @Test
    public void loadCatalog_ShouldUpdateCatalog_IfExist() throws Exception {
        final StoreChain chain = getTestStoreChain();
        final Catalog catalog = chain.addCatalog("milk", "1234", "DAIRY", "4.99", "Milk", "Food,dairy,milk");
        when(catalogRepositoryMock.findByChainAndCode(chain, catalog.getCode())).thenReturn(catalog);

        final Catalog catalogInput = new Catalog("milk", "1234");
        catalogInput.setNaturalName("Homo Milk");

        serviceToTest.loadCatalog(chain, new Catalog[]{catalogInput});

        assertEquals(1, chain.getCatalogs().size());
        assertEquals("milk-1234", chain.getCatalogs().get(0).getCode());
        assertEquals("Homo Milk", catalog.getNaturalName());
        verify(catalogRepositoryMock, times(1)).findByChainAndCode(chain, catalog.getCode());
        verify(catalogRepositoryMock, times(1)).save(eq(catalog));
        verify(storeChainRepositoryMock, times(1)).save(chain);
    }

    private StoreChain getTestStoreChain() {
        final StoreChain chain = StoreChain.createStoreChain(TEST_CHAIN_CODE, TEST_CHAIN_NAME);
        chain.setId(TEST_CHAIN_ID);
        return chain;
    }
}
