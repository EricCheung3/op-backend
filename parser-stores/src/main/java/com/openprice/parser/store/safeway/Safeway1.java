package com.openprice.parser.store.safeway;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.store.AbstractStoreParser;

public class Safeway1 extends AbstractStoreParser {

    public Safeway1(final StoreConfigImpl config,
            final PriceParserWithCatalog priceParserWithCatalog) {
        super(config, priceParserWithCatalog);

        // register field parsers
        fieldParsers.put(ReceiptFieldType.GstAmount,  line -> parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptFieldType.SubTotal,  line -> parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptFieldType.Total,  line -> parseTotal(line.getCleanText()));
        fieldParsers.put(ReceiptFieldType.Date,  line -> parseDate(line));
    }
}
