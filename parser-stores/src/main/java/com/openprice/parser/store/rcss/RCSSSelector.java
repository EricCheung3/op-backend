package com.openprice.parser.store.rcss;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ReceiptData;
import com.openprice.parser.store.AbstractStoreParserSelector;
import com.openprice.parser.store.ChainRegistry;
import com.openprice.parser.store.StoreConfig;
import com.openprice.parser.store.StoreParser;
import com.openprice.parser.store.rcss.rcss1.RCSS1;

@Service
public class RCSSSelector extends AbstractStoreParserSelector {
    private RCSS1 rcss1;

    @Inject
    public RCSSSelector(final ChainRegistry chainRegistry) {
        super(chainRegistry);
    }

    @Override
    public StoreParser selectParser(final ReceiptData receipt) {
        // just one parser now. TODO based on receipt data to return parser
        return rcss1;
    }

    @Override
    protected String getParserBaseCode() {
       return "RCSS";
    }

    @Override
    protected void generateParser() {
        StoreConfig config = loadParserConfig("RCSS1");
        rcss1 = new RCSS1(config, chain);
    }
}
