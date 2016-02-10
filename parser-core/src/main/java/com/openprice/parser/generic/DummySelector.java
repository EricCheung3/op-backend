package com.openprice.parser.generic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.Product;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.api.StoreParserSelector;
import com.openprice.parser.price.PriceParserWithCatalog;


/**
 * Dummy in the sense that it should never be used for data operations.
 */
public class DummySelector implements StoreParserSelector{

    @Override
    public StoreParser selectParser(ReceiptData receipt) {
        return new GenericParser(StoreConfigImpl.fromPropCategorySkipBeforeAfterBlack(
                new Properties(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>()),
                PriceParserWithCatalog.withCatalog(new HashSet<Product>())
                );
    }

}
