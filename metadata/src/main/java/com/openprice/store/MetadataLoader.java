package com.openprice.store;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.openprice.store.data.CategoryData;
import com.openprice.store.data.ProductData;
import com.openprice.store.data.StoreBranchData;
import com.openprice.store.data.StoreChainData;

public class MetadataLoader {

    //generic, store-independent data.
    private static final String STORE_DATA_JSON = "/store-data.json";
    private static final String CATEGORY_DATA_JSON = "/category-data.json";

    //catalog: store dependent data.
    private static final String CATALOG_DATA_JSON = "/catalog-data.json";

    //branches: store dependent data
    private static final String BRANCH_DATA_JSON = "/branch-data.json";

    //identification field that can be used to find stores: store dependent data
    private static final String IDENTIFY_FIELD_DATA_JSON = "/identify.json";

    public static StoreMetadata loadMetadata() {
        // load category first
        Map<String, ProductCategory> categoryMap = loadProductCategory();
        Map<String, StoreChain> chainMap = loadStoreChains(categoryMap);
        return new StoreMetadata(chainMap, categoryMap);
    }

    static Map<String, ProductCategory> loadProductCategory() {
        final ImmutableMap.Builder<String, ProductCategory> categoryMapBuilder = new ImmutableMap.Builder<>();
        final CategoryData[] categories = loadFromJsonResource(CATEGORY_DATA_JSON, CategoryData[].class);
        if (categories == null) {
            throw new RuntimeException("No product category data at " + CATEGORY_DATA_JSON);
        }

        final Set<String> codeSet = new HashSet<>();
        for (final CategoryData category : categories) {
            // fix code and name, check duplicate code
            category.setCode(category.getCode().toLowerCase().trim());
            if (codeSet.contains(category.getCode())) {
                throw new RuntimeException("Duplicate category code found at " + CATEGORY_DATA_JSON + " for " + category.getCode());
            }
            codeSet.add(category.getCode());
            categoryMapBuilder.put(category.getCode(), new ProductCategory(category));
        }

        return categoryMapBuilder.build();
    }

    static Map<String, StoreChain> loadStoreChains(final Map<String, ProductCategory> categoryMap){
        final ImmutableMap.Builder<String, StoreChain> chainMapBuilder = new ImmutableMap.Builder<>();
        final StoreChainData[] storeChains = loadFromJsonResource(STORE_DATA_JSON, StoreChainData[].class);
        validateStoreChainData(storeChains);
        if (storeChains == null) {
            throw new RuntimeException("No store chain data at "+STORE_DATA_JSON);
        }
        for (StoreChainData chain : storeChains) {
            final List<StoreBranch> branches = loadStoreBranches(chain.getCode());
            final Map<String, CatalogProduct> products = loadCatalogProducts(chain.getCode(), categoryMap);
            final List<String> identifyFields = loadStoreIdentify(chain.getCode());
            chainMapBuilder.put(chain.getCode(),
                                new StoreChain(
                                        chain,
                                        branches,
                                        identifyFields,
                                        products));
        }
        return chainMapBuilder.build();
    }

    /**
     * validate store chains are well-formatted and nonRepeating, etc. see
     * https://github.com/opgt/op-backend/wiki/Store-Meta-data:-store-date.json
     */
    static boolean validateStoreChainDataList(final List<StoreChainData> data){
        final Set<String> codeSet=new HashSet<String>();
        for(StoreChainData chain:data){
            if(chain.getCode().contains("\\s+"))
                throw new RuntimeException("chain "+chain+"'s code should not contain spaces:"+chain.getCode());
            if(!chain.getCode().toLowerCase().equals(chain.getCode()))
                throw new RuntimeException("chain "+chain+"'s code is not lowercased:"+chain.getCode());
            if(Character.isDigit(chain.getCode().charAt(0)))
                throw new RuntimeException("chain "+chain+"'s code should not starts with a digit:"+chain.getCode());
            if(codeSet.contains(chain.getCode()))
                throw new RuntimeException("repeating code. either a repeating chain or this chain's code has been used already:"+chain.getCode());
            codeSet.add(chain.getCode());
        }
        return true;
    }
    public static boolean validateStoreChainData(final StoreChainData[] data) {
        return validateStoreChainDataList(Arrays.asList(data));
    }

    static List<StoreBranch> loadStoreBranches(final String chainCode) {
        final ImmutableList.Builder<StoreBranch> branchListBuilder = new ImmutableList.Builder<>();
        final String branchFileName = "/" + chainCode + BRANCH_DATA_JSON;
        final StoreBranchData[] storeBranches = loadFromJsonResource(branchFileName, StoreBranchData[].class);
        if (storeBranches != null) {
            for(final StoreBranchData branch : storeBranches) {
                //TODO any validation???
                branchListBuilder.add(new StoreBranch(branch));
            }
        }
        return branchListBuilder.build();
    }

    //TODO test
    static List<String> loadStoreIdentify(final String chainCode) {
        final ImmutableList.Builder<String> branchListBuilder = new ImmutableList.Builder<>();
        final String branchFileName = "/" + chainCode + IDENTIFY_FIELD_DATA_JSON;
        final String[] identifys = loadFromJsonResource(branchFileName, String[].class);
        if (identifys != null) {
            for(final String id : identifys) {
                branchListBuilder.add(id);
            }
        }
        return branchListBuilder.build();
    }

    static Map<String, CatalogProduct> loadCatalogProducts(final String chainCode, final Map<String, ProductCategory> categoryMap) {
        final ImmutableMap.Builder<String, CatalogProduct> productListBuilder = new ImmutableMap.Builder<>();
        final String catalogFileName = "/" + chainCode + CATALOG_DATA_JSON;
        final ProductData[] products = loadFromJsonResource(catalogFileName, ProductData[].class);

        if (products != null) {
            final Set<String> codeSet = new HashSet<>();
            for(ProductData product : products) {
                if (StringUtils.isEmpty(product.getName())) {
                    throw new RuntimeException("Empty name at " + catalogFileName + " for " + product);
                }
                final String code = ProductUtils.generateCatalogCode(product.getName(), product.getNumber());
                if (codeSet.contains(code)) {
                    throw new RuntimeException("Duplicate product catalog code found at " + catalogFileName + " for " + product);
                }
                ProductCategory category = categoryMap.get(product.getProductCategory());
                if (category == null) {
                    throw new RuntimeException("Invalid category code found at " + catalogFileName + " for " + product);
                }
                productListBuilder.put(code, new CatalogProduct(product, category));
            }
        }
        return productListBuilder.build();
    }

    static <T> T[] loadFromJsonResource(String resourceFileName, Class<T[]> clazz) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try (final InputStream is = MetadataLoader.class.getResourceAsStream(resourceFileName)){
            if (is == null) {
                return null;
            }
            return mapper.readValue(is, clazz);
        } catch (Exception ex) {
            throw new RuntimeException("Cannot load json file " + resourceFileName
                    + ", parsing json error: " + ex.getMessage());
        }

    }
}
