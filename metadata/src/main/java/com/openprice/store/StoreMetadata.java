package com.openprice.store;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class StoreMetadata {

    @Getter
    private Map<String, StoreChain> storeChainMap;

    @Getter
    private Map<String, ProductCategory> categoryMap;

    public StoreChain getStoreChainByCode(final String chainCode) {
        return storeChainMap.get(chainCode);
    }

    public ProductCategory getProductCategoryByCode(final String code) {
        return categoryMap.get(code);
    }

}
