package com.openprice.parser.data;

import com.openprice.store.StoreChain;

import lombok.Getter;

public class StoreChainFound {

    @Getter
    private final StoreChain chain;
    @Getter
    private final FoundChainAt foundAt;

    public StoreChainFound(final StoreChain c, final FoundChainAt position){
        this.chain = c;
        this.foundAt = position;
    }
}
