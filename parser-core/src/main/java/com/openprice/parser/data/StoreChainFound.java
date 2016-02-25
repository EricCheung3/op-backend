package com.openprice.parser.data;

import com.openprice.store.StoreChain;

import lombok.Getter;

public class StoreChainFound {

    @Getter
    private final StoreChain chain;
    @Getter
    private final FoundChainAt foundAt; //0 means found at beginning; 1 means found at middle, 2 means found at end

    public StoreChainFound(final StoreChain c, final FoundChainAt position){
        this.chain = c;
        this.foundAt = position;
    }
}
