package com.openprice.store;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.openprice.store.data.CategoryData;
import com.openprice.store.data.ProductData;
import com.openprice.store.data.StoreBranchData;
import com.openprice.store.data.StoreChainData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetadataLoader {

    public static final String GENERIC_STORE_CODE = "generic";  // special store chain code for unknown generic store

    //a store parser will load from a file from this folder if it doesn't have the file
    public static final String SHARED_CONFIG_ROOT = "sharedConfigurationFilesShouldNotCoincideWithAnyStoreNames";

    public static Properties loadSharedNonHeader(){
        return loadNonHeaderProperties(SHARED_CONFIG_ROOT);
    }

    public static Properties loadSharedHeader(){
        return loadHeaderProperties(SHARED_CONFIG_ROOT);
    }

    public static List<String> loadSharedNotCatalogItemNames(){
        return loadStringList(ChainConfigFiles.getBlackList(SHARED_CONFIG_ROOT));
    }

    public static List<String> loadSharedSkipAfter(){
        return loadStringList(ChainConfigFiles.getSkipAfter(SHARED_CONFIG_ROOT));
    }

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

    /*
     * only the chain code and name are used from store-data.json
     * we should use them if such data is available.
     */
    static Map<String, StoreChain> loadStoreChains(final Map<String, ProductCategory> categoryMap){
        final ImmutableMap.Builder<String, StoreChain> chainMapBuilder = new ImmutableMap.Builder<>();
        final String storeFile=UniversalConfigFiles.getStoresFile();
        final StoreChainData[] storeChains = loadArrayFromJsonResource(storeFile, StoreChainData[].class);
        validateStoreChainData(storeChains);
        if (storeChains == null) {
            throw new RuntimeException("No store chains data at "+storeFile);
        }

        final Properties sharedHeader = loadSharedHeader();
        final Properties sharedNonHeader = loadSharedNonHeader();
        final List<String> sharedNotCatalogItemNames = loadSharedNotCatalogItemNames();
        final List<String> sharedAfter = loadSharedSkipAfter();

        for (StoreChainData chain : storeChains) {
            final List<StoreBranch> branches = loadStoreBranches(chain.getCode());
            final List<String> receiptCategories = loadStringList(ChainConfigFiles.getCategoriesOfStore(chain.getCode()));
            final Map<String, CatalogProduct> products = loadCatalogProducts(chain.getCode(), categoryMap);
            final List<String> notations = loadStringList(ChainConfigFiles.getNotations(chain.getCode()));

            List<String> identifyFields = loadStringList(ChainConfigFiles.getIdentify(chain.getCode()));
            if(identifyFields.isEmpty()){
                final ImmutableList.Builder<String> builder = new ImmutableList.Builder<>();
                builder.add(chain.getName());
                identifyFields = builder.build();
            }

            Properties headerProperties=loadHeaderProperties(chain.getCode());
            if(headerProperties == null || headerProperties.isEmpty()){
                headerProperties = sharedHeader;
            }

            Properties nonHeaderProperties=loadNonHeaderProperties(chain.getCode());
            if(nonHeaderProperties == null || nonHeaderProperties.isEmpty()){
                nonHeaderProperties = sharedNonHeader;
            }

            final List<String> notItemOfThisChain = loadStringList(ChainConfigFiles.getBlackList(chain.getCode()));
            final ImmutableSet.Builder<String> builder = new ImmutableSet.Builder<>();
            builder.add(chain.getName());
            builder.addAll(notItemOfThisChain);
            builder.addAll(sharedNotCatalogItemNames);
            final List<String> notCatalogItemNames = builder.build().stream().collect(Collectors.toList());

            final List<String> skipBefore = loadStringList(ChainConfigFiles.getSkipBefore(chain.getCode()));

            List<String> skipAfter = loadStringList(ChainConfigFiles.getSkipAfter(chain.getCode()));
            if(skipAfter == null || skipAfter.isEmpty()){
                skipAfter = sharedAfter;
            }

            chainMapBuilder.put(chain.getCode(),
                                StoreChain.fromChainCategoriesBranchesIdentifyMapNotationsHeaderNonHeaderNotItemNamesBeforeAfter(
                                        chain,
                                        receiptCategories,
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

        // HACK! Add generic store chain to chain map, so shopping list can handle receipts with unknown store
        chainMapBuilder.put(GENERIC_STORE_CODE, StoreChain.genericChainWithOnlyCode(GENERIC_STORE_CODE));

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

    //TODO apply category predictor for those products missing categories?
    static Map<String, CatalogProduct> loadCatalogProducts(final String chainCode, final Map<String, ProductCategory> categoryMap) {
        final ImmutableMap.Builder<String, CatalogProduct> productListBuilder = new ImmutableMap.Builder<>();
        final String catalogFileName = ChainConfigFiles.getCatalog(chainCode);
        final ProductData[] productsFromCatalog = loadArrayFromJsonResource(catalogFileName, ProductData[].class);
        final Set<ProductData> productsFromCatalogSet = getSet(chainCode, productsFromCatalog);
        final String flyerFileName = ChainConfigFiles.getCatalogFromFlyers(chainCode);
        final ProductData[] productsFromFlyers = loadArrayFromJsonResource(flyerFileName, ProductData[].class);
        final Set<ProductData> productsFromFlyersSet = getSet(chainCode, productsFromFlyers);

        final Set<ProductData> allProducts = new HashSet<>();
        allProducts.addAll(productsFromFlyersSet);
        allProducts.addAll(productsFromCatalogSet);
        final int numDuplicates = productsFromCatalogSet.size() + productsFromFlyersSet.size() - allProducts.size();
        if(numDuplicates > 0){
            log.warn("there are " + numDuplicates +" duplicate products across these catalog and flyers data:");
            productsFromFlyersSet
            .stream()
            .filter(p -> productsFromCatalogSet.contains(p))
            .forEach(p ->System.out.println(p));
        }

        final Set<String> codeSet = new HashSet<>();
        for(ProductData product : allProducts) {
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
        return productListBuilder.build();
    }

    final static Set<ProductData> getSet(final String chainCode, final ProductData[] array){
        if(array==null)
            return new HashSet<ProductData>();
        final List<ProductData> list = Arrays.asList(array);
        final Set<ProductData> set = list.stream().collect(Collectors.toSet());
        final int duplicates = list.size() - set.size();
        if(duplicates > 0){
            log.warn(chainCode+": There are " + duplicates +" duplicates");
            final List<ProductData> listCopy = new ArrayList<>(list);
            set.stream().forEach(p -> listCopy.remove(p));
            listCopy.forEach(p -> log.warn("duplicate: "+p));
        }
        return set;
    }

    public static <T> T[] loadArrayFromJsonResource(String resourceFileName, Class<T[]> clazz) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try (final InputStream is = MetadataLoader.class.getResourceAsStream(resourceFileName)){
            if (is == null) {
//                log.warn("Inputstream returns null for file "+resourceFileName);
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
