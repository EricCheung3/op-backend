package com.openprice.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openprice.parser.common.Levenshtein;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.ScoreWithMatchPair;

import lombok.Getter;

/**
 * Service to store all store chains in the system as registry. Each StoreParserSelector service will register
 * a StoreChain to this registry.
 *
 */
@Service
//@Slf4j
public class ChainRegistry {
    private static final double CHAIN_IDENTIFY_MATCH_THRESHOLD = 0.8;

    @Getter
    private final List<StoreChain> storeChains = Collections.synchronizedList(new ArrayList<>());

    public synchronized void addChain(StoreChain chain) {
        storeChains.add(chain);
    }

    public StoreChain findBestMatchedChain(final ReceiptData receipt) {
        final List<ReceiptLine> lines = receipt.getTopBottomChainMatchingLines();
        //log.debug("TopBottom matching lines:\n"+lines);
        //log.debug("search chains in registry with "+storeChains);

        final Optional<ScoreWithMatchPair<StoreChain>> maxChainMatch =
            storeChains
            .stream()
            .map( chain -> {
                // score sum for all match identify fields whose matching score is > CHAIN_IDENTIFY_MATCH_THRESHOLD
                double matchingScoreSum =
                    lines
                    .stream()
                    .filter( line -> StringCommon.countDigitAndChars(line.getCleanText())[1] >= 2)
                    .map( line -> {
                        Optional<ScoreWithMatchPair<String>> maxIdentifyMatch =
                            chain
                            .getIdentifyFields()
                            .stream()
                            .map( identify -> {
                                final String matchingString = identify.trim().toLowerCase();
                                if (line.getCleanText().contains(matchingString)) { // FIXME may cause problem if item line contains store chain name, such as "T&T"
                                    return new ScoreWithMatchPair<String>(1.0, line.getNumber(), identify);
                                }
                                return new ScoreWithMatchPair<String>(
                                       Levenshtein.compare(line.getCleanText(), matchingString),
                                       line.getNumber(),
                                       identify);
                            })
                            .max( Comparator.comparing(ScoreWithMatchPair<String>::getScore) )
                            ;
                        if (maxIdentifyMatch.isPresent()) {
                            //log.debug("maxIdentifyMatch score is "+maxIdentifyMatch.get().getScore());
                            return maxIdentifyMatch.get().getScore();
                        }
                        return -1.0;
                    })
                    .filter( score -> score > CHAIN_IDENTIFY_MATCH_THRESHOLD)
                    .reduce(0.0, Double::sum)
                    ;
                return new ScoreWithMatchPair<StoreChain>(matchingScoreSum, -1, chain);
            })
            .max( Comparator.comparing(ScoreWithMatchPair<StoreChain>::getScore) )
            ;

        return maxChainMatch.isPresent()? maxChainMatch.get().getMatch() : null;
    }

}
