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

    private final List<String> receiptCategories;//store own categories on store's receipt

    private final List<StoreBranch> branches;

    private final List<String> identifyFields;

    private final Map<String, CatalogProduct> productMap;

    private final List<String> notations;

    private final Properties headerProperties;

    private final Properties nonHeaderProperties;

    private final List<String> notCatalogItemNames;

    private final List<String> skipBefore;

    private final List<String> skipAfter;

    public static StoreChain genericChainWithOnlyCode(final String code){
        return new StoreChain(
                StoreChainData.fromCodeOnly(code),
                new ArrayList<String>(),
                new ArrayList<StoreBranch>(),
                new ArrayList<String>(),
                new HashMap<String, CatalogProduct>(),
                new ArrayList<String>(),
                new Properties(),
                new Properties(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>()
                );
    }

    public static StoreChain fromChainCategoriesBranchesIdentifyMapNotationsHeaderNonHeaderNotItemNamesBeforeAfter(
            final StoreChainData chain,
            final List<String> receiptCategories,
            final List<StoreBranch> branches,
            final List<String> identifyFields,
            final Map<String, CatalogProduct> productMap,
            final List<String> notations,
            final Properties headerProperties,
            final Properties nonHeaderProperties,
            final List<String> notCatalogItemNames,
            final List<String> skipBefore,
            final List<String> skipAfter
            ){
        return new StoreChain(
                chain,
                receiptCategories,
                branches,
                identifyFields,
                productMap,
                notations,
                headerProperties,
                nonHeaderProperties,
                notCatalogItemNames,
                skipBefore,
                skipAfter
                );
    }

    public String getCode() {
        return storeChainData.getCode();
    }

    public List<String> getReceiptCategories() {
        return receiptCategories;
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

    public List<String> getNotations(){
        return notations;
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

    public List<String> getNotCatalogItemNames(){
        return notCatalogItemNames;
    }

    public List<String> getSkipBefore(){
        return skipBefore;
    }

    public List<String> getSkipAfter(){
        return skipAfter;
    }
}
