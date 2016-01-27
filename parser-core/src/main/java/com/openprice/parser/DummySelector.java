package com.openprice.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import com.openprice.parser.data.Product;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.parser.simple.Generic1;


/**
 * Dummy in the sense that it should never be used for data operations.
 */
public class DummySelector implements StoreParserSelector{

    @Override
    public StoreParser selectParser(ReceiptData receipt) {
        // TODO Auto-generated method stub
        return new Generic1(StoreConfig.fromPropCategorySkipBeforeAfterBlack(
                new Properties(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>()),
                PriceParserWithCatalog.withCatalog(new HashSet<Product>())
                );
    }

}
