package com.openprice.parser.store;

import java.util.List;

import com.openprice.parser.ReceiptData;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * In memory store chain info, like code, categories, identify fields, etc.
 *
 */
@Data
@Builder
@Slf4j
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
            log.debug("@@@@@for branch " + branch.getBranchName()+", the score="+score+"\n");
            if(score>scoreMax){
                scoreMax=score;
                matchBranch=branch;
            }
        }
        return matchBranch;
    }

}
