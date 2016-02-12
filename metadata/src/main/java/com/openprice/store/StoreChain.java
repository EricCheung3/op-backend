package com.openprice.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.openprice.store.data.StoreChainData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StoreChain {

    private final StoreChainData storeChainData;

    private final List<StoreBranch> branches;

    private final List<String> identifyFields;

    private final Map<String, CatalogProduct> productMap;

    private final Properties headerProperties;

    private final Properties nonHeaderProperties;

    public static StoreChain genericChainWithOnlyCode(final String code){
        return new StoreChain(
                StoreChainData.fromCodeOnly(code),
                new ArrayList<StoreBranch>(),
                new ArrayList<String>(),
                new HashMap<String, CatalogProduct>(),
                new Properties(),
                new Properties()
                );
    }

    public static StoreChain fromChainBranchesIdentifyMapHeaderNonHeader(
            final StoreChainData chain,
            final List<StoreBranch> branches,
            final List<String> identifyFields,
            final Map<String, CatalogProduct> productMap,
            Properties headerProperties,
            Properties nonHeaderProperties
            ){
        return new StoreChain(
                chain,
                branches,
                identifyFields,
                productMap,
                headerProperties,
                nonHeaderProperties);
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

    public Properties getHeaderProperties(){
        return headerProperties;
    }

    public Properties getNonHeaderProperties(){
        return nonHeaderProperties;
    }
}
