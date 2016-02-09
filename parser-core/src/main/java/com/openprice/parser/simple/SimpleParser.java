package com.openprice.parser.simple;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ParsedReceiptImpl;
import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.ReceiptParser;
import com.openprice.parser.StoreChainUtils;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.api.StoreParserSelector;
import com.openprice.parser.common.DateParserUtils;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.generic.CheapParser;
import com.openprice.parser.generic.GenericChains;
import com.openprice.parser.generic.GenericParser;
import com.openprice.store.StoreBranch;
import com.openprice.store.StoreChain;
import com.openprice.store.StoreMetadata;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SimpleParser implements ReceiptParser {

    private final ChainRegistry chainRegistry;

    private final StoreMetadata metadata;

    @Inject
    public SimpleParser(final ChainRegistry chainRegistry, final StoreMetadata metadata) {
        this.chainRegistry = chainRegistry;
        this.metadata=metadata;
    }

    @Override
    public ParsedReceipt parseReceiptOcrResult(final List<String> ocrTextList) {
        try{
            final ReceiptDataImpl receipt = ReceiptDataImpl.fromOCRResults(ocrTextList);
            if (receipt.getReceiptLines().size() == 0) {
                log.warn("No receipt data to parse.");
                return null;
            }
            return parseReceiptData(receipt);
        }
        catch(Exception ex){
            return null;
        }
    }

    private ParsedReceipt parseReceiptData(final ReceiptDataImpl receipt) throws Exception {
        // find chain first
        final StoreChain chain = chainRegistry.findBestMatchedChain(receipt);
        if (chain == null) {
            log.warn("No specific parser for this chain yet!");
            try{
                //TODO
                final GenericChains chains = new GenericChains("/config/Generic/chain.list");

                final String genericChainCode = chains.findChain(receipt.getOriginalLines()).toLowerCase();
                log.info("genericChainCode="+genericChainCode);
                return GenericParser.parse(StoreChain.genericChainWithOnlyCode(genericChainCode), receipt);
            } catch(Exception ex) {
                log.warn("exception in calling generic parser: {}. now call cheapParser!", ex.getMessage());
                return new CheapParser().parse(receipt.getOriginalLines());
            }
        }
        log.info("Parse receipt and find matcing chain {}", chain.getCode());

        // get store branch
        final StoreBranch branch = StoreChainUtils.matchBranchByScoreSum(chain, receipt);
        if (branch != null) {
            log.info("Parser find matching branch {}.", branch.getName());
        }

        // get store parser
        final StoreParserSelector selector = chain.getSelector();
        final StoreParser parser = selector.selectParser(receipt);

        // match fields
        final MatchedRecord matchedRecord = new MatchedRecord();
        matchedRecord.matchToBranch(receipt, branch);
        matchedRecord.matchToHeader(receipt, parser.getStoreConfig(), parser);

        //globally finding the date string
        if (matchedRecord.getFieldToValueLine().get(ReceiptFieldType.Date) == null ||
                matchedRecord.getFieldToValueLine().get(ReceiptFieldType.Date).getValue().isEmpty()){
            log.debug("date header not found: searching date string globally.");
            final ValueLine dateVL=DateParserUtils.findDateStringAfterLine(receipt.getOriginalLines(), 0);
            matchedRecord.putFieldLine(ReceiptFieldType.Date, dateVL);
        }

        // parse items
        List<ParsedItem> items = SimpleParserUtils.parseItems(matchedRecord, receipt, parser);
        return ParsedReceiptImpl.fromChainItemsMapBranch(chain, items, matchedRecord.getFieldToValueLine(), branch.getName());
    }

    public ParsedReceipt parseLines(final List<String> lines) throws Exception {
        final ReceiptDataImpl receipt = ReceiptDataImpl.fromContentLines(lines);
        return parseReceiptData(receipt);
    }

}
