package com.openprice.store;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.openprice.common.Levenshtein;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    public List<StoreChain> allStoreChains(){
        return storeChainMap
                .entrySet()
                .stream()
                .map(e->e.getValue())
                .collect(Collectors.toList());
    }

    @Value
    private static class ProductWithScore {
        CatalogProduct p;
        double s;
    }

    public List<CatalogProduct> findMatchingProducts(final String query,
                                                     final String chainCode,
                                                     final int returnCount) {
        final StoreChain storeChain = getStoreChainByCode(chainCode);
        if (storeChain == null) {
            return Collections.emptyList();
        }
        return storeChain
                .getProducts()
                .stream()
                .map(p -> {
                        Set<String> s = new HashSet<>(Arrays.asList(p.getNaturalName().split("\\s+")));
                        double score = Levenshtein.mostSimilarScoreInSetLevenshtein(query, s);
                        return new ProductWithScore(p, score);
                })
                .filter(ps -> ps.s > 0.3)
                .sorted((p1, p2) -> Double.compare(0 - p1.s, 0 - p2.s))
                .limit(returnCount)
                .map(ps -> ps.p)
                .collect(Collectors.toList());
    }

    @Value
    private static class StoreChainWithScore {
        StoreChain chain;
        double s;
    }

    public List<StoreChain> findMatchingStoreChainByName(final String query, final int returnCount) {
        //special treatment for single-char query
        final String queryTrimLower = query.trim().toLowerCase();
        List<StoreChain> singleCharResults = null;
        if(queryTrimLower.length() == 1 && !Character.isDigit(queryTrimLower.charAt(0))){
            singleCharResults =  storeChainMap
            .values()
            .stream()
            .filter(chain -> chain.getName().toLowerCase().startsWith(queryTrimLower))
            .limit(returnCount)
            .collect(Collectors.toList());
            if (!singleCharResults.isEmpty()){
                return singleCharResults;
            }
        }

        return storeChainMap
                .values()
                .stream()
                .map(chain -> {
                    List<String> s = Arrays.asList(chain.getName().toLowerCase().split("\\s+"));
//                    Set<String> s = new HashSet<>(Arrays.asList(chain.getName().split("\\s+")));
//                    double score = Levenshtein.mostSimilarScoreInSetLevenshtein(queryTrim, s);
//                    double score = Levenshtein.mostSimilarScoreInSetTwoWay(queryTrim, s);
                    double score = Levenshtein.weightedScoreByPositionOrder(queryTrimLower, s);
                    log.debug("score="+ score);
                    return new StoreChainWithScore(chain, score);
                })
                .filter(scs -> {
                    log.debug("chain="+scs.getChain().getName()+ ", score="+scs.getS());
                    return scs.s > 0.2;
                 })
                .sorted((scs1, scs2) -> Double.compare(0 - scs1.s, 0 - scs2.s))
                .limit(returnCount)
                .map(scs -> scs.chain)
                .collect(Collectors.toList())
                ;
    }

}
