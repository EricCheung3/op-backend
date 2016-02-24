package com.openprice.parser.store.rona;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.store.AbstractStoreParserSelector;
import com.openprice.store.StoreMetadata;

@Service
public class RonaSelector extends AbstractStoreParserSelector {
    private Rona1 parser;
    private static final String CODE="Safeway";

    @Inject
    public RonaSelector(final ChainRegistry chainRegistry, final StoreMetadata metadata) {
        super(chainRegistry, metadata);
    }

    @Override
    public StoreParser selectParser(final ReceiptData receipt) {
        // just one parser now. TODO based on receipt data to return parser
        return parser;
    }

    @Override
    protected String getParserBaseCode() {
        return CODE;
    }

    @Override
    protected void generateParser() {
        StoreConfigImpl config = loadParserConfig(CODE+"1");
        parser = new Rona1(config, loadPriceParserWithCatalog());
    }

}
