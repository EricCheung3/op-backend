package com.openprice.parser.store;

public abstract class AbstractStoreParser implements StoreParser {
    private final StoreConfig config;
    private final StoreChain chain;

    public AbstractStoreParser(final StoreConfig config,
                               final StoreChain chain) {
        this.config = config;
        this.chain = chain;
    }

    @Override
    public StoreConfig getStoreConfig() {
        // TODO Auto-generated method stub
        return config;
    }

}
