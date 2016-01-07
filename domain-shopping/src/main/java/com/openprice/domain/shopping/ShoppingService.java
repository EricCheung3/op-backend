package com.openprice.domain.shopping;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.product.ProductCategory;
import com.openprice.domain.store.CatalogProduct;
import com.openprice.domain.store.CatalogProductRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShoppingService {
    private final ShoppingStoreRepository shoppingStoreRepository;
    private final ShoppingItemRepository shoppingItemRepository;
    private final StoreChainRepository storeChainRepository;
    private final CatalogProductRepository catalogProductRepository;

    @Inject
    public ShoppingService(final ShoppingStoreRepository shoppingStoreRepository,
                           final ShoppingItemRepository shoppingItemRepository,
                           final StoreChainRepository storeChainRepository,
                           final CatalogProductRepository catalogProductRepository) {
        this.shoppingStoreRepository = shoppingStoreRepository;
        this.shoppingItemRepository = shoppingItemRepository;
        this.storeChainRepository = storeChainRepository;
        this.catalogProductRepository = catalogProductRepository;
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

    public ShoppingItem addShoppingItemToStore(final ShoppingStore store,
                                               final String catalogCode,
                                               final String name) {
        final ShoppingItem item = new ShoppingItem();
        item.setStore(store);
        item.setCatalogCode(catalogCode);
        item.setName(name);

        if (StringUtils.isEmpty(catalogCode)) {
            // TODO: add algorithm to find best matching ProductCategory by name
            item.setProductCategory(ProductCategory.uncategorized);
        } else {
            final StoreChain chain = storeChainRepository.findByCode(store.getChainCode());
            final CatalogProduct catalogProduct = catalogProductRepository.findByChainAndCatalogCode(chain, catalogCode);
            if (catalogProduct != null) {
                item.setProductCategory(catalogProduct.getProductCategory());
            } else {
                log.error("SEVERE! Cannot get CatalogProduct by catalog code '{}' in store '{}', something wrong with the database!"
                        , catalogCode, store.getChainCode());
                item.setProductCategory(ProductCategory.uncategorized);
            }
        }
        shoppingItemRepository.save(item);
        return item;
    }

}
