package com.openprice.parser;

import java.util.List;
import java.util.Map;

import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;

import lombok.Data;

@Data
public class ParsedReceipt {
    final StoreChain chain;
    final List<Item> items;
    final Map<ReceiptField, ValueLine> fieldToValueMap;

    StoreBranch branch=StoreBranch.EmptyStoreBranch();

    private ParsedReceipt(final StoreChain chain, final List<Item> items, final  Map<ReceiptField, ValueLine> map){
        this.chain=chain;
        this.items=items;
        this.fieldToValueMap=map;
    }

    private ParsedReceipt(final StoreChain chain, final List<Item> items, final  Map<ReceiptField, ValueLine> map, final StoreBranch branch){
        this(chain, items, map);
        this.branch=branch;
    }

    public static ParsedReceipt fromChainItemsMapBranch(final StoreChain chain, final List<Item> items, final  Map<ReceiptField, ValueLine> map, final StoreBranch branch){
        return new ParsedReceipt(chain, items, map, branch);
    }
}
