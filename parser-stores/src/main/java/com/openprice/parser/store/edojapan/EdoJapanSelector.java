package com.openprice.parser.store.edojapan;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.ReceiptData;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.store.AbstractStoreParserSelector;

@Service
public class EdoJapanSelector extends AbstractStoreParserSelector {
    private EdoJapan1 edo1;

    private static final String EDO_JAPAN="EdoJapan";

    @Inject
    public EdoJapanSelector(final ChainRegistry chainRegistry) {
        super(chainRegistry);
    }

    @Override
    public StoreParser selectParser(final ReceiptData receipt) {
        // just one parser now. TODO based on receipt data to return parser
        return edo1;
    }

    @Override
    protected String getParserBaseCode() {
        return EDO_JAPAN;
    }

    @Override
    protected void generateParser() {
        StoreConfig config = loadParserConfig(EDO_JAPAN+"1");
        edo1 = new EdoJapan1(config, loadPriceParserWithCatalog());
    }

}
