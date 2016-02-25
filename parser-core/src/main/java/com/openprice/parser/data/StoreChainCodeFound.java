package com.openprice.parser.data;

import lombok.Getter;

public class StoreChainCodeFound {

    @Getter
    private final String chainCode;
    @Getter
    private final FoundChainAt foundAt;

    public StoreChainCodeFound(final String code, final FoundChainAt at){
        this.chainCode = code;
        this.foundAt = at;
    }
}
