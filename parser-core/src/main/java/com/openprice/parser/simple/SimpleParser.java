package com.openprice.parser.simple;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreBranch;
import com.openprice.parser.StoreChain;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.StoreParserSelector;
import com.openprice.parser.common.ListCommon;
import com.openprice.parser.common.ParserUtils;
import com.openprice.parser.common.StringCommon;
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
        final StoreChain chain = chainRegistry.findChain(receipt);
        if (chain == null) {
            log.warn("Cannot find matching store chain!");
            return null;
        }

        // get store branch
        final StoreBranch branch = chain.matchBranchByScoreSum(receipt);

        // get store parser
        final StoreParserSelector selector = chain.getSelector();
        final StoreParser parser = selector.selectParser(receipt);

        // match fields
        final MatchedRecord matchedRecord = new MatchedRecord();
        matchedRecord.matchToBranch(receipt, branch);
        matchedRecord.matchToHeader(receipt, parser.getStoreConfig(), parser);

        // parse items
        List<Item> items = parseItem(matchedRecord, receipt, parser.getStoreConfig());

        return ParsedReceipt.builder().branch(branch).items(items).build();
    }

    private List<Item> parseItem(final MatchedRecord matchedRecord, final ReceiptData receipt, final StoreConfig config) throws Exception {
        final List<Item> items = new ArrayList<Item>();
        final int stopLine = matchedRecord.itemStopLine();
        if (stopLine >= 0 && stopLine < receipt.getReceiptLines().size())
            log.debug("\n@@@@@  last field line is " + stopLine + ", content is " + receipt.getLine(stopLine) );

        for (int i = 0; i < receipt.getReceiptLines().size(); i++) {
            if (matchedRecord.isFieldLine(i))
                continue;

            // stop if this is the last field line
            if (stopLine >= 0 && i >= stopLine)
                break;

            String name = receipt.getLine(i).getCleanText();
            String lower = name.toLowerCase();

            if (ListCommon.matchList(config.getSkipBefore(), name, config.similarityThresholdOfTwoStrings())) {
                log.debug("skipping " + name + ", becasue it is in skipBefore");
                continue;
            }
//
//            if (ParserUtils.containsSubString(config.getSkipSubstring(), name))
//                continue;

            if (ListCommon.matchList(config.getSkipAfter(), name, config.similarityThresholdOfTwoStrings())) {
                log.debug("skipping " + name + " becasue it is in skipAfter");
                log.debug("!!!!!!!itemsFinished=true!!! matched skip After");
                break;
            }

            // stop items when reaching the total line
            if (StringCommon.stringMatchesHead(lower, "total", 0.6)) {
                break;
            }

            if (StringCommon.stringMatchesHead(lower, "subtotal", 0.6)) {
                break;
            }

            if (ParserUtils.isItem(name))
                items.add(new Item(name, i));
        }
        return items;
    }

}
