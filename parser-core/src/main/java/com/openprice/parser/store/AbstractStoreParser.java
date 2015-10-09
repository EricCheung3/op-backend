package com.openprice.parser.store;

import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;

public abstract class AbstractStoreParser implements StoreParser {
    private final StoreConfig config;

    public AbstractStoreParser(final StoreConfig config) {
        this.config = config;
    }

    @Override
    public StoreConfig getStoreConfig() {
        return config;
    }

}
