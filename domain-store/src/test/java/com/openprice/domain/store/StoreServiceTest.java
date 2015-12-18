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

import com.openprice.domain.product.ProductData;

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
        final ProductData input = ProductData.builder().name("milk").number("1234").productCategory("dairy").build();
        when(catalogRepositoryMock.findByChainAndCatalogCode(chain, input.getCatalogCode())).thenReturn(null);

        serviceToTest.loadCatalog(chain, new ProductData[]{input});

        //assertEquals(1, chain.getCatalogs().size());
        //assertEquals("milk_1234", chain.getCatalogs().get(0).getCode());
        verify(catalogRepositoryMock, times(1)).findByChainAndCatalogCode(chain, input.getCatalogCode());
        verify(catalogRepositoryMock, times(1)).save(any(CatalogProduct.class));
        verify(storeChainRepositoryMock, times(1)).save(chain);
    }

    @Test
    public void loadCatalog_ShouldUpdateCatalog_IfExist() throws Exception {
        final StoreChain chain = getTestStoreChain();
        final CatalogProduct catalog = chain.addCatalogProduct("milk", "1234", "4.99", "Milk", "Food,dairy,milk", "dairy");
        when(catalogRepositoryMock.findByChainAndCatalogCode(chain, catalog.getCatalogCode())).thenReturn(catalog);

        final ProductData input = ProductData.builder().name("milk").number("1234").productCategory("dairy").build();
        input.setNaturalName("Homo Milk");
        input.setPrice("5.99");

        serviceToTest.loadCatalog(chain, new ProductData[]{input});

        assertEquals("Homo Milk", catalog.getNaturalName());
        assertEquals("5.99", catalog.getPrice());
        verify(catalogRepositoryMock, times(1)).findByChainAndCatalogCode(chain, catalog.getCatalogCode());
        verify(catalogRepositoryMock, times(1)).save(eq(catalog));
        verify(storeChainRepositoryMock, times(1)).save(chain);
    }

    private StoreChain getTestStoreChain() {
        final StoreChain chain = StoreChain.createStoreChain(TEST_CHAIN_CODE, TEST_CHAIN_NAME);
        chain.setId(TEST_CHAIN_ID);
        return chain;
    }
}
