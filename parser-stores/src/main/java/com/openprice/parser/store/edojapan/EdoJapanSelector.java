package com.openprice.parser.store.edojapan;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.store.AbstractStoreParserSelector;
import com.openprice.store.StoreMetadata;

@Service
public class EdoJapanSelector extends AbstractStoreParserSelector {
    private EdoJapan1 parser;

    private static final String EDO_JAPAN="EdoJapan";

    @Inject
    public EdoJapanSelector(final ChainRegistry chainRegistry, final StoreMetadata metadata) {
        super(chainRegistry, metadata);
    }

    @Override
    public StoreParser selectParser(final ReceiptData receipt) {
        // just one parser now. TODO based on receipt data to return parser
        return parser;
    }

    @Override
    protected String getParserBaseCode() {
        return EDO_JAPAN;
    }

    @Override
    protected void generateParser() {
        StoreConfigImpl config = loadParserConfig(EDO_JAPAN+"1");
        parser = new EdoJapan1(config, loadPriceParserWithCatalog());
    }

}
