package com.openprice.domain.shopping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserRoleType;
import com.openprice.predictor.CategoryPredictor;
import com.openprice.store.CatalogProduct;
import com.openprice.store.ProductCategory;
import com.openprice.store.StoreChain;
import com.openprice.store.StoreMetadata;
import com.openprice.store.data.CategoryData;
import com.openprice.store.data.ProductData;
import com.openprice.store.data.StoreChainData;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingServiceTest {

    private static final String USER_ID = "user123";
    private static final String USER_EMAIL = "john.doe@email.com";
    private static final String TEST_CHAIN_CODE = "CHAIN_CODE";
    private static final String TEST_CHAIN_NAME = "My TestStore";
    private static final String TEST_CATALOG_CODE = "MILK";
    private static final String TEST_STORE_ID = "SHOPPING_STORE_TEST_ID";
    private static final String TEST_ITEM_ID = "ITEM_TEST_ID";
    private static final String TEST_ITEM_NAME = "2% Milk";

    @Mock
    ShoppingStoreRepository shoppingStoreRepositoryMock;

    @Mock
    ShoppingItemRepository shoppingItemRepositoryMock;

    @Mock
    CategoryPredictor categoryPredictorMock;

    @Mock
    StoreMetadata storeMetadataMock;

    @InjectMocks
    ShoppingService serviceToTest;

    UserAccount testUser;
    ProductCategory uncategorized;
    ProductCategory dairy;
    StoreChain testStoreChain;


    @Test
    public void getShoppingStoreForStoreChain_ShouldReturnExistingShoppingStore() throws Exception {
        final ShoppingStore store = new ShoppingStore();
        when(shoppingStoreRepositoryMock.findByUserAndChainCode(eq(testUser), eq(TEST_CHAIN_CODE))).thenReturn(store);
        when(storeMetadataMock.getStoreChainByCode(eq(TEST_CHAIN_CODE))).thenReturn(testStoreChain);

        ShoppingStore result = serviceToTest.getShoppingStoreForStoreChain(testUser, TEST_CHAIN_CODE);

        assertEquals(store, result);
        verify(shoppingStoreRepositoryMock, times(1)).findByUserAndChainCode(eq(testUser), eq(TEST_CHAIN_CODE));
        verify(shoppingStoreRepositoryMock, times(0)).save(isA(ShoppingStore.class));
    }

    @Test
    public void getShoppingStoreForStoreChain_ShouldReturnNewShoppingStore_IfNotExist() throws Exception {
        when(storeMetadataMock.getStoreChainByCode(eq(TEST_CHAIN_CODE))).thenReturn(testStoreChain);
        when(shoppingStoreRepositoryMock.findByUserAndChainCode(eq(testUser), eq(TEST_CHAIN_CODE))).thenReturn(null);
        when(shoppingStoreRepositoryMock.save(isA(ShoppingStore.class))).thenAnswer( new Answer<ShoppingStore>() {
            @Override
            public ShoppingStore answer(InvocationOnMock invocation) throws Throwable {
                final ShoppingStore store = (ShoppingStore)invocation.getArguments()[0];
                store.setId(TEST_STORE_ID);
                return store;
            }
        });

        ShoppingStore result = serviceToTest.getShoppingStoreForStoreChain(testUser, TEST_CHAIN_CODE);
        assertEquals(TEST_STORE_ID, result.getId());
        assertEquals(TEST_CHAIN_CODE, result.getChainCode());
        verify(shoppingStoreRepositoryMock, times(1)).findByUserAndChainCode(eq(testUser), eq(TEST_CHAIN_CODE));
        verify(shoppingStoreRepositoryMock, times(1)).save(isA(ShoppingStore.class));
    }

    @Test(expected=IllegalArgumentException.class)
    public void getShoppingStoreForStoreChain_ShouldThrowException_IfInvalidChainCode() throws Exception {
        serviceToTest.getShoppingStoreForStoreChain(testUser, "invalid code");
    }

    @Test
    public void addShoppingItemToStore_ShouldAddNewItemWithCategory() throws Exception {
        final ShoppingStore store = ShoppingStore.createShoppingStore(testUser, testStoreChain);

        when(storeMetadataMock.getStoreChainByCode(eq(TEST_CHAIN_CODE))).thenReturn(testStoreChain);
        when(shoppingItemRepositoryMock.findByStoreAndCatalogCode(eq(store), eq(TEST_CATALOG_CODE))).thenReturn(null);
        when(shoppingItemRepositoryMock.save(isA(ShoppingItem.class))).thenAnswer( new Answer<ShoppingItem>() {
            @Override
            public ShoppingItem answer(InvocationOnMock invocation) throws Throwable {
                final ShoppingItem item = (ShoppingItem)invocation.getArguments()[0];
                item.setId(TEST_ITEM_ID);
                return item;
            }
        });

        ShoppingItem result = serviceToTest.addShoppingItemToStore(store, TEST_CATALOG_CODE, TEST_ITEM_NAME);

        assertEquals(TEST_CATALOG_CODE, result.getCatalogCode());
        assertEquals(TEST_ITEM_NAME, result.getName());
        assertEquals(1, result.getNumber());
        assertEquals(TEST_CATALOG_CODE, result.getCatalogCode());
        verify(shoppingItemRepositoryMock, times(1)).findByStoreAndCatalogCode(eq(store), eq(TEST_CATALOG_CODE));
        verify(shoppingItemRepositoryMock, times(1)).save(isA(ShoppingItem.class));
        verify(storeMetadataMock, times(1)).getStoreChainByCode(eq(TEST_CHAIN_CODE));
        verify(categoryPredictorMock, times(0)).mostMatchingCategory(eq(TEST_ITEM_NAME));
    }

    @Test
    public void addShoppingItemToStore_ShouldIncreaseNumberForExistingItem() throws Exception {
        final ShoppingStore store = ShoppingStore.createShoppingStore(testUser, testStoreChain);
        final ShoppingItem existingItem = ShoppingItem.testObjectBuilder()
                                                      .catalogCode(TEST_CATALOG_CODE)
                                                      .name(TEST_ITEM_NAME)
                                                      .number(1)
                                                      .catalogCode(TEST_CATALOG_CODE)
                                                      .build();

        when(shoppingItemRepositoryMock.findByStoreAndCatalogCode(eq(store), eq(TEST_CATALOG_CODE))).thenReturn(existingItem);
        when(shoppingItemRepositoryMock.save(isA(ShoppingItem.class))).thenAnswer( new Answer<ShoppingItem>() {
            @Override
            public ShoppingItem answer(InvocationOnMock invocation) throws Throwable {
                final ShoppingItem item = (ShoppingItem)invocation.getArguments()[0];
                item.setId(TEST_ITEM_ID);
                return item;
            }
        });

        ShoppingItem result = serviceToTest.addShoppingItemToStore(store, TEST_CATALOG_CODE, TEST_ITEM_NAME);

        assertEquals(TEST_CATALOG_CODE, result.getCatalogCode());
        assertEquals(TEST_ITEM_NAME, result.getName());
        assertEquals(2, result.getNumber());
        assertEquals(TEST_CATALOG_CODE, result.getCatalogCode());
        verify(shoppingItemRepositoryMock, times(1)).findByStoreAndCatalogCode(eq(store), eq(TEST_CATALOG_CODE));
        verify(shoppingItemRepositoryMock, times(1)).save(isA(ShoppingItem.class));
        verify(storeMetadataMock, times(0)).getStoreChainByCode(any());
        verify(categoryPredictorMock, times(0)).mostMatchingCategory(any());
    }

    @Test
    public void addShoppingItemToStore_ShouldAddItemWithUncategoried_WhenEmptyCatalogCode() throws Exception {
        final ShoppingStore store = ShoppingStore.createShoppingStore(testUser, testStoreChain);

        when(categoryPredictorMock.mostMatchingCategory(eq(TEST_ITEM_NAME))).thenReturn("uncategorized");
        when(storeMetadataMock.getProductCategoryByCode(eq("uncategorized"))).thenReturn(uncategorized);
        when(shoppingItemRepositoryMock.findByStoreAndNameAndCatalogCodeIsNull(eq(store), eq(TEST_ITEM_NAME))).thenReturn(null);
        when(shoppingItemRepositoryMock.save(isA(ShoppingItem.class))).thenAnswer( new Answer<ShoppingItem>() {
            @Override
            public ShoppingItem answer(InvocationOnMock invocation) throws Throwable {
                final ShoppingItem item = (ShoppingItem)invocation.getArguments()[0];
                item.setId(TEST_ITEM_ID);
                return item;
            }
        });

        ShoppingItem result = serviceToTest.addShoppingItemToStore(store, "", TEST_ITEM_NAME);

        assertNull(result.getCatalogCode());
        assertEquals(TEST_ITEM_NAME, result.getName());
        assertEquals(1, result.getNumber());
        assertEquals("uncategorized", result.getCategoryCode());
        verify(storeMetadataMock, times(0)).getStoreChainByCode(any());
        verify(storeMetadataMock, times(1)).getProductCategoryByCode(eq("uncategorized"));
        verify(categoryPredictorMock, times(1)).mostMatchingCategory(eq(TEST_ITEM_NAME));

    }

    @Test
    public void addShoppingItemToStore_ShouldAddItemWithCategory_WhenEmptyCatalogCodeAndCategoryPredictorWorks() throws Exception {
        final ShoppingStore store = ShoppingStore.createShoppingStore(testUser, testStoreChain);

        when(categoryPredictorMock.mostMatchingCategory(eq(TEST_ITEM_NAME))).thenReturn("dairy");
        when(storeMetadataMock.getProductCategoryByCode(eq("dairy"))).thenReturn(dairy);
        when(shoppingItemRepositoryMock.findByStoreAndNameAndCatalogCodeIsNull(store, TEST_ITEM_NAME)).thenReturn(null);
        when(shoppingItemRepositoryMock.save(isA(ShoppingItem.class))).thenAnswer( new Answer<ShoppingItem>() {
            @Override
            public ShoppingItem answer(InvocationOnMock invocation) throws Throwable {
                final ShoppingItem item = (ShoppingItem)invocation.getArguments()[0];
                item.setId(TEST_ITEM_ID);
                return item;
            }
        });

        ShoppingItem result = serviceToTest.addShoppingItemToStore(store, "", TEST_ITEM_NAME);

        assertNull(result.getCatalogCode());
        assertEquals(TEST_ITEM_NAME, result.getName());
        assertEquals(1, result.getNumber());
        assertEquals("dairy", result.getCategoryCode());
        verify(storeMetadataMock, times(0)).getStoreChainByCode(any());
        verify(storeMetadataMock, times(1)).getProductCategoryByCode(eq("dairy"));
        verify(categoryPredictorMock, times(1)).mostMatchingCategory(eq(TEST_ITEM_NAME));
    }

    @Test
    public void addShoppingItemToStore_ShouldIncreaseNumberForExistingItem_WhenEmptyCatalogCode() throws Exception {
        final ShoppingStore store = ShoppingStore.createShoppingStore(testUser, testStoreChain);
        final ShoppingItem existingItem = ShoppingItem.testObjectBuilder()
                                                      .catalogCode(null)
                                                      .name(TEST_ITEM_NAME)
                                                      .number(1)
                                                      .categoryCode("dairy")
                                                      .build();

        when(shoppingItemRepositoryMock.findByStoreAndNameAndCatalogCodeIsNull(store, TEST_ITEM_NAME)).thenReturn(existingItem);
        when(shoppingItemRepositoryMock.save(isA(ShoppingItem.class))).thenAnswer( new Answer<ShoppingItem>() {
            @Override
            public ShoppingItem answer(InvocationOnMock invocation) throws Throwable {
                final ShoppingItem item = (ShoppingItem)invocation.getArguments()[0];
                item.setId(TEST_ITEM_ID);
                return item;
            }
        });

        ShoppingItem result = serviceToTest.addShoppingItemToStore(store, "", TEST_ITEM_NAME);

        assertNull(result.getCatalogCode());
        assertEquals(TEST_ITEM_NAME, result.getName());
        assertEquals(2, result.getNumber());
        assertEquals("dairy", result.getCategoryCode());
        verify(storeMetadataMock, times(0)).getStoreChainByCode(any());
        verify(storeMetadataMock, times(0)).getProductCategoryByCode(any());
        verify(categoryPredictorMock, times(0)).mostMatchingCategory(any());
    }

    @Before
    public void setup() {
        testUser =
                UserAccount.testObjectBuilder()
                           .id(USER_ID)
                           .email(USER_EMAIL)
                           .role(UserRoleType.ROLE_USER)
                           .build();

        dairy = new ProductCategory(CategoryData.builder().code("dairy").build());
        uncategorized = new ProductCategory(CategoryData.builder().code("uncategorized").build());

        final StoreChainData chainData =
                StoreChainData.builder()
                              .code(TEST_CHAIN_CODE)
                              .name(TEST_CHAIN_NAME)
                              .build();
        final ProductData productData =
                ProductData.builder()
                           .name(TEST_CATALOG_CODE)
                           .build();
        final Map<String, CatalogProduct> productMap = new HashMap<>();
        productMap.put(TEST_CATALOG_CODE, new CatalogProduct(productData, dairy));
        testStoreChain = new StoreChain(chainData, null, productMap);

    }

}
