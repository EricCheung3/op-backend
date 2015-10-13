package com.openprice.parser.store.rcss;

import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.store.AbstractStoreParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RCSS1 extends AbstractStoreParser {

    public RCSS1(final StoreConfig config,
                 final PriceParserWithCatalog priceParserWithCatalog) {
        super(config, priceParserWithCatalog);
    }

    @Override
    public String parseField(ReceiptField field, ReceiptData receipt, int lineNumber) {

        switch (field) {
        case Card:
            return getValueAtTail(receipt.getLine(lineNumber).getCleanText(), "");

        case GstAmount:
            return parseItemPrice(receipt.getLine(lineNumber).getCleanText(), config.priceTail());

        case SubTotal:
            return parseItemPrice(receipt.getLine(lineNumber).getCleanText(), config.priceTail());

        case Total:
            return parseTotal(receipt.getLine(lineNumber).getCleanText());

        default:
            return receipt.getLine(lineNumber).getCleanText();
        }
    }


}
