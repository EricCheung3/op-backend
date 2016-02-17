package com.openprice.parser.simple;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.common.StringCommon;
import com.openprice.parser.ChainRegistry;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ParsedReceiptImpl;
import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.ReceiptParser;
import com.openprice.parser.StoreChainUtils;
import com.openprice.parser.api.MatchFields;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.api.StoreParserSelector;
import com.openprice.parser.common.DateParserUtils;
import com.openprice.parser.data.StringInt;
import com.openprice.parser.generic.CheapParser;
import com.openprice.parser.generic.GenericChains;
import com.openprice.parser.generic.GenericParser;
import com.openprice.store.StoreBranch;
import com.openprice.store.StoreChain;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SimpleParser implements ReceiptParser {

    private final ChainRegistry chainRegistry;

    @Inject
    public SimpleParser(final ChainRegistry chainRegistry) {
        this.chainRegistry = chainRegistry;
    }

    @Override
    public ParsedReceipt parseReceiptOcrResult(final List<String> ocrTextList) {
        try{
            final ReceiptData receipt = ReceiptDataImpl.fromOCRResults(ocrTextList);
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

    private ParsedReceipt parseReceiptData(final ReceiptData receipt) throws Exception {
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
                return new CheapParser().parseReceiptOcrResult(receipt.getOriginalLines());
            }
        }
        log.info("Parse receipt and find matcing chain {}", chain.getCode());

        // get store branch
        final StoreBranch branch = StoreChainUtils.matchBranchByScoreSum(chain, receipt);
        if (branch != null) {
            log.info("Parser find matching branch {}.", branch.getName());
        }
        log.debug("branch="+branch);

        // get store parser
        final StoreParserSelector selector = chainRegistry.getParserSelector(chain.getCode());
        final StoreParser parser = selector.selectParser(receipt);

        // matching fields and record the results
        final MatchedRecord record = new MatchedRecordImpl();
        final MatchFields match=new MatchFieldsImpl();

        if(branch != null)
            match.matchToBranch(record, receipt, branch);
        match.matchToHeaders(record, receipt, parser.getStoreConfig(), parser);

        //globally finding the date string
        if (record.valueOfField(ReceiptFieldType.Date) == null ||
                record.valueOfField(ReceiptFieldType.Date).getValue().isEmpty()){
            log.debug("date header not found: searching date string globally.");
            final StringInt dateVL=DateParserUtils.findDateStringAfterLine(receipt.getOriginalLines(), 0);
            record.putFieldLineValue(ReceiptFieldType.Date, dateVL.getLine(), dateVL.getValue());
        }

        // parse items
        List<ParsedItem> items = SimpleParserUtils.parseItems(record, receipt, parser);
        if(branch!=null)
            return ParsedReceiptImpl.fromChainItemsMapBranch(chain, items, record.getFieldToValueLine(), branch.getName());
        return ParsedReceiptImpl.fromChainItemsMapBranch(chain, items, record.getFieldToValueLine(), StringCommon.EMPTY);
    }

    public ParsedReceipt parseLines(final List<String> lines) throws Exception {
        final ReceiptData receipt = ReceiptDataImpl.fromContentLines(lines);
        return parseReceiptData(receipt);
    }

}
