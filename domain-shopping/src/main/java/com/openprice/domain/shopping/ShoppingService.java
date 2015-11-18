package com.openprice.domain.shopping;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;

@Service
public class ShoppingService {
    private final ShoppingStoreRepository shoppingStoreRepository;
    private final StoreChainRepository storeChainRepository;

    @Inject
    public ShoppingService(final ShoppingStoreRepository shoppingStoreRepository,
                           final StoreChainRepository storeChainRepository) {
        this.shoppingStoreRepository = shoppingStoreRepository;
        this.storeChainRepository = storeChainRepository;
    }

    /**
     * Return shopping list for a specific store for user. Return ShoppingStore if exists;
     * create new one if not exists.
     *
     * @param user
     * @param chainCode StoreChain code, throw exception if invalid code.
     * @return new store if not exist, existing store if already in database.
     */
    public ShoppingStore getShoppingStoreForStoreChain(final UserAccount user, final String chainCode) {
        final StoreChain chain = storeChainRepository.findByCode(chainCode);
        if (chain == null) {
            throw new IllegalArgumentException("Invalid store chain code "+chainCode);
        }

        ShoppingStore store = shoppingStoreRepository.findByUserAndChainCode(user, chainCode);
        if (store != null) {
            return store;
        }
        store = ShoppingStore.createShoppingStore(user, chain);
        return shoppingStoreRepository.save(store);
    }

}
