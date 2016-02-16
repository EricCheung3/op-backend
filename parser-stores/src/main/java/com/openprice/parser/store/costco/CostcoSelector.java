package com.openprice.parser.store.costco;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.store.AbstractStoreParserSelector;
import com.openprice.store.StoreMetadata;

@Service
public class CostcoSelector extends AbstractStoreParserSelector {
    private Costco1 parser;
    private static final String SAFEWAY="Safeway";

    @Inject
    public CostcoSelector(final ChainRegistry chainRegistry, final StoreMetadata metadata) {
        super(chainRegistry, metadata);
    }

    @Override
    public StoreParser selectParser(final ReceiptData receipt) {
        // just one parser now. TODO based on receipt data to return parser
        return parser;
    }

    @Override
    protected String getParserBaseCode() {
        return SAFEWAY;
    }

    @Override
    protected void generateParser() {
        StoreConfigImpl config = loadParserConfig(SAFEWAY+"1");
        parser = new Costco1(config, loadPriceParserWithCatalog());
    }

}
