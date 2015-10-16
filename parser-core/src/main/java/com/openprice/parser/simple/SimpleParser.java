package com.openprice.parser.simple;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreBranch;
import com.openprice.parser.StoreChain;
import com.openprice.parser.StoreParser;
import com.openprice.parser.StoreParserSelector;
import com.openprice.parser.common.ListCommon;
import com.openprice.parser.data.Item;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SimpleParser {
    private final ChainRegistry chainRegistry;

    @Inject
    public SimpleParser(final ChainRegistry chainRegistry) {
        this.chainRegistry = chainRegistry;
    }

    public ParsedReceipt parse(final List<String> lines) throws Exception {
        final ReceiptData receipt = ReceiptData.fromContentLines(lines);

        // find chain first
        final StoreChain chain = chainRegistry.findBestMatchedChain(receipt);
        if (chain == null) {
            log.warn("Cannot find matching store chain!");
            //TODO call default parser to parse
            return null;
        }

        // get store branch
        final StoreBranch branch = chain.matchBranchByScoreSum(receipt);

        // get store parser
        final StoreParserSelector selector = chain.getSelector();
        final StoreParser parser = selector.selectParser(receipt);

        // match fields
        final MatchedRecord matchedRecord = new MatchedRecord();
        //matchedRecord.putFieldLine(ReceiptField.Chain, matchedChain.getMatchedOnLine(), chain.getCode());
        matchedRecord.matchToBranch(receipt, branch);
        matchedRecord.matchToHeader(receipt, parser.getStoreConfig(), parser);

        // parse items
        List<Item> items = parseItem(matchedRecord, receipt, parser);

        return ParsedReceipt.builder().branch(branch).items(items).fieldToValueMap(matchedRecord.getFieldToValueLine()).build();
    }

    private List<Item> parseItem(final MatchedRecord matchedRecord, final ReceiptData receipt, final StoreParser parser) throws Exception {
        final int stopLine = Math.min(matchedRecord.itemStopLineNumber(), receipt.getReceiptLines().size());
        return
            receipt.lines()
                   .filter( line -> !matchedRecord.isFieldLine(line.getNumber()))
                   .filter( line -> line.getNumber() < stopLine)
                   .filter( line ->
                       !ListCommon.matchList(parser.getStoreConfig().getSkipBefore(), line.getCleanText(), parser.getStoreConfig().similarityThresholdOfTwoStrings())
                   )
                   .map( line -> parser.parseItemLine(line.getCleanText()))
                   .filter( item -> item != null)
                   .collect(Collectors.toList())
                   ;
        // TODO stop if match skipAfter strings
    }

}
