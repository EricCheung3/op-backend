package com.openprice.parser.simple;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ReceiptData;
import com.openprice.parser.cheapParser.CheapParser;
import com.openprice.parser.data.Item;
import com.openprice.parser.store.ChainRecognizer;
import com.openprice.parser.store.ChainRegistry;
import com.openprice.parser.store.MatchedRecord;
import com.openprice.parser.store.ParsedReceipt;
import com.openprice.parser.store.StoreBranch;
import com.openprice.parser.store.StoreChain;
import com.openprice.parser.store.StoreParser;
import com.openprice.parser.store.StoreParserSelector;

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
        ReceiptData receipt = ReceiptData.fromContentLines(lines);

        // find chain first
        final ChainRecognizer recognizer = new ChainRecognizer(this.chainRegistry);
        StoreChain chain = recognizer.findChain(lines);

        // get store branch
        StoreBranch branch = chain.matchBranchByScoreSum(receipt);

        // get store parser
        StoreParserSelector selector = chain.getSelector();
        StoreParser parser = selector.selectParser(receipt);

        // match fields
        MatchedRecord matchedRecord = new MatchedRecord();
        matchedRecord.matchToBranch(receipt, branch);
        matchedRecord.matchToHeader(receipt, parser.getStoreConfig(), parser);

        CheapParser receiptParser = new CheapParser(receipt, matchedRecord, parser.getStoreConfig().getSkip());
        List<Item> items = receiptParser.parseGeneral();

        return ParsedReceipt.builder().branch(branch).items(items).build();
    }
}
