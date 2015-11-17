package com.openprice.domain.shopping;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingServiceTest {
    @Mock
    ShoppingStoreRepository shoppingStoreRepositoryMock;

    @Mock
    StoreChainRepository storeChainRepositoryMock;

    ShoppingService serviceToTest;

    static final String TEST_CHAIN_CODE = "rcss";

    @Before
    public void setup() throws Exception {
        serviceToTest = new ShoppingService(shoppingStoreRepositoryMock, storeChainRepositoryMock);
    }

    @Test
    public void getShoppingStoreForStoreChain_ShouldReturnExistingShoppingStore() throws Exception {
        final UserAccount testUser = getTestUser();
        final StoreChain rcssChain = new StoreChain();
        final ShoppingStore store = new ShoppingStore();
        store.setId("testShopping");
        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(rcssChain);
        when(shoppingStoreRepositoryMock.findByUserAndChainCode(eq(testUser), eq(TEST_CHAIN_CODE))).thenReturn(store);

        ShoppingStore result = serviceToTest.getShoppingStoreForStoreChain(testUser, TEST_CHAIN_CODE);
        assertEquals(store, result);
    }

    @Test
    public void getShoppingStoreForStoreChain_ShouldReturnNewShoppingStore_IfNotExist() throws Exception {
        final UserAccount testUser = getTestUser();
        final StoreChain rcssChain = new StoreChain();

        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(rcssChain);
        when(shoppingStoreRepositoryMock.findByUserAndChainCode(eq(testUser), eq(TEST_CHAIN_CODE))).thenReturn(null);
        when(shoppingStoreRepositoryMock.save(isA(ShoppingStore.class))).thenAnswer( new Answer<ShoppingStore>() {
            @Override
            public ShoppingStore answer(InvocationOnMock invocation) throws Throwable {
                final ShoppingStore store = (ShoppingStore)invocation.getArguments()[0];
                store.setId("shopping101");
                return store;
            }
        });

        ShoppingStore result = serviceToTest.getShoppingStoreForStoreChain(testUser, TEST_CHAIN_CODE);
        assertEquals("shopping101", result.getId());
        assertEquals(TEST_CHAIN_CODE, result.getChainCode());
    }

    @Test(expected=IllegalArgumentException.class)
    public void getShoppingStoreForStoreChain_ShouldThrowException_IfInvalidChainCode() throws Exception {
        final UserAccount testUser = getTestUser();

        when(storeChainRepositoryMock.findByCode(eq(TEST_CHAIN_CODE))).thenReturn(null);

        serviceToTest.getShoppingStoreForStoreChain(testUser, TEST_CHAIN_CODE);
    }

    private UserAccount getTestUser() {
        final UserAccount testUser = new UserAccount();
        testUser.setId("user123");
        testUser.setEmail("user123@email.com");
        return testUser;
    }

}
