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
import com.openprice.parser.common.DateParserUtils;
import com.openprice.parser.common.ListCommon;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SimpleParser {
    private final ChainRegistry chainRegistry;

    @Inject
    public SimpleParser(final ChainRegistry chainRegistry) {
        this.chainRegistry = chainRegistry;
    }

    public ParsedReceipt parseOCRResults(final List<String> ocrTextList) throws Exception {
        final ReceiptData receipt = ReceiptData.fromOCRResults(ocrTextList);
        if (receipt.getReceiptLines().size() == 0) {
            log.warn("No receipt data to parse.");
            return null;
        }
        return parseReceiptData(receipt);
    }

    public ParsedReceipt parse(final List<String> lines) throws Exception {
        final ReceiptData receipt = ReceiptData.fromContentLines(lines);
        return parseReceiptData(receipt);
    }

    private ParsedReceipt parseReceiptData(final ReceiptData receipt) throws Exception {
        // find chain first
        final StoreChain chain = chainRegistry.findBestMatchedChain(receipt);
        if (chain == null) {
            log.warn("Cannot find matching store chain!");
            final GenericParser parser = new GenericParser();
            return parser.parse(receipt.getOriginalLines());
        }
        log.info("Parse receipt and find matcing chain {}", chain.getCode());

        // get store branch
        final StoreBranch branch = chain.matchBranchByScoreSum(receipt);
        if (branch != null) {
            log.info("Parser find matching branch {}.", branch.getBranchName());
        }

        // get store parser
        final StoreParserSelector selector = chain.getSelector();
        final StoreParser parser = selector.selectParser(receipt);

        // match fields
        final MatchedRecord matchedRecord = new MatchedRecord();
        matchedRecord.matchToBranch(receipt, branch);
        matchedRecord.matchToHeader(receipt, parser.getStoreConfig(), parser);

        //globally finding the date string
        if (matchedRecord.getFieldToValueLine().get(ReceiptField.Date) == null ||
                matchedRecord.getFieldToValueLine().get(ReceiptField.Date).getValue().isEmpty()){
            log.debug("date header not found: searching date string globally.");
            final ValueLine dateVL=DateParserUtils.findDateStringAfterLine(receipt.getOriginalLines(), 0);
            matchedRecord.putFieldLine(ReceiptField.Date, dateVL);
        }

        // parse items
        List<Item> items = parseItem(matchedRecord, receipt, parser);

        return ParsedReceipt.builder()
                .chain(chain)
                .branch(branch)
                .items(items)
                .fieldToValueMap(matchedRecord.getFieldToValueLine())
                .build();
    }

    private List<Item> parseItem(final MatchedRecord matchedRecord, final ReceiptData receipt, final StoreParser parser) throws Exception {
        final int stopLine = Math.min(matchedRecord.itemStopLineNumber(), receipt.getReceiptLines().size());
        log.debug("black list size is "+parser.getStoreConfig().getCatalogFilter().getBlackList().size());
        //        parser.getStoreConfig().getCatalogFilter().getBlackList().forEach(line->log.debug(line+"\n"));

        return
                receipt.lines()
                .filter( line -> !matchedRecord.isFieldLine(line.getNumber()))
                .filter( line -> line.getNumber() < stopLine)
                .filter( line ->
                !ListCommon.matchList(parser.getStoreConfig().getSkipBefore(), line.getCleanText(), parser.getStoreConfig().similarityThresholdOfTwoStrings())
                        )
                .map( line -> parser.parseItemLine(line.getCleanText()))
                .filter( item ->
                item != null &&
                !item.getProduct().getName().isEmpty() &&
                !parser.getStoreConfig().getCatalogFilter().matchesBlackList(item.getProduct().getName())
                        )
                .collect(Collectors.toList())
                ;
        // TODO stop if match skipAfter strings
    }

}
