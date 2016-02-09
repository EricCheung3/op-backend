package com.openprice.parser;

import java.util.Comparator;
import java.util.Optional;

import com.openprice.parser.data.ScoreWithMatchPair;
import com.openprice.parser.simple.MatchToBranchImpl;
import com.openprice.store.StoreBranch;
import com.openprice.store.StoreChain;

import lombok.Data;

/**
 * In memory store chain info, like code, categories, identify fields, etc.
 *
 */
@Data
public class StoreChainUtils{
    final StoreChain storeChain;
//    final StoreParserSelector selector;

//    List<String> categories=new ArrayList<String>();
//    List<String> identifyFields=new ArrayList<String>();
//    List<StoreBranchMatch> branches=new ArrayList<StoreBranchMatch>();

//    private StoreChainMatch(final String code, final StoreParserSelector selector){
//        this.storeChain=StoreChain.genericChainWithOnlyCode(code);
//        this.selector=selector;
//    }
//
//    public static StoreChainMatch fromCodeAndParserSelector(final String code, final StoreParserSelector selector){
//       return new StoreChainMatch(code, selector);
//    }
//
//    public static StoreChainMatch genericStoreChain(final String code){
//        return fromCodeAndParserSelector(code, new DummySelector());
//    }
//
//    private StoreChainMatch(
//            final String code,
//            final StoreParserSelector selector,
//            final List<String> categories,
//            final List<String> identifyFields,
//            final List<StoreBranchMatch> branches) {
//        this.code=code;
//        this.selector=selector;
//        this.categories=categories;
//        this.identifyFields=identifyFields;
//        this.branches=branches;
//    }
//
//    public static StoreChainMatch fromCodeSelectorCategoriesFieldsBranches(
//            final String code,
//            final StoreParserSelector selector,
//            final List<String> categories,
//            final List<String> identifyFields,
//            final List<StoreBranchMatch> branches){
//        return new StoreChainMatch(code, selector, categories, identifyFields, branches);
//    }

    public static StoreBranch matchBranchByScoreSum(final StoreChain storeChain, final ReceiptData receipt) {
        Optional<ScoreWithMatchPair<StoreBranch>> maxBranchMatch =
                storeChain
                .getBranches()
                .stream()
                .map( branch -> new ScoreWithMatchPair<StoreBranch>(
                        new MatchToBranchImpl().matchScore(branch, receipt), -1, branch)
                 )
                .max( Comparator.comparing(score -> score.getScore()) );
        return maxBranchMatch.isPresent()? maxBranchMatch.get().getMatch() : null;
    }
}
