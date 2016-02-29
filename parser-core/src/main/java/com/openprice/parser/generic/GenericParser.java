package com.openprice.parser.generic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import com.openprice.common.StringCommon;
import com.openprice.common.TextResourceUtils;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ParsedReceiptImpl;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.MatchFields;
import com.openprice.parser.api.MatchedRecord;
import com.openprice.parser.api.Product;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.data.StringInt;
import com.openprice.parser.date.DateParserUtils;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.simple.MatchFieldsImpl;
import com.openprice.parser.simple.MatchedRecordImpl;
import com.openprice.parser.simple.SimpleParserUtils;
import com.openprice.parser.store.AbstractStoreParser;
import com.openprice.parser.store.FieldParserCommon;
import com.openprice.store.StoreChain;
import com.openprice.store.StoreMetadata;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenericParser extends AbstractStoreParser{

    public GenericParser(final StoreConfig config,
            final PriceParserWithCatalog priceParserWithCatalog) {
        super(config, priceParserWithCatalog);

        // register field parsers
        fieldParsers.put(ReceiptFieldType.GstAmount,  line -> FieldParserCommon.parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptFieldType.SubTotal,  line -> FieldParserCommon.parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptFieldType.Total,  line -> FieldParserCommon.parseTotal(line.getCleanText()));
        fieldParsers.put(ReceiptFieldType.Date,  line -> FieldParserCommon.parseDate(line));
        fieldParsers.put(ReceiptFieldType.TotalSold,  line -> FieldParserCommon.parseTotalSold(line));
    }


    /**
     * generate StoreConfig from store chain code
     * @param genericCode
     * @return
     */
    public static StoreConfig fromGenericCode(final String genericCode, final StoreMetadata metadata){
        List<String> blackList=null;
        final Properties prop = new Properties();

        log.debug("metadata="+metadata);
        log.debug("metadata.getStoreChainMap().size() = "+metadata.getStoreChainMap().size());
        log.debug("genericCode="+genericCode);
        System.out.println("metadata.getStoreChainByCode(genericCode)="+metadata.getStoreChainByCode(genericCode).toString());

        //generate black list file first from the chain folder if there is; otherwise use a default one
        try{
            blackList = metadata.getStoreChainByCode(genericCode).getNotCatalogItemNames();
        } catch (Exception e1){
            log.warn("customized blackList file is not availabe at meta data folder "+ genericCode);
            final String blackListFileName="/config/Generic/not-catalog-item-names.txt";
            try{
                blackList=TextResourceUtils.loadStringArray(blackListFileName);
            }catch(Exception e2){
                log.warn("cannot load "+blackListFileName);
                blackList=new ArrayList<String>();
            }
        }

        try{
            prop.putAll(metadata.getStoreChainByCode(genericCode).getHeaderProperties());
        } catch(Exception e){
            log.warn("customized header file is not availabe at meta data folder "+ genericCode);
            final String headersFile="/config/Generic/Generic1/headerConfig.properties";
            try{
                final Properties header = new Properties();
                header.load(StoreParser.class.getResourceAsStream(headersFile));
                prop.putAll(header);
            } catch (IOException e3) {
                log.warn("Cannot load " + headersFile+" for Generic store chain!");
            }
        }

        try{
            prop.putAll(metadata.getStoreChainByCode(genericCode).getNonHeaderProperties());
        } catch(Exception e){
            log.warn("customized non-headerheader file is not availabe at meta data folder "+ genericCode);
            final String nonHeadersFile="/config/Generic/config.properties";
            try{
                final Properties nonHeader = new Properties();
                nonHeader.load(StoreParser.class.getResourceAsStream(nonHeadersFile));
                prop.putAll(nonHeader);
            } catch (IOException e3) {
                log.warn("Cannot load " + nonHeadersFile+" for Generic store chain!");
            }
        }

        return StoreConfigImpl.fromPropCategorySkipBeforeAfterBlack(
                prop,
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                blackList);
    }

    public static ParsedReceipt parse(final String genericChainCode, final StoreConfig config, final ReceiptData receipt)
        throws Exception{
        final GenericParser generic = new GenericParser(config,PriceParserWithCatalog.withCatalog(new HashSet<Product>()));//selectParser(receipt);
        // match fields
        final MatchedRecord record = new MatchedRecordImpl();
        final MatchFields matching = new MatchFieldsImpl();
        log.debug("before matching to header");
        matching.matchToHeaders(record, receipt, generic);

        //globally finding the date string
        if (record.valueOfField(ReceiptFieldType.Date) == null ||
                record.valueOfField(ReceiptFieldType.Date).getValue().isEmpty()){
            log.debug("date header not found: searching date string globally.");
            final StringInt dateVL=DateParserUtils.findDate(receipt.getOriginalLines(), 0);
            record.putFieldLineValue(ReceiptFieldType.Date, dateVL.getLine(), dateVL.getValue());
        }

        // parse items
        log.debug("before parse items");
        List<ParsedItem> items = SimpleParserUtils.parseItems(record, receipt, generic);
        final StoreChain genericChain = StoreChain.genericChainWithOnlyCode(genericChainCode);
        return ParsedReceiptImpl.fromChainItemsMapBranch(genericChain, items, record.getFieldToValueLine(), StringCommon.EMPTY);
    }

}
