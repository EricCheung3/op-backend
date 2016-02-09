package com.openprice.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.openprice.parser.data.ParsedFieldImpl;
import com.openprice.parser.data.ValueLine;
import com.openprice.store.StoreChain;

public class ParsedReceiptImpl implements ParsedReceipt{
    final StoreChain chain;
    final List<ParsedItem> items;
    final Map<ReceiptFieldType, ValueLine> fieldToValueMap;
    final String branchName;

    private ParsedReceiptImpl(final StoreChain chain, final List<ParsedItem> items, final  Map<ReceiptFieldType, ValueLine> map, final String branchName){
        this.chain=chain;
        this.items=items;
        this.fieldToValueMap=map;
        this.branchName=branchName;
    }

    public static ParsedReceiptImpl fromChainItemsMapBranch(final StoreChain chain, final List<ParsedItem> items, final  Map<ReceiptFieldType, ValueLine> map, final String branch){
        return new ParsedReceiptImpl(chain, items, map, branch);
    }

    @Override
    public String getChainCode() {
        if(chain.getCode()==null || chain.getCode().isEmpty())
            return null;
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
        if(items==null)
            return new ArrayList<ParsedItem>();
        return items;
    }

    @Override
    public String getBranchName() {
        return branchName;
    }
}
