package com.openprice.store;

import java.util.List;

import com.openprice.store.data.StoreChainData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StoreChain {

    private final StoreChainData storeChainData;

    private final List<StoreBranch> branches;

    private final List<CatalogProduct> products;

    public String getCode() {
        return storeChainData.getCode();
    }

    public String getName() {
        return storeChainData.getName();
    }

    public List<StoreBranch> getBranches() {
        return branches;
    }

    public List<CatalogProduct> getProducts() {
        return products;
    }
}
