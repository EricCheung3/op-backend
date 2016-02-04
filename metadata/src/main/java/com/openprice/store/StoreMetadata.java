package com.openprice.store;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.openprice.common.Levenshtein;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

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

    @Value
    private static class ProductWithScore {
        CatalogProduct p;
        double s;
    }
    public List<CatalogProduct> findMatchingProducts(final String query,
                                                     final StoreChain storeChain,
                                                     final int returnCount) {
        return storeChain
                .getProducts()
                .stream()
                .map(p -> {
                        Set<String> s = new HashSet<>(Arrays.asList(p.getNaturalName().split("\\s+")));
                        double score = Levenshtein.mostSimilarScoreInSet(query, s);
                        return new ProductWithScore(p, score);
                })
                .filter(ps -> ps.s > 0.5)
                .sorted((p1, p2) -> Double.compare(0 - p1.s, 0 - p2.s))
                .limit(returnCount)
                .map(ps -> ps.p)
                .collect(Collectors.toList());
    }

}
