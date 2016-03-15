package com.openprice.store;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

        final String queryTrimLower = query.trim().toLowerCase();

        //special treatment for single-char query
        List<CatalogProduct> singleCharResults = null;
        if(queryTrimLower.length() == 1 && !Character.isDigit(queryTrimLower.charAt(0))){
            singleCharResults =  storeChain
                                    .getProducts()
                                    .stream()
                                    .filter(p -> p.getNaturalName().toLowerCase().startsWith(queryTrimLower))
                                    .limit(returnCount)
                                    .collect(Collectors.toList());
                                    if (!singleCharResults.isEmpty()){
                                        return singleCharResults;
                                    }
        }

        final List<CatalogProduct> startWithQueryResults =storeChain
                .getProducts()
                .stream()
                .filter(p -> (Arrays.asList(p.getNaturalName().toLowerCase().split("\\s+")))
                        .stream().anyMatch(w->w.startsWith(queryTrimLower)))
                .limit(returnCount)
                .map(p -> {
                    double score = Levenshtein.weightedScoreByPositionOrder(query, Arrays.asList(p.getNaturalName().split("\\s+")));
                    return new ProductWithScore(p, score);
                })
                .sorted((p1, p2) -> Double.compare(0 - p1.s, 0 - p2.s))
                .map(ps -> ps.p)
                .collect(Collectors.toList());
                if (!startWithQueryResults.isEmpty() && startWithQueryResults.size() == returnCount){
                    return startWithQueryResults;
                }

        final List<CatalogProduct> appendedResultsFromLevenshtein = storeChain
            .getProducts()
            .stream()
            .map(p -> {
                    double score = Levenshtein.weightedScoreByPositionOrder(query, Arrays.asList(p.getNaturalName().split("\\s+")));
                    return new ProductWithScore(p, score);
            })
            .filter(ps -> ps.s > 0.3)
            .filter(ps -> !startWithQueryResults.contains(ps.getP()))
            .sorted((p1, p2) -> Double.compare(0 - p1.s, 0 - p2.s))
            .limit(returnCount)
            .map(ps -> ps.p)
            .collect(Collectors.toList());

        startWithQueryResults.addAll(appendedResultsFromLevenshtein);
        return startWithQueryResults;
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

        final List<StoreChain> startWithQueryResults = storeChainMap
            .values()
            .stream()
            .filter(chain -> chain.getName().toLowerCase().startsWith(queryTrimLower))
            .limit(returnCount)
            .collect(Collectors.toList());
            if (!startWithQueryResults.isEmpty() && startWithQueryResults.size() == returnCount){
                return startWithQueryResults;
            }

        //append results by Levenshtein
        final List<StoreChain> appendedResultsFromLevenshtein = storeChainMap
                .values()
                .stream()
                .map(chain -> {
                    List<String> s = Arrays.asList(chain.getName().toLowerCase().split("\\s+"));
                    double score = Levenshtein.weightedScoreByPositionOrder(queryTrimLower, s);
                    return new StoreChainWithScore(chain, score);
                })
                .filter(scs -> {
                    return scs.s > 0.2;
                 })
                .filter(scs -> !startWithQueryResults.contains(scs.chain))
                .sorted((scs1, scs2) -> Double.compare(0 - scs1.s, 0 - scs2.s))
                .limit(returnCount - startWithQueryResults.size())
                .map(scs -> scs.chain)
                .collect(Collectors.toList())
                ;
        startWithQueryResults.addAll(appendedResultsFromLevenshtein);
        return startWithQueryResults;
    }

}
