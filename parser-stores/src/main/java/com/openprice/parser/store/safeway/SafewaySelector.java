package com.openprice.parser.store.safeway;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.ReceiptData;
import com.openprice.parser.api.StoreParser;
import com.openprice.parser.store.AbstractStoreParserSelector;
import com.openprice.store.StoreMetadata;

@Service
public class SafewaySelector extends AbstractStoreParserSelector {
    private Safeway1 safeway1;
    private static final String SAFEWAY="Safeway";

    @Inject
    public SafewaySelector(final ChainRegistry chainRegistry, final StoreMetadata metadata) {
        super(chainRegistry, metadata);
    }

    @Override
    public StoreParser selectParser(final ReceiptData receipt) {
        // just one parser now. TODO based on receipt data to return parser
        return safeway1;
    }

    @Override
    protected String getParserBaseCode() {
        return SAFEWAY;
    }

    @Override
    protected void generateParser() {
        StoreConfigImpl config = loadParserConfig(SAFEWAY+"1");
        safeway1 = new Safeway1(config, loadPriceParserWithCatalog());
    }

}
