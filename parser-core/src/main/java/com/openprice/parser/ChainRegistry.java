package com.openprice.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.openprice.common.StringCommon;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.ReceiptLine;
import com.openprice.parser.api.StoreParserSelector;
import com.openprice.parser.data.FoundChainAt;
import com.openprice.parser.data.ScoreWithMatchPair;
import com.openprice.parser.data.StoreChainFound;
import com.openprice.store.StoreChain;

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
    private static final double CHAIN_IDENTIFY_MATCH_THRESHOLD = 0.8;

    //limit on the number of lines that are searched when looking for chain
    private static final int CHAIN_SEARCH_NUMBER_LINES = 10;

    @Getter
    private final List<StoreChain> storeChains = Collections.synchronizedList(new ArrayList<>());

    private final Map<String, StoreParserSelector> chainToParserSelector=new HashMap<>();

    public synchronized void addChain(final StoreChain chain, final StoreParserSelector selector) {
        storeChains.add(chain);
        chainToParserSelector.put(chain.getCode(), selector);
    }

    public StoreParserSelector getParserSelector(final String chainCode){
        return chainToParserSelector.get(chainCode);
    }


    /*
     * prefer finding in the begin, then in the end, and then middle
     *
     */
    public StoreChainFound findBestMatchedChain(final ReceiptData receipt) {
        final int lastLineOfBegin=CHAIN_SEARCH_NUMBER_LINES;
        final ScoreWithMatchPair<StoreChain> foundAtBegin = findBestMatchedChain(receipt, 0, lastLineOfBegin);
        log.debug("receipt.size="+receipt.getOriginalLines().size());
        if(foundAtBegin==null)
            log.debug("foundAtBegin=null");
        else
            log.debug("foundAtBegin: "+foundAtBegin.getMatch().getCode()+", score="+foundAtBegin.getScore());

        final int firstLineOfEnd=receipt.getReceiptLines().size() - CHAIN_SEARCH_NUMBER_LINES;
        //TODO: is receipt.getReceiptLines().size() the same as the largest original line number? Did you guarantee this through interface?
        final ScoreWithMatchPair<StoreChain> foundAtEnd = findBestMatchedChain(receipt,  firstLineOfEnd, receipt.getReceiptLines().size());
        if(foundAtEnd==null)
            log.debug("foundAtEnd=null");
        else
            log.debug("foundAtEnd: "+foundAtEnd.getMatch().getCode()+", score="+foundAtEnd.getScore());

        if(foundAtBegin != null
                && foundAtEnd != null
//                && foundAtBegin.getScore() >= foundAtEnd.getScore()
                && foundAtBegin.getScore() > CHAIN_IDENTIFY_MATCH_THRESHOLD)
            return new StoreChainFound(foundAtBegin.getMatch(), FoundChainAt.BEGIN, foundAtBegin.getLineNumber());

        if(foundAtEnd !=null
                && foundAtBegin != null
                && foundAtEnd.getScore() > foundAtBegin.getScore()
                && foundAtEnd.getScore() > CHAIN_IDENTIFY_MATCH_THRESHOLD)
            return new StoreChainFound(foundAtEnd.getMatch(), FoundChainAt.END, foundAtEnd.getLineNumber());

        if(foundAtBegin != null
                && foundAtBegin.getScore() > CHAIN_IDENTIFY_MATCH_THRESHOLD)
            return new StoreChainFound(foundAtBegin.getMatch(), FoundChainAt.BEGIN, foundAtBegin.getLineNumber());

        if(foundAtEnd !=null
                && foundAtEnd.getScore() > CHAIN_IDENTIFY_MATCH_THRESHOLD)
            return new StoreChainFound(foundAtEnd.getMatch(), FoundChainAt.END, foundAtEnd.getLineNumber());

        final ScoreWithMatchPair<StoreChain> foundAtMiddle = findBestMatchedChain(receipt, lastLineOfBegin+1, firstLineOfEnd-1);
        if(foundAtMiddle == null)
            log.debug("foundAtMiddle=null");
        else
            log.debug("foundAtMiddle: "+foundAtMiddle.getMatch().getCode()+", score="+foundAtMiddle.getScore());
        if(foundAtMiddle !=null
                && foundAtMiddle.getScore() > CHAIN_IDENTIFY_MATCH_THRESHOLD)
            return new StoreChainFound(foundAtMiddle.getMatch(), FoundChainAt.MIDDLE, foundAtMiddle.getLineNumber());

        return null;
    }

    public ScoreWithMatchPair<StoreChain> findBestMatchedChain(final ReceiptData receipt, final int begin, final int end) {
        final List<ReceiptLine> lines = receipt.getReceiptLines();
        //log.debug("TopBottom matching lines:\n"+lines);
        //log.debug("search chains in registry with "+storeChains);
        //log.debug("storeChain lists.size="+storeChains.size()+":");
        //storeChains.forEach(c -> log.debug(c.getCode()));

        final Optional<ScoreWithMatchPair<StoreChain>> maxChainMatch =
                storeChains
                .stream()
                .map( chain -> {
                    // score sum for all match identify fields whose matching score is > CHAIN_IDENTIFY_MATCH_THRESHOLD
                    double matchingScoreSum =
                            lines
                            .stream()
                            .filter( line -> line.getNumber() >= begin && line.getNumber() <= end )
                            .filter( line -> {
                                boolean noisyLine=StringCommon.countDigitAndChars(line.getCleanText())[1] >= 2;
                                return noisyLine;
                            })
                            .map( line -> {
                                Optional<ScoreWithMatchPair<String>> maxIdentifyMatch =
                                        chain
                                        .getIdentifyFields()
                                        .stream()
                                        .map( identify -> {
                                            final String identifyField = identify.trim().toLowerCase();
                                            if (line.getCleanText().toLowerCase().contains(identifyField)) { // FIXME may cause problem if item line contains store chain name, such as "T&T"
//                                                log.debug("line="+line.getCleanText()+", identifyField="+identifyField+", score=1.0");
                                                return new ScoreWithMatchPair<String>(1.0, line.getNumber(), identify);
                                            }
//                                            double score=Levenshtein.compare(line.getCleanText(), identifyField);
                                            double score=StringCommon.matchStringToSubStringTwoWay(line.getCleanText(), identifyField);
//                                            log.debug("line="+line.getCleanText()+", identifyField="+identifyField+", score="+score);
                                            return new ScoreWithMatchPair<String>(
                                                    score,
                                                    line.getNumber(),
                                                    identify);
                                        })
                                        .max( Comparator.comparing(ScoreWithMatchPair<String>::getScore) )
                                        ;
                                if (maxIdentifyMatch.isPresent()) {
//                                    log.debug("maxIdentifyMatch score for line '{}' is {}.", line.getCleanText(), maxIdentifyMatch.get().getScore());
                                    return maxIdentifyMatch.get().getScore();
                                }
                                return -1.0;
                            })
                            .filter( score -> score > CHAIN_IDENTIFY_MATCH_THRESHOLD)
                            .reduce(0.0, Double::sum)
                            ;
//                    log.debug("Get matching score {} for chain {} at line {}", matchingScoreSum, chain.getCode(), );
                    return new ScoreWithMatchPair<StoreChain>(matchingScoreSum, -1, chain);
                })
                //.filter(pair -> pair.getScore() > CHAIN_IDENTIFY_MATCH_THRESHOLD)
                .max( Comparator.comparing(ScoreWithMatchPair<StoreChain>::getScore) );

        return maxChainMatch.isPresent()? maxChainMatch.get() : null;
    }

}
