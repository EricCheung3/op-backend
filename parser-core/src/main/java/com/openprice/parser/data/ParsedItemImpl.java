package com.openprice.parser.data;

import com.openprice.common.StringCommon;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.price.ProductPrice;

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

    public static ParsedItemImpl fromProductPriceLineNumber(final ProductPrice pPrice, final int lineNumber){
        return new ParsedItemImpl(
                    pPrice.getName(),
                    pPrice.getPrice(),
                    pPrice.toCatalogCode(),
                    lineNumber);
    }

    @Override
    public String getParsedName() {
        if(parsedName==null || parsedName.isEmpty())
            return null;
        return parsedName;
    }

    @Override
    public String getParsedBuyPrice() {
        if(parsedBuyPrice==null || parsedBuyPrice.isEmpty())
            return null;
        return parsedBuyPrice;
    }

    @Override
    public String getCatalogCode() {
        if(catalogCode==null || catalogCode.isEmpty())
            return null;
        return catalogCode;
    }

    @Override
    public int getLineNumber() {
        if(lineNumber<0)
            return -1;
        return lineNumber;
    }

}
