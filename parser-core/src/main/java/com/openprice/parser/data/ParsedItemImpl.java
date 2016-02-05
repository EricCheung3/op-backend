package com.openprice.parser.data;

import com.openprice.parser.ParsedItem;

import lombok.Data;

@Data
public class ParsedItemImpl implements ParsedItem{

    private final String parsedName;
    private final String parsedBuyPrice;
    private final String catalogCode;
    private final int lineNumber;

    public ParsedItemImpl(final String name, final String price, final String code, final int line){
        this.parsedName=name;
        this.parsedBuyPrice=price;
        this.catalogCode=code;
        this.lineNumber=line;
    }
}
