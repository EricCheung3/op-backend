package com.openprice.parser.store;

import java.util.List;

import com.openprice.parser.common.Levenshtein;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.exception.ChainNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChainRecognizer {
    private final static String SECONDLEVEL_SPLITTER = ",";// the splitter used
    // inside a field
    // between :

    // use a large values to search all lines from the end
    private static final int MaxSearchedLinesEnd = 6;
    private static final int MaxSearchedLinesBegin = 6;

    private final ChainRegistry chainRegistry;

    public ChainRecognizer(final ChainRegistry chainRegistry) {
        this.chainRegistry = chainRegistry;
    }

    public StoreChain findChain(final List<String> lines) throws ChainNotFoundException {
        if (lines == null || lines.isEmpty())
            return null;
        // find the meaningful lines in the beginning and end
        int begin = -1;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] > 2) {
                begin = i;
                break;
            }
        }

        // fast mode: searching a few number of lines from beginning.
        MatchedChain chainBegin = chainNameSearch(lines, begin, begin + MaxSearchedLinesBegin);
        if (chainBegin.getMatchedScore() > 0.75)
            return chainBegin.getChain();

        int end = -1;
        for (int i = lines.size() - 1; i >= 0; i--) {
            String line = lines.get(i).trim();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] > 2) {
                end = i;
                break;
            }
        }
        MatchedChain chainEnd = chainNameSearch(lines, end - MaxSearchedLinesEnd, end);
        log.debug("#####searching from head: chain=" + chainBegin.getChain().getCode() + ", score=" + chainBegin.getMatchedScore());
        log.debug("#####searching from End: chain=" + chainEnd.getChain().getCode() + ", score=" + chainEnd.getMatchedScore());
        if (chainEnd.getMatchedScore() > chainBegin.getMatchedScore())
            return chainEnd.getChain();
        else
            return chainBegin.getChain();
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
    MatchedChain chainNameSearch(final List<String> lines, final int begin, final int end) throws ChainNotFoundException {
        double maxScore = -1;
        int found = -1;
        StoreChain matchedStoreChain = null;
        for (int i = Math.max(0, begin); i <= Math.min(lines.size() - 1, end); i++) {
            final String line = lines.get(i).trim();
            int[] counts = StringCommon.countDigitAndChars(line);
            if (counts[1] < 2)
                continue;

            for (StoreChain storeChain : chainRegistry.getStoreChains()) {
                double score = matchChainScore(storeChain, line);
                if (score > maxScore) {
                    maxScore = score;
                    matchedStoreChain = storeChain;
                    found = i;
                }
                if (Math.abs(1.0 - score) < 0.02) {
                    final MatchedChain chain = MatchedChain.builder().chain(storeChain).matchedScore(score).matchedOnLine(i).build();
                    return chain;
                }
            }
        }
        if (matchedStoreChain == null)
            throw new ChainNotFoundException("no chain found between line " + begin + " and line " + end);
        final MatchedChain chain = MatchedChain.builder().chain(matchedStoreChain).matchedScore(maxScore).matchedOnLine(found).build();
        return chain;
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


}
