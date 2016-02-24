package com.openprice.parser.store.walmart;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.store.AbstractStoreParser;
import com.openprice.parser.store.FieldParserCommon;

public class Walmart1 extends AbstractStoreParser {

    public Walmart1(final StoreConfigImpl config,
            final PriceParserWithCatalog priceParserWithCatalog) {
        super(config, priceParserWithCatalog);

        // register field parsers
        fieldParsers.put(ReceiptFieldType.GstAmount,  line -> FieldParserCommon.parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptFieldType.SubTotal,  line -> FieldParserCommon.parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptFieldType.Total,  line -> FieldParserCommon.parseTotal(line.getCleanText()));
        fieldParsers.put(ReceiptFieldType.Date,  line -> FieldParserCommon.parseDate(line));
        fieldParsers.put(ReceiptFieldType.TotalSold,  line -> FieldParserCommon.parseTotalSold(line));
    }

}
