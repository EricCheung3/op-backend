package com.openprice.parser.data;

import com.openprice.common.StringCommon;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.price.ProductPrice;

import lombok.Data;

@Data
public class ParsedItemImpl implements ParsedItem{

    private final String parsedName;
    private final String parsedBuyPrice;
    private final String catalogCode;
    private final int lineNumber;

    private ParsedItemImpl(
            final String name,
            final String price,
            final String code,
            final int line){
        this.parsedName=name;
        this.parsedBuyPrice=price;
        this.catalogCode=code;
        this.lineNumber=line;
    }

    public static ParsedItemImpl fromNamePriceCodeLine(
            final String name,
            final String price,
            final String code,
            final int line){
        return new ParsedItemImpl(name, price, code, line);
    }

    public static ParsedItemImpl fromNameOnly(final String lineString){
        return new ParsedItemImpl(lineString, StringCommon.EMPTY, StringCommon.EMPTY, -1);
    }

    public static ParsedItemImpl emptyItem(){
        return fromNameOnly(StringCommon.EMPTY);
    }

    //TODO linenumber?
    public static ParsedItemImpl fromProductPrice(final ProductPrice pPrice){
        return new ParsedItemImpl(
                pPrice.getName(),
                pPrice.getPrice(),
                pPrice.toCatalogCode(),
                -1);
    }

//    @Override
//    public String getCatalogCode(){
//        return "";
//    }
}
