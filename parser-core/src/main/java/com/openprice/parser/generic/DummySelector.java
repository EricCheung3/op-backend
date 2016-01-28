package com.openprice.parser.generic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.StoreParserSelector;
import com.openprice.parser.data.Product;
import com.openprice.parser.price.PriceParserWithCatalog;


/**
 * Dummy in the sense that it should never be used for data operations.
 */
public class DummySelector implements StoreParserSelector{

    @Override
    public StoreParser selectParser(ReceiptData receipt) {
        return new GenericParser(StoreConfig.fromPropCategorySkipBeforeAfterBlack(
                new Properties(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>()),
                PriceParserWithCatalog.withCatalog(new HashSet<Product>())
                );
    }

}
