package com.openprice.parser.store.rcss;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.ReceiptDataImpl;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.store.AbstractStoreParserSelector;

@Service
public class RCSSSelector extends AbstractStoreParserSelector {
    private RCSS1 rcss1;
    private final static String RCSS="RCSS";

    @Inject
    public RCSSSelector(final ChainRegistry chainRegistry) {
        super(chainRegistry);
    }

    @Override
    public StoreParser selectParser(final ReceiptDataImpl receipt) {
        // just one parser now. TODO based on receipt data to return parser
        return rcss1;
    }

    @Override
    protected String getParserBaseCode() {
        return RCSS;
    }

    @Override
    protected void generateParser() {
        StoreConfigImpl config = loadParserConfig(RCSS+"1");
        rcss1 = new RCSS1(config, loadPriceParserWithCatalog());
    }

}
