package com.openprice.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openprice.store.data.StoreChainData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StoreChain {

    private final StoreChainData storeChainData;

    private final List<StoreBranch> branches;

    private final List<String> identifyFields;

    private final Map<String, CatalogProduct> productMap;

    public static StoreChain genericChainWithOnlyCode(final String code){
        return new StoreChain(
                StoreChainData.fromCodeOnly(code),
                new ArrayList<StoreBranch>(),
                new ArrayList<String>(),
                new HashMap<String, CatalogProduct>()
                );
    }

    public String getCode() {
        return storeChainData.getCode();
    }

    public String getName() {
        return storeChainData.getName();
    }

    public List<StoreBranch> getBranches() {
        return branches;
    }

    public List<String> getIdentifyFields() {
        return identifyFields;
    }

    public Collection<CatalogProduct> getProducts() {
        return productMap.values();
    }

    public CatalogProduct getCatalogProductByCode(final String catalogCode) {
        return productMap.get(catalogCode);
    }
}
