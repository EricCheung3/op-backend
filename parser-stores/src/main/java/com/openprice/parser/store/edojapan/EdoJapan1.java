package com.openprice.parser.store.edojapan;

import com.openprice.parser.StoreConfig;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.store.AbstractStoreParser;

public class EdoJapan1 extends AbstractStoreParser {

    public EdoJapan1(final StoreConfig config,
            final PriceParserWithCatalog priceParserWithCatalog) {
        super(config, priceParserWithCatalog);

        // register field parsers
        fieldParsers.put(ReceiptField.GstAmount,  line -> parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptField.SubTotal,  line -> parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptField.Total,  line -> parseTotal(line.getCleanText()));
        fieldParsers.put(ReceiptField.Date,  line -> parseDate(line));
    }

}
