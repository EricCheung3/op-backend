package com.openprice.parser;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.openprice.parser.data.ScoreWithMatchPair;

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
        Optional<ScoreWithMatchPair<StoreBranch>> maxBranchMatch =
            branches.stream()
                    .map( branch -> new ScoreWithMatchPair<StoreBranch>(branch.matchScore(receipt), -1, branch) )
                    .max( Comparator.comparing(score -> score.getScore()) )
                    ;
        return maxBranchMatch.isPresent()? maxBranchMatch.get().getMatch() : null;
    }

}
