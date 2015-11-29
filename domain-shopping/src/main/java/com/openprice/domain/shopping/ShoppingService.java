package com.openprice.domain.shopping;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
        log.debug("Create shopping list for store '{}' for user {}.", chain.getCode(), user.getEmail());
        return shoppingStoreRepository.save(store);
    }

}
