package com.openprice.parser.data;

import lombok.Getter;

public class StoreChainCodeFound {

    @Getter
    private final String chainCode;
    @Getter
    private final FoundChainAt foundAt;
    @Getter
    private final int lineNumber;

    public StoreChainCodeFound(final String code, final FoundChainAt at, final int line){
        this.chainCode = code;
        this.foundAt = at;
        this.lineNumber = line;
    }
}
