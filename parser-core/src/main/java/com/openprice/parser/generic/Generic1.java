package com.openprice.parser.generic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreBranch;
import com.openprice.parser.StoreChain;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.common.DateParserUtils;
import com.openprice.parser.common.TextResourceUtils;
import com.openprice.parser.data.Item;
import com.openprice.parser.data.Product;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.simple.MatchedRecord;
import com.openprice.parser.simple.SimpleParser;
import com.openprice.parser.store.AbstractStoreParser;
import com.openprice.parser.store.ConfigFiles;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Generic1 extends AbstractStoreParser {

    public Generic1(final StoreConfig config,
            final PriceParserWithCatalog priceParserWithCatalog) {
        super(config, priceParserWithCatalog);

        // register field parsers
        fieldParsers.put(ReceiptField.GstAmount,  line -> parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptField.SubTotal,  line -> parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptField.Total,  line -> parseTotal(line.getCleanText()));
        fieldParsers.put(ReceiptField.Date,  line -> parseDate(line));
    }

    public static Generic1 selectParser(ReceiptData receipt) {
        List<String> blackList=null;
        try{
            blackList=TextResourceUtils.loadStringArray("/config/Generic/"+ConfigFiles.CATALOG_BLACK_LIST_FILE_NAME);
        }catch(Exception e){
            log.warn("cannot load "+ConfigFiles.CATALOG_BLACK_LIST_FILE_NAME);
            blackList=new ArrayList<String>();
        }
        final Properties prop = new Properties();
        try{
            prop.load(StoreParser.class.getResourceAsStream("/config/Generic/"+ConfigFiles.BASE_CONFIG_FILE_NAME));
        }catch (IOException ex) {
            log.warn("Cannot load config.properties for Generic store chain!");
        }

        final StoreConfig config=StoreConfig.fromPropCategorySkipBeforeAfterBlack(
                prop,
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                blackList);
        return new Generic1(config, PriceParserWithCatalog.withCatalog(new HashSet<Product>()));
    }

    public static ParsedReceipt parse(final StoreChain genericChain, final ReceiptData receipt)
        throws Exception{
        final Generic1 generic=selectParser(receipt);
        // match fields
        final MatchedRecord matchedRecord = new MatchedRecord();
        matchedRecord.matchToHeader(receipt, generic.getStoreConfig(), generic);

        //globally finding the date string
        if (matchedRecord.getFieldToValueLine().get(ReceiptField.Date) == null ||
                matchedRecord.getFieldToValueLine().get(ReceiptField.Date).getValue().isEmpty()){
            log.debug("date header not found: searching date string globally.");
            final ValueLine dateVL=DateParserUtils.findDateStringAfterLine(receipt.getOriginalLines(), 0);
            matchedRecord.putFieldLine(ReceiptField.Date, dateVL);
        }

        // parse items
        List<Item> items = SimpleParser.parseItem(matchedRecord, receipt, generic);

        return ParsedReceipt.fromChainItemsMapBranch(genericChain, items, matchedRecord.getFieldToValueLine(), StoreBranch.EmptyStoreBranch());
    }


}
