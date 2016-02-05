package com.openprice.parser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.openprice.parser.data.ParsedFieldImpl;
import com.openprice.parser.data.ValueLine;

public class ParsedReceiptImpl implements ParsedReceipt{
    final StoreChain chain;
    final List<ParsedItem> items;
    final Map<ReceiptFieldType, ValueLine> fieldToValueMap;

    StoreBranch branch=StoreBranch.EmptyStoreBranch();

    private ParsedReceiptImpl(final StoreChain chain, final List<ParsedItem> items, final  Map<ReceiptFieldType, ValueLine> map){
        this.chain=chain;
        this.items=items;
        this.fieldToValueMap=map;
    }

    private ParsedReceiptImpl(final StoreChain chain, final List<ParsedItem> items, final  Map<ReceiptFieldType, ValueLine> map, final StoreBranch branch){
        this(chain, items, map);
        this.branch=branch;
    }

    public static ParsedReceiptImpl fromChainItemsMapBranch(final StoreChain chain, final List<ParsedItem> items, final  Map<ReceiptFieldType, ValueLine> map, final StoreBranch branch){
        return new ParsedReceiptImpl(chain, items, map, branch);
    }

    @Override
    public String getChainCode() {
        return chain.getCode();
    }

    @Override
    public Map<ReceiptFieldType, ParsedField> getFields() {
        return fieldToValueMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e->e.getKey(),
                        e -> new ParsedFieldImpl(
                                e.getKey(),
                                e.getValue().getValue(),
                                e.getValue().getLine()
                                )));
    }

    @Override
    public List<ParsedItem> getItems() {
        return items;
    }
}
