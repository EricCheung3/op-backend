package com.openprice.store;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.openprice.store.data.StoreChainData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StoreChain {

    private final StoreChainData storeChainData;

    private final List<StoreBranch> branches;

    private final Map<String, CatalogProduct> productMap;

    public String getCode() {
        return storeChainData.getCode();
    }

    public String getName() {
        return storeChainData.getName();
    }

    public List<StoreBranch> getBranches() {
        return branches;
    }

    public Collection<CatalogProduct> getProducts() {
        return productMap.values();
    }

    public CatalogProduct getCatalogProductByCode(final String catalogCode) {
        return productMap.get(catalogCode);
    }
}
