package com.openprice.parser.store.edojapan;

import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.store.AbstractStoreParser;
import com.openprice.parser.store.FieldParserCommon;

public class EdoJapan1 extends AbstractStoreParser {

    public EdoJapan1(final StoreConfigImpl config,
            final PriceParserWithCatalog priceParserWithCatalog) {
        super(config, priceParserWithCatalog);

        // register field parsers
        fieldParsers.put(ReceiptFieldType.GstAmount,  line -> FieldParserCommon.parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptFieldType.SubTotal,  line -> FieldParserCommon.parseItemPrice(line.getCleanText(), config.priceTail()));
        fieldParsers.put(ReceiptFieldType.Total,  line -> FieldParserCommon.parseTotal(line.getCleanText()));
        fieldParsers.put(ReceiptFieldType.Date,  line -> FieldParserCommon.parseDate(line));
    }

}
