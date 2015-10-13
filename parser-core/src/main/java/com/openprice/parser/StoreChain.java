package com.openprice.parser;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.openprice.parser.common.Levenshtein;
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

    /**
     * Iterates store chain's identify fields and get max score. If no identify fields, return with -1.0 as score.
     * @param receiptLine
     * @return
     */
    public ScoreWithMatchPair<StoreChain> matchChainScore(final ReceiptLine receiptLine) {
        Optional<ScoreWithMatchPair<String>> identifyMatch =
            identifyFields.stream()
                          .map( identify -> {
                              final String matchingString = identify.trim().toLowerCase();
                              if (receiptLine.getCleanText().contains(matchingString)) {
                                  return new ScoreWithMatchPair<String>(1.0, receiptLine.getNumber(), identify);
                              }
                              return new ScoreWithMatchPair<String>(Levenshtein.compare(receiptLine.getCleanText(), matchingString),
                                                              receiptLine.getNumber(),
                                                              identify);
                          })
                          .max(Comparator.comparing(score -> score.getScore()))
                          ;
        return identifyMatch.isPresent()?
                new ScoreWithMatchPair<StoreChain>(identifyMatch.get().getScore(), identifyMatch.get().getLineNumber(), this)
                : new ScoreWithMatchPair<StoreChain>(-1.0, -1, this);

   }

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
