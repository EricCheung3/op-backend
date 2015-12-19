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

import com.openprice.domain.product.ProductCategory;
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
    CatalogProductRepository catalogProductRepositoryMock;

    @Mock
    MultipartFile fileMock;

    @InjectMocks
    StoreService serviceToTest;

    @Test
    public void batchUpdateCatalog_ShouldAddNewCatalog_IfNotExist() throws Exception {
        final StoreChain chain = getTestStoreChain();
        final ProductData input = ProductData.builder()
                                             .name("milk")
                                             .number("1234")
                                             .productCategory("dairy")
                                             .build();

        when(catalogProductRepositoryMock.findByChainAndCatalogCode(chain, input.getCatalogCode())).thenReturn(null);

        serviceToTest.batchUpdateCatalog(chain, new ProductData[]{input});

        verify(catalogProductRepositoryMock, times(1)).findByChainAndCatalogCode(chain, input.getCatalogCode());
        verify(catalogProductRepositoryMock, times(1)).save(any(CatalogProduct.class));
    }

    @Test
    public void batchUpdateCatalog_ShouldUpdateCatalog_IfExist() throws Exception {
        final StoreChain chain = getTestStoreChain();
        final CatalogProduct catalog = CatalogProduct.testObjectBuilder()
                                                     .id("cata001")
                                                     .chain(chain)
                                                     .name("milk")
                                                     .number("1234")
                                                     .naturalName("2% milk")
                                                     .labelCodes("food,dairy")
                                                     .price("5.29")
                                                     .productCategory(ProductCategory.dairy)
                                                     .build();
        when(catalogProductRepositoryMock.findByChainAndCatalogCode(chain, catalog.getCatalogCode())).thenReturn(catalog);

        final ProductData input = ProductData.builder()
                                             .name("milk")
                                             .number("1234")
                                             .naturalName("Homo Milk")
                                             .price("5.99")
                                             .labelCodes("food,dairy,milk")
                                             .productCategory("dairy")
                                             .build();

        serviceToTest.batchUpdateCatalog(chain, new ProductData[]{input});

        assertEquals("Homo Milk", catalog.getNaturalName());
        assertEquals("5.99", catalog.getPrice());
        assertEquals("food,dairy,milk", catalog.getLabelCodes());
        verify(catalogProductRepositoryMock, times(1)).findByChainAndCatalogCode(chain, catalog.getCatalogCode());
        verify(catalogProductRepositoryMock, times(1)).save(eq(catalog));
    }

    private StoreChain getTestStoreChain() {
        final StoreChain chain = StoreChain.createTestStoreChain(TEST_CHAIN_ID, TEST_CHAIN_CODE, TEST_CHAIN_NAME);
        return chain;
    }
}
