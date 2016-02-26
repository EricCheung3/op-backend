package com.openprice.parser.data;

import com.openprice.store.StoreChain;

import lombok.Getter;

public class StoreChainFound {

    @Getter
    private final StoreChain chain;
    @Getter
    private final FoundChainAt foundAt;
    @Getter
    private final int lineNumber;

    public StoreChainFound(final StoreChain c, final FoundChainAt position, final int line){
        this.chain = c;
        this.foundAt = position;
        this.lineNumber = line;
    }
}
