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
import com.openprice.parser.StoreParser;
import com.openprice.parser.StoreParserSelector;
import com.openprice.parser.common.ListCommon;
import com.openprice.parser.common.StringCommon;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;

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
        final ChainRegistry.MatchedChain matchedChain = chainRegistry.findChain(receipt);
        if (matchedChain == null) {
            log.warn("Cannot find matching store chain!");
            return null;
        }

        final StoreChain chain = matchedChain.getChain();
        // get store branch
        final StoreBranch branch = chain.matchBranchByScoreSum(receipt);

        // get store parser
        final StoreParserSelector selector = chain.getSelector();
        final StoreParser parser = selector.selectParser(receipt);

        // match fields
        final MatchedRecord matchedRecord = new MatchedRecord();
        matchedRecord.putFieldLine(ReceiptField.Chain, matchedChain.getMatchedOnLine(), chain.getCode());
        matchedRecord.matchToBranch(receipt, branch);
        matchedRecord.matchToHeader(receipt, parser.getStoreConfig(), parser);

        // parse items
        List<Item> items = parseItem(matchedRecord, receipt, parser);

        return ParsedReceipt.builder().branch(branch).items(items).fieldToValueMap(matchedRecord.getFieldToValueLine()).build();
    }

    private List<Item> parseItem(final MatchedRecord matchedRecord, final ReceiptData receipt, final StoreParser parser) throws Exception {
        final List<Item> items = new ArrayList<Item>();
        final int stopLine = matchedRecord.itemStopLine();
        if (stopLine >= 0 && stopLine < receipt.getReceiptLines().size())
            log.debug("\n@@@@@  last field line is " + stopLine + ", content is " + receipt.getLine(stopLine) );

        System.out.println(matchedRecord);

        for (int i = 0; i < receipt.getReceiptLines().size(); i++) {
            if (matchedRecord.isFieldLine(i))
                continue;

            // stop if this is the last field line
            if (stopLine >= 0 && i >= stopLine)
                break;

            String name = receipt.getLine(i).getCleanText();
            String lower = name.toLowerCase();

            if (ListCommon.matchList(parser.getStoreConfig().getSkipBefore(), name, parser.getStoreConfig().similarityThresholdOfTwoStrings())) {
                log.debug("skipping " + name + ", becasue it is in skipBefore");
                continue;
            }
//
//            if (ParserUtils.containsSubString(config.getSkipSubstring(), name))
//                continue;

            if (ListCommon.matchList(parser.getStoreConfig().getSkipAfter(), name, parser.getStoreConfig().similarityThresholdOfTwoStrings())) {
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

            Item item = parser.parseItemLine(receipt.getLine(i).getCleanText());
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

}
