package com.openprice.parser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.openprice.parser.data.ScoreWithMatchPair;

import lombok.Data;

/**
 * In memory store chain info, like code, categories, identify fields, etc.
 *
 */
@Data
public class StoreChain {
    final String code;
    final StoreParserSelector selector;

    List<String> categories=new ArrayList<String>();
    List<String> identifyFields=new ArrayList<String>();
    List<StoreBranch> branches=new ArrayList<StoreBranch>();

    private StoreChain(final String code, final StoreParserSelector selector){
        this.code=code;
        this.selector=selector;
    }

    public static StoreChain fromCodeAndParserSelector(final String code, final StoreParserSelector selector){
       return new StoreChain(code, selector);
    }

    public static StoreChain genericStoreChain(final String code){
        return fromCodeAndParserSelector(code, new DummySelector());
    }

    private StoreChain(
            final String code,
            final StoreParserSelector selector,
            final List<String> categories,
            final List<String> identifyFields,
            final List<StoreBranch> branches) {
        this.code=code;
        this.selector=selector;
        this.categories=categories;
        this.identifyFields=identifyFields;
        this.branches=branches;
    }

    public static StoreChain fromCodeSelectorCategoriesFieldsBranches(
            final String code,
            final StoreParserSelector selector,
            final List<String> categories,
            final List<String> identifyFields,
            final List<StoreBranch> branches){
        return new StoreChain(code, selector, categories, identifyFields, branches);
    }

    public StoreBranch matchBranchByScoreSum(final ReceiptData receipt) {
        Optional<ScoreWithMatchPair<StoreBranch>> maxBranchMatch =
            branches.stream()
                    .map( branch -> new ScoreWithMatchPair<StoreBranch>(branch.matchScore(receipt), -1, branch) )
                    .max( Comparator.comparing(score -> score.getScore()) )
                    ;
        return maxBranchMatch.isPresent()? maxBranchMatch.get().getMatch() : null;
    }

}
