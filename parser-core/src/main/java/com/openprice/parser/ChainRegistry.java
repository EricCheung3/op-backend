package com.openprice.parser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openprice.parser.common.Levenshtein;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.ScoreWithMatchPair;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Service to store all store chains in the system as registry. Each StoreParserSelector service will register
 * a StoreChain to this registry.
 *
 */
@Service
@Slf4j
public class ChainRegistry {
    @Getter
    private final List<StoreChain> storeChains = new ArrayList<>();

    public synchronized void addChain(StoreChain chain) {
        storeChains.add(chain);
    }

    // use a large values to search all lines from the end
    private static final int MaxSearchedLinesEnd = 6;
    private static final int MaxSearchedLinesBegin = 10;

    public MatchedChain findChain(final ReceiptData receipt) {
        final List<ReceiptLine> lines = receipt.getReceiptLines();
        if (lines == null || lines.isEmpty())
            return null;

        // find the meaningful lines in the beginning
        int begin = -1;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).getCleanText();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] > 2) {
                begin = i;
                break;
            }
        }

        // fast mode: searching a few number of lines from beginning.
        MatchedChain chainBegin = chainNameSearch(lines, begin, begin + MaxSearchedLinesBegin);
        if (chainBegin == null) {
            log.warn("Cannot recognize chain from begnning!!");
        } else {
            log.debug("Searching from head: chain=" + chainBegin.getChain().getCode() + ", score=" + chainBegin.getMatchedScore());
            if (chainBegin.getMatchedScore() > 0.75) {
                return chainBegin;
            }
        }

        int end = -1;
        for (int i = lines.size() - 1; i >= 0; i--) {
            String line = lines.get(i).getCleanText();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] > 2) {
                end = i;
                break;
            }
        }
        MatchedChain chainEnd = chainNameSearch(lines, end - MaxSearchedLinesEnd, end);
        if (chainEnd == null) {
            log.warn("Cannot recognize chain from end!!");
        } else {
            log.debug("Searching from End: chain=" + chainEnd.getChain().getCode() + ", score=" + chainEnd.getMatchedScore());
        }

        if (chainBegin == null && chainEnd == null) {
            return null;
        }
        if (chainBegin != null && chainEnd == null) {
            return chainBegin;
        }
        if (chainBegin == null && chainEnd != null) {
            return chainEnd;
        }
        if (chainEnd.getMatchedScore() > chainBegin.getMatchedScore())
            return chainEnd;
        else
            return chainBegin;
    }

//    MatchedChain chainNameSearch(final List<ReceiptLine> lines) {
//        Optional<ScoreWithMatchPair<StoreChain>> chainMatch =
//            lines.stream()
//                 .filter( line -> StringCommon.countDigitAndChars(line.getCleanText())[1] >= 2 )
//                 .map( line -> {
//                     Optional<ScoreWithMatchPair<StoreChain>> score =
//                         storeChains.stream()
//                                    .map( chain -> {
//                                        Optional<ScoreWithMatchPair<String>> identifyMatch = chain.matchChainScore(line);
//                                        return identifyMatch.isPresent()?
//                                                new ScoreWithMatchPair<StoreChain>(identifyMatch.get().getScore(), identifyMatch.get().getLineNumber(), chain)
//                                                : new ScoreWithMatchPair<StoreChain>(-1.0, -1, chain);
//                                    })
//                                    .max(Comparator.comparing(score -> score.getScore()))
//                                    ;
//
//                 })
//                 .max( Comparator.comparing(score -> score.getScore()))
//                 ;
//         return chainMatch.isPresent()? new MatchedChain(chainMatch.get().getMatch(), chainMatch.get().getScore(), chainMatch.get().getLineNumber())
//                 : null;
//
//    }

    Optional<MatchedChain> matchReceiptLineWithRegisteredChain(final ReceiptLine line) {
        Optional<ScoreWithMatchPair<StoreChain>> chainMatch =
                storeChains.stream()
                           .map( chain -> chain.matchChainScore(line))
                           .max(Comparator.comparing(score -> score.getScore()))
                           ;
      return chainMatch.isPresent()?
              Optional.of(new MatchedChain(chainMatch.get().getMatch(), chainMatch.get().getScore(), chainMatch.get().getLineNumber()))
              : Optional.empty();
    }

    /*
     * detect which chain the store between begin and end
     *
     * @param begin the begin line number
     *
     * @param end the end line number
     *
     * @return a Chain object, the first is matched chain name, the second is
     * the score, the third is the line number matched.
     */
    MatchedChain chainNameSearch(final List<ReceiptLine> lines, final int begin, final int end) {
        double maxScore = -1;
        int found = -1;
        StoreChain matchedStoreChain = null;
        for (int i = Math.max(0, begin); i <= Math.min(lines.size() - 1, end); i++) {
            final String line = lines.get(i).getCleanText();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] < 2)
                continue;

            for (StoreChain storeChain : getStoreChains()) {
                double score = matchChainScore(storeChain, line);
                if (score > maxScore) {
                    maxScore = score;
                    matchedStoreChain = storeChain;
                    found = i;
                }
                if (Math.abs(1.0 - score) < 0.02) {
                    return MatchedChain.builder().chain(storeChain).matchedScore(score).matchedOnLine(i).build();
                }
            }
        }
        if (matchedStoreChain == null) {
            return null;
        }

        return MatchedChain.builder().chain(matchedStoreChain).matchedScore(maxScore).matchedOnLine(found).build();
    }

    /*
     * For one line of receipt data, calculate matching score for specific store chain, by matching the chain
     * identify fields string array.
     */
    private double matchChainScore(final StoreChain storeChain, final String receiptLine) {
        double maxScore = -1, score = 0;
        for (final String identify : storeChain.getIdentifyFields()) {
            String rLine = receiptLine.toLowerCase();
            String cName = identify.trim().toLowerCase();
            if (rLine.contains(cName)) {
                score = 1.0;
                maxScore = 1.0;
                break;
            } else
                score = Levenshtein.compare(rLine, cName);
            if (score > maxScore) {
                maxScore = score;
            }
        }
        return maxScore;
    }

    @Data
    @Builder
    public static class MatchedChain {

        // matched StoreChain
        private final StoreChain chain;
        private final double matchedScore;
        private final int matchedOnLine;
    }

}
