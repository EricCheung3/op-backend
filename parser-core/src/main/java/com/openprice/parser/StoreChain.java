package com.openprice.parser;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * In memory store chain info, like code, categories, identify fields, etc.
 *
 */
@Data
@Builder
public class StoreChain {
    private final String code;
    private final List<String> categories;
    private final List<String> identifyFields;
    private final List<StoreBranch> branches;
    private final StoreParserSelector selector;

    public StoreBranch matchBranchByScoreSum(final ReceiptData receipt) {
        double scoreMax=0;
        StoreBranch matchBranch=null;
        for(StoreBranch branch : branches){
            double score=branch.matchScore(receipt);
            if(score>scoreMax){
                scoreMax=score;
                matchBranch=branch;
            }
        }
        return matchBranch;
    }

}
