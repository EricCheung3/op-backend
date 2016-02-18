package com.openprice.parser.store;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.openprice.common.StringCommon;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.api.ReceiptLine;
import com.openprice.parser.api.StoreConfig;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.data.ParsedItemImpl;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.price.ProductPrice;

public abstract class AbstractStoreParser implements StoreParser {
    protected final StoreConfig config;
    protected final PriceParserWithCatalog priceParserWithCatalog;
    protected final Map<ReceiptFieldType, Function<ReceiptLine, String>> fieldParsers = new HashMap<>();

    public AbstractStoreParser(final StoreConfig config,
            final PriceParserWithCatalog priceParserWithCatalog) {
        this.config = config;
        this.priceParserWithCatalog = priceParserWithCatalog;
    }

    @Override
    public StoreConfig getStoreConfig() {
        return config;
    }

    @Override
    public final String parseField(final ReceiptFieldType field, final ReceiptLine line) {
        Function<ReceiptLine, String> fieldParser = fieldParsers.get(field);
        //if no field parser for this field, then return the original line text
        String parsedValue=StringCommon.EMPTY;
        if (fieldParser == null) {
            parsedValue = line.getCleanText();
        } else {
            parsedValue = fieldParser.apply(line);
        }
        return parsedValue;
    }

    @Override
    public ParsedItem parseItemLine(final String lineString, int lineNumber) {
        if (priceParserWithCatalog == null)
            return ParsedItemImpl.fromNameOnly(lineString);
        final ProductPrice pPrice = priceParserWithCatalog.parsePriceLine(lineString);
        if(pPrice==null || pPrice.getName()==null || pPrice.getName().isEmpty())
            return null;
        return ParsedItemImpl.fromProductPriceLineNumber(pPrice, lineNumber);
    }
}
