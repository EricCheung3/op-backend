package com.openprice.parser.generic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import com.openprice.common.StringCommon;
import com.openprice.common.TextResourceUtils;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceiptImpl;
import com.openprice.parser.ReceiptData;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.StoreChain;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.common.DateParserUtils;
import com.openprice.parser.data.Product;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.simple.MatchedRecord;
import com.openprice.parser.simple.SimpleParserUtils;
import com.openprice.parser.store.AbstractStoreParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericParser extends AbstractStoreParser{

    public GenericParser(final StoreConfig config,
            final PriceParserWithCatalog priceParserWithCatalog) {
        super(config, priceParserWithCatalog);

        // register field parsers
        fieldParsers.put(ReceiptFieldType.GstAmount,  line -> parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptFieldType.SubTotal,  line -> parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptFieldType.Total,  line -> parseTotal(line.getCleanText()));
        fieldParsers.put(ReceiptFieldType.Date,  line -> parseDate(line));
    }

    public static GenericParser selectParser(ReceiptData receipt) {
        List<String> blackList=null;
        try{
            blackList=TextResourceUtils.loadStringArray(ConfigFiles.blackListFile("Generic"));
        }catch(Exception e){
            log.warn("cannot load "+ConfigFiles.CATALOG_BLACK_LIST_FILE_NAME);
            blackList=new ArrayList<String>();
        }
        final Properties prop = new Properties();
        try{
            prop.load(StoreParser.class.getResourceAsStream(ConfigFiles.headerFile("Generic", "Generic1")));
        }catch (IOException ex) {
            log.warn("Cannot load config.properties for Generic store chain!");
        }

        final StoreConfig config=StoreConfig.fromPropCategorySkipBeforeAfterBlack(
                prop,
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                blackList);
        return new GenericParser(config, PriceParserWithCatalog.withCatalog(new HashSet<Product>()));
    }

    public static ParsedReceiptImpl parse(final StoreChain genericChain, final ReceiptData receipt)
        throws Exception{
        final GenericParser generic=selectParser(receipt);
        // match fields
        final MatchedRecord matchedRecord = new MatchedRecord();
        matchedRecord.matchToHeader(receipt, generic.getStoreConfig(), generic);

        //globally finding the date string
        if (matchedRecord.getFieldToValueLine().get(ReceiptFieldType.Date) == null ||
                matchedRecord.getFieldToValueLine().get(ReceiptFieldType.Date).getValue().isEmpty()){
            log.debug("date header not found: searching date string globally.");
            final ValueLine dateVL=DateParserUtils.findDateStringAfterLine(receipt.getOriginalLines(), 0);
            matchedRecord.putFieldLine(ReceiptFieldType.Date, dateVL);
        }

        // parse items
        List<ParsedItem> items = SimpleParserUtils.parseItems(matchedRecord, receipt, generic);
        return ParsedReceiptImpl.fromChainItemsMapBranch(genericChain, items, matchedRecord.getFieldToValueLine(), StringCommon.EMPTY);
    }



}
