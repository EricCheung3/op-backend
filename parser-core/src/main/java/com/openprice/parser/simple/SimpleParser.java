package com.openprice.parser.simple;

import java.util.HashSet;
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
import com.openprice.parser.api.Product;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.api.StoreParserSelector;
import com.openprice.parser.data.FoundChainAt;
import com.openprice.parser.data.StoreChainFound;
import com.openprice.parser.data.StringInt;
import com.openprice.parser.date.DateParserUtils;
import com.openprice.parser.generic.CheapParser;
import com.openprice.parser.generic.GenericParser;
import com.openprice.parser.price.PriceParserWithCatalog;
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
    public SimpleParser(final ChainRegistry chainRegistry, StoreMetadata metadata) {
        this.chainRegistry = chainRegistry;
        this.metadata = metadata;
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

    /*
     *  find chains from two kinds of chains: specialized chain for which we have parser; generic chains from store-data.json
     */
    private ParsedReceipt parseReceiptData(final ReceiptData receipt) throws Exception {
        final StoreChainFound parserChainFound = chainRegistry.findParserChain(receipt);
        final StoreChainFound genericChainFound = ChainRegistry.findBestMatchedChain(receipt, metadata.allStoreChains());

        final StoreChain parserChain = (parserChainFound == null)? null : parserChainFound.getChain();
        if (parserChainFound == null) {
            log.debug("No specific store parser for this receipt yet!");
        } else {
            log.debug("ChainRegistry: find matching chain {}, at {} at line {}.", parserChain.getCode(), parserChainFound.getFoundAt(), parserChainFound.getLineNumber());
        }

        final StoreChain genericChain = genericChainFound == null? null: genericChainFound.getChain();
        if(genericChain == null){
            log.info("Generic chains: no chain found");
        }

        if (parserChain == null ||
                (parserChainFound.getFoundAt() != FoundChainAt.BEGIN &&
                genericChainFound.getFoundAt() == FoundChainAt.BEGIN)) {
            if (parserChain != null) {
                log.info("With ChainRegistry, the chain code was found at the end. We decide to trust generic chain which is found in the beginning. ");
            }
            try{
                String genericCode = null;
                if(genericChain != null)
                    genericCode = genericChain.getCode();
                log.debug("genericChainCode=" + genericCode);
                final StoreConfig storeConfig = GenericParser.fromGenericCode(genericCode, metadata);
                final GenericParser genericParser = new GenericParser(storeConfig, PriceParserWithCatalog.withCatalog(new HashSet<Product>()));//selectParser(receipt);
                return applyParser(genericParser, receipt, genericChain, null);
            } catch(Exception ex) {
                ex.printStackTrace();//for debugging.
                log.info("exception in calling generic parser: {}. now call cheapParser!", ex.getMessage());
                return new CheapParser().parseReceiptOcrResult(receipt.getOriginalLines());
            }
        }
        final StoreBranch branch = StoreChainUtils.matchBranchByScoreSum(parserChain, receipt);
        if (branch != null) {
            log.debug("Parser find matching branch {}.", branch.getName());
        }
        final StoreParserSelector selector = chainRegistry.getParserSelector(parserChain.getCode());
        final StoreParser parser = selector.selectParser(receipt);
        return applyParser(parser, receipt, parserChain, branch);
    }

    /**
     * apply a parser to a receipt to get parsed result.
     * @param parser
     * @param receipt
     * @param chain
     * @param branch
     * @return
     * @throws Exception
     */
    public static ParsedReceipt applyParser(
            final StoreParser parser,
            final ReceiptData receipt,
            final StoreChain chain,
            final StoreBranch branch) throws Exception{
        final MatchedRecord record = new MatchedRecordImpl();
        final MatchFields match=new MatchFieldsImpl();

        if(branch != null)
            match.matchToBranch(record, receipt, branch);
        match.matchToHeaders(record, receipt, parser);

        //globally finding the date string
        if (record.valueOfField(ReceiptFieldType.Date) == null ||
                record.valueOfField(ReceiptFieldType.Date).getValue().isEmpty()) {
            log.debug("date header not found: searching date string globally.");
            final StringInt dateVL=DateParserUtils.findDate(receipt.getOriginalLines(), 0);
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
