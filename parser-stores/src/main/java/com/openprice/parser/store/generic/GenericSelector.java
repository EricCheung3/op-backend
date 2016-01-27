package com.openprice.parser.store.generic;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.store.AbstractStoreParserSelector;

@Service
public class GenericSelector extends AbstractStoreParserSelector {
    private Generic1 generic1;

    private static final String GENERIC="Generic";

    @Inject
    public GenericSelector(final ChainRegistry chainRegistry) {
        super(chainRegistry);
    }

    @Override
    public StoreParser selectParser(final ReceiptData receipt) {
        // just one parser now. TODO based on receipt data to return parser
        return generic1;
    }

    @Override
    protected String getParserBaseCode() {
        return GENERIC;
    }

    @Override
    protected void generateParser() {
        StoreConfig config = loadParserConfig(GENERIC+"1");
        generic1 = new Generic1(config, loadPriceParserWithCatalog());
    }

}
