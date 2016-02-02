package com.openprice.domain.shopping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserRoleType;
import com.openprice.domain.product.ProductCategory;
import com.openprice.domain.store.CatalogProduct;
import com.openprice.domain.store.CatalogProductRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.parser.category.CategoryPredictorInterface;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingServiceTest {

    static final String USER_ID = "user123";
    static final String USER_EMAIL = "john.doe@email.com";
    private static final String TEST_CHAIN_ID = "CHAIN_TEST_ID";
    private static final String TEST_CHAIN_CODE = "test";
    private static final String TEST_CHAIN_NAME = "My TestStore";
    private static final String TEST_CATALOG_CODE = "MILK";
    private static final String TEST_ITEM_ID = "ITEM_TEST_ID";
    private static final String TEST_ITEM_NAME = "2% Milk";

    @Mock
    ShoppingStoreRepository shoppingStoreRepositoryMock;

    @Mock
    ShoppingItemRepository shoppingItemRepositoryMock;

    @Mock
    CatalogProductRepository catalogProductRepositoryMock;

    @Mock
    StoreChainRepository storeChainRepositoryMock;

    @Mock
    CategoryPredictorInterface categoryPredictorMock;

    @InjectMocks
    ShoppingService serviceToTest;

    @Test
    public void getShoppingStoreForStoreChain_ShouldReturnExistingShoppingStore() throws Exception {
        final UserAccount testUser = getTestUserAccount();
        final StoreChain rcssChain = getTestStoreChain();
        final ShoppingStore store = new ShoppingStore();
        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(rcssChain);
        when(shoppingStoreRepositoryMock.findByUserAndChainCode(eq(testUser), eq(TEST_CHAIN_CODE))).thenReturn(store);

        ShoppingStore result = serviceToTest.getShoppingStoreForStoreChain(testUser, TEST_CHAIN_CODE);
        assertEquals(store, result);
    }

    @Test
    public void getShoppingStoreForStoreChain_ShouldReturnNewShoppingStore_IfNotExist() throws Exception {
        final UserAccount testUser = getTestUserAccount();
        final StoreChain testChain = getTestStoreChain();

        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(testChain);
        when(shoppingStoreRepositoryMock.findByUserAndChainCode(eq(testUser), eq(TEST_CHAIN_CODE))).thenReturn(null);
        when(shoppingStoreRepositoryMock.save(isA(ShoppingStore.class))).thenAnswer( new Answer<ShoppingStore>() {
            @Override
            public ShoppingStore answer(InvocationOnMock invocation) throws Throwable {
                final ShoppingStore store = (ShoppingStore)invocation.getArguments()[0];
                store.setId(TEST_CHAIN_ID);
                return store;
            }
        });

        ShoppingStore result = serviceToTest.getShoppingStoreForStoreChain(testUser, TEST_CHAIN_CODE);
        assertEquals(TEST_CHAIN_ID, result.getId());
        assertEquals(TEST_CHAIN_CODE, result.getChainCode());
    }

    @Test(expected=IllegalArgumentException.class)
    public void getShoppingStoreForStoreChain_ShouldThrowException_IfInvalidChainCode() throws Exception {
        final UserAccount testUser = getTestUserAccount();

        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(null);

        serviceToTest.getShoppingStoreForStoreChain(testUser, TEST_CHAIN_CODE);
    }

    @Test
    public void addShoppingItemToStore_ShouldAddItemWithCategory() throws Exception {
        final UserAccount testUser = getTestUserAccount();
        final StoreChain rcssChain = getTestStoreChain();
        final ShoppingStore store = ShoppingStore.createShoppingStore(testUser, rcssChain);
        final CatalogProduct catalogProduct = CatalogProduct.testObjectBuilder()
                                                            .chain(rcssChain)
                                                            .name("MILK")
                                                            .naturalName(TEST_ITEM_NAME)
                                                            .productCategory(ProductCategory.dairy)
                                                            .build();

        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(rcssChain);
        when(catalogProductRepositoryMock.findByChainAndCatalogCode(rcssChain, TEST_CATALOG_CODE)).thenReturn(catalogProduct);
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
        assertEquals(ProductCategory.dairy, result.getProductCategory());
        assertEquals(TEST_ITEM_NAME, result.getName());
        assertEquals(1, result.getNumber());
    }

    @Test
    public void addShoppingItemToStore_ShouldIncreaseNumberForExistingItem() throws Exception {
        final UserAccount testUser = getTestUserAccount();
        final StoreChain rcssChain = getTestStoreChain();
        final ShoppingStore store = ShoppingStore.createShoppingStore(testUser, rcssChain);
        final CatalogProduct catalogProduct = CatalogProduct.testObjectBuilder()
                                                            .chain(rcssChain)
                                                            .name("MILK")
                                                            .naturalName(TEST_ITEM_NAME)
                                                            .productCategory(ProductCategory.dairy)
                                                            .build();
        final ShoppingItem existingItem = ShoppingItem.testObjectBuilder()
                                                      .catalogCode(TEST_CATALOG_CODE)
                                                      .name(TEST_ITEM_NAME)
                                                      .number(1)
                                                      .productCategory(ProductCategory.dairy)
                                                      .build();

        when(shoppingItemRepositoryMock.findByStoreAndCatalogCode(store, TEST_CATALOG_CODE)).thenReturn(existingItem);
        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(rcssChain);
        when(catalogProductRepositoryMock.findByChainAndCatalogCode(rcssChain, TEST_CATALOG_CODE)).thenReturn(catalogProduct);
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
        assertEquals(ProductCategory.dairy, result.getProductCategory());
        assertEquals(TEST_ITEM_NAME, result.getName());
        assertEquals(2, result.getNumber());
    }

    @Test
    public void addShoppingItemToStore_ShouldAddItemWithUncategoried_WhenEmptyCatalogCode() throws Exception {
        final UserAccount testUser = getTestUserAccount();
        final StoreChain rcssChain = getTestStoreChain();
        final ShoppingStore store = ShoppingStore.createShoppingStore(testUser, rcssChain);

        when(shoppingItemRepositoryMock.findByStoreAndNameAndCatalogCodeIsNull(store, TEST_ITEM_NAME)).thenReturn(null);
        when(shoppingStoreRepositoryMock.findByUserAndChainCode(eq(testUser), eq(TEST_CHAIN_CODE))).thenReturn(store);
        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(rcssChain);
        when(shoppingItemRepositoryMock.save(isA(ShoppingItem.class))).thenAnswer( new Answer<ShoppingItem>() {
            @Override
            public ShoppingItem answer(InvocationOnMock invocation) throws Throwable {
                final ShoppingItem item = (ShoppingItem)invocation.getArguments()[0];
                item.setId(TEST_ITEM_ID);
                return item;
            }
        });

        ShoppingItem result = serviceToTest.addShoppingItemToStore(store, "", TEST_ITEM_NAME);
        assertEquals(ProductCategory.uncategorized, result.getProductCategory());
        assertEquals(TEST_ITEM_NAME, result.getName());
        assertEquals(1, result.getNumber());
    }

    @Test
    public void addShoppingItemToStore_ShouldAddItemWithCategory_WhenEmptyCatalogCodeAndCategoryPredictorWorks() throws Exception {
        final UserAccount testUser = getTestUserAccount();
        final StoreChain rcssChain = getTestStoreChain();
        final ShoppingStore store = ShoppingStore.createShoppingStore(testUser, rcssChain);

        when(categoryPredictorMock.mostMatchingCategory(eq(TEST_ITEM_NAME))).thenReturn(ProductCategory.dairy.getCode());
        when(shoppingItemRepositoryMock.findByStoreAndNameAndCatalogCodeIsNull(store, TEST_ITEM_NAME)).thenReturn(null);
        when(shoppingStoreRepositoryMock.findByUserAndChainCode(eq(testUser), eq(TEST_CHAIN_CODE))).thenReturn(store);
        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(rcssChain);
        when(shoppingItemRepositoryMock.save(isA(ShoppingItem.class))).thenAnswer( new Answer<ShoppingItem>() {
            @Override
            public ShoppingItem answer(InvocationOnMock invocation) throws Throwable {
                final ShoppingItem item = (ShoppingItem)invocation.getArguments()[0];
                item.setId(TEST_ITEM_ID);
                return item;
            }
        });

        ShoppingItem result = serviceToTest.addShoppingItemToStore(store, "", TEST_ITEM_NAME);
        assertEquals(ProductCategory.dairy, result.getProductCategory());
        assertEquals(TEST_ITEM_NAME, result.getName());
        assertEquals(1, result.getNumber());
    }

    @Test
    public void addShoppingItemToStore_ShouldIncreaseNumberForExistingItem_WhenEmptyCatalogCode() throws Exception {
        final UserAccount testUser = getTestUserAccount();
        final StoreChain rcssChain = getTestStoreChain();
        final ShoppingStore store = ShoppingStore.createShoppingStore(testUser, rcssChain);
        final CatalogProduct catalogProduct = CatalogProduct.testObjectBuilder()
                                                            .chain(rcssChain)
                                                            .name("MILK")
                                                            .naturalName(TEST_ITEM_NAME)
                                                            .productCategory(ProductCategory.dairy)
                                                            .build();
        final ShoppingItem existingItem = ShoppingItem.testObjectBuilder()
                                                      .catalogCode(null)
                                                      .name(TEST_ITEM_NAME)
                                                      .number(1)
                                                      .productCategory(ProductCategory.dairy)
                                                      .build();

        when(shoppingItemRepositoryMock.findByStoreAndNameAndCatalogCodeIsNull(store, TEST_ITEM_NAME)).thenReturn(existingItem);
        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(rcssChain);
        when(catalogProductRepositoryMock.findByChainAndCatalogCode(rcssChain, TEST_CATALOG_CODE)).thenReturn(catalogProduct);
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
        assertEquals(ProductCategory.dairy, result.getProductCategory());
        assertEquals(TEST_ITEM_NAME, result.getName());
        assertEquals(2, result.getNumber());
    }

    private UserAccount getTestUserAccount() {
        return UserAccount.testObjectBuilder()
                          .id(USER_ID)
                          .email(USER_EMAIL)
                          .role(UserRoleType.ROLE_USER)
                          .build();
    }

    private StoreChain getTestStoreChain() {
        return StoreChain.createTestStoreChain(TEST_CHAIN_ID, TEST_CHAIN_CODE, TEST_CHAIN_NAME);
    }

}
