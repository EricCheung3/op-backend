package com.openprice.store;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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

    public static StoreMetadata loadMetadata() {
        // load category first
        //TODO -->hengshuai: is the order important?
        Map<String, ProductCategory> categoryMap = loadProductCategory();
        Map<String, StoreChain> chainMap = loadStoreChains(categoryMap);
        return new StoreMetadata(chainMap, categoryMap);
    }

    static Map<String, ProductCategory> loadProductCategory() {
        final ImmutableMap.Builder<String, ProductCategory> categoryMapBuilder = new ImmutableMap.Builder<>();
        final String categoryFile=UniversalConfigFiles.getCategoyFile();
        final CategoryData[] categories = loadArrayFromJsonResource(categoryFile, CategoryData[].class);
        if (categories == null) {
            throw new RuntimeException("No productCategories (our own categories) data at " + categoryFile);
        }

        final Set<String> codeSet = new HashSet<>();
        for (final CategoryData category : categories) {
            // fix code and name, check duplicate code
            category.setCode(category.getCode().toLowerCase().trim());
            if (codeSet.contains(category.getCode())) {
                throw new RuntimeException("Duplicate category code found at " + categoryFile + " for " + category.getCode());
            }
            codeSet.add(category.getCode());
            categoryMapBuilder.put(category.getCode(), new ProductCategory(category));
        }

        return categoryMapBuilder.build();
    }

    static Map<String, StoreChain> loadStoreChains(final Map<String, ProductCategory> categoryMap){
        final ImmutableMap.Builder<String, StoreChain> chainMapBuilder = new ImmutableMap.Builder<>();
        final String storeFile=UniversalConfigFiles.getStoresFile();
        final StoreChainData[] storeChains = loadArrayFromJsonResource(storeFile, StoreChainData[].class);
        validateStoreChainData(storeChains);
        if (storeChains == null) {
            throw new RuntimeException("No store chains data at "+storeFile);
        }
        for (StoreChainData chain : storeChains) {
            final List<StoreBranch> branches = loadStoreBranches(chain.getCode());
            final Map<String, CatalogProduct> products = loadCatalogProducts(chain.getCode(), categoryMap);
            final List<String> notations = loadStringList(ChainConfigFiles.getNotations(chain.getCode()));
            final List<String> identifyFields = loadStringList(ChainConfigFiles.getIdentify(chain.getCode()));
            final Properties headerProperties=loadHeaderProperties(chain.getCode());
            final Properties nonHeaderProperties=loadNonHeaderProperties(chain.getCode());
            final List<String> notCatalogItemNames = loadStringList(ChainConfigFiles.getBlackList(chain.getCode()));
            final List<String> skipBefore = loadStringList(ChainConfigFiles.getSkipBefore(chain.getCode()));
            final List<String> skipAfter = loadStringList(ChainConfigFiles.getSkipAfter(chain.getCode()));
            chainMapBuilder.put(chain.getCode(),
                                StoreChain.fromChainBranchesIdentifyMapNotationsHeaderNonHeaderBeforeAfter(
                                        chain,
                                        branches,
                                        identifyFields,
                                        products,
                                        notations,
                                        headerProperties,
                                        nonHeaderProperties,
                                        notCatalogItemNames,
                                        skipBefore,
                                        skipAfter));
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
        final String branchFileName = ChainConfigFiles.getBranches(chainCode);
        final StoreBranchData[] storeBranches = loadArrayFromJsonResource(branchFileName, StoreBranchData[].class);
        if (storeBranches != null) {
            for(final StoreBranchData branch : storeBranches) {
                //TODO any validation???
                branchListBuilder.add(new StoreBranch(branch));
            }
        }
        return branchListBuilder.build();
    }

    static List<String> loadStringList(final String resourceFileName) {
        final ImmutableList.Builder<String> branchListBuilder = new ImmutableList.Builder<>();
        final String[] identifys = loadArrayFromJsonResource(resourceFileName, String[].class);
        if (identifys != null) {
            for(final String id : identifys) {
                branchListBuilder.add(id);
            }
        }
        return branchListBuilder.build();
    }

    static Properties loadHeaderProperties(final String chainCode){
        return loadObjectFromJsonResource(ChainConfigFiles.getHeaders(chainCode), Properties.class);
    }

    static Properties loadNonHeaderProperties(final String chainCode){
        return loadObjectFromJsonResource(ChainConfigFiles.getNonHeaderProperties(chainCode), Properties.class);
    }

    static Map<String, CatalogProduct> loadCatalogProducts(final String chainCode, final Map<String, ProductCategory> categoryMap) {
        final ImmutableMap.Builder<String, CatalogProduct> productListBuilder = new ImmutableMap.Builder<>();
        final String catalogFileName = ChainConfigFiles.getCatalog(chainCode);
        final ProductData[] products = loadArrayFromJsonResource(catalogFileName, ProductData[].class);

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

    public static <T> T[] loadArrayFromJsonResource(String resourceFileName, Class<T[]> clazz) {
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

    public static <T> T loadObjectFromJsonResource(String resourceFileName, Class<T> clazz) {
        final ObjectMapper mapper = new ObjectMapper();
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
