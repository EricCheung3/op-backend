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
import com.openprice.predictor.CategoryPredictor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShoppingService {
    private final ShoppingStoreRepository shoppingStoreRepository;
    private final ShoppingItemRepository shoppingItemRepository;
    private final StoreChainRepository storeChainRepository;
    private final CatalogProductRepository catalogProductRepository;
    private final CategoryPredictor categoryPredictor;

    @Inject
    public ShoppingService(final ShoppingStoreRepository shoppingStoreRepository,
                           final ShoppingItemRepository shoppingItemRepository,
                           final StoreChainRepository storeChainRepository,
                           final CatalogProductRepository catalogProductRepository,
                           final CategoryPredictor categoryPredictor) {
        this.shoppingStoreRepository = shoppingStoreRepository;
        this.shoppingItemRepository = shoppingItemRepository;
        this.storeChainRepository = storeChainRepository;
        this.catalogProductRepository = catalogProductRepository;
        this.categoryPredictor = categoryPredictor;
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

    /**
     * Adds user input shopping item to store shopping list, the number default to 1.
     * If the item is already in the list (same catalog product, or same name without catalog), it will increase
     * item quantity number.
     *
     * @param store
     * @param catalogCode
     * @param name
     * @return
     */
    public ShoppingItem addShoppingItemToStore(final ShoppingStore store,
                                               final String catalogCode,
                                               final String name) {
        assert store != null;
        assert !StringUtils.isEmpty(name);

        ShoppingItem item;

        if (StringUtils.isEmpty(catalogCode)) {
            item = shoppingItemRepository.findByStoreAndNameAndCatalogCodeIsNull(store, name);
            if (item != null) {
                item.setNumber(item.getNumber() + 1);
                log.debug("Add item without catalogCode, and find existing item, "
                        + "so increase number for shopping item '{}' to {}", name, item.getNumber());
            } else {
                item = new ShoppingItem();
                item.setStore(store);
                item.setCatalogCode(null);
                item.setName(name);
                item.setNumber(1);
                item.setProductCategory(predictCategoryByName(name));
                log.debug("Add item without catalogCode, and create new item for '{}'", name);
            }
        } else {
            item = shoppingItemRepository.findByStoreAndCatalogCode(store, catalogCode);
            if (item != null) {
                item.setNumber(item.getNumber() + 1);
                // we only increase number, it will keep previous name/category in case user might have changed it.
                log.debug("Add item with catalogCode '{}', and find existing item, "
                        + "so increase number for shopping item '{}' to {}", catalogCode, name, item.getNumber());
            } else {
                item = new ShoppingItem();
                item.setStore(store);
                item.setCatalogCode(catalogCode);
                item.setName(name);
                item.setNumber(1);
                log.debug("Add item with catalogCode '{}', and create new item for '{}'", catalogCode, name);
                final StoreChain chain = storeChainRepository.findByCode(store.getChainCode());
                final CatalogProduct catalogProduct = catalogProductRepository.findByChainAndCatalogCode(chain, catalogCode); // TODO load catalog product into cache
                if (catalogProduct != null) {
                    item.setProductCategory(catalogProduct.getProductCategory());
                } else {
                    log.error("SEVERE! Cannot get CatalogProduct by catalog code '{}' in store '{}', something wrong with the database!"
                            , catalogCode, store.getChainCode());
                    item.setProductCategory(ProductCategory.uncategorized);
                }
            }

        }
        item = shoppingItemRepository.save(item);
        return item;
    }

    /**
     * We only allow user to update name, number or category.
     *
     * @param item
     * @param name
     * @param number
     * @param productCategory
     * @return
     */
    public ShoppingItem updateShoppingItem(final ShoppingItem item,
                                           final String name,
                                           final int number,
                                           final ProductCategory productCategory) {
        item.setName(name);
        item.setNumber(number);
        item.setProductCategory(productCategory);

        return shoppingItemRepository.save(item);
    }

    private ProductCategory predictCategoryByName(final String name) {
        final String predictedCategoryCode = categoryPredictor.mostMatchingCategory(name);
        log.debug("CategoryPredictor returns category code {} for name '{}'", predictedCategoryCode, name);
        final ProductCategory category = ProductCategory.findByCode(predictedCategoryCode);
        return (category == null) ? ProductCategory.uncategorized : category;
    }
}
