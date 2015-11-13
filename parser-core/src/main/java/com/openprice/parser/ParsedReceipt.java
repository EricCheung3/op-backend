package com.openprice.parser;

import java.util.List;
import java.util.Map;

import com.openprice.parser.data.Item;
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ParsedReceipt {
    StoreChain chain;
    StoreBranch branch;
    List<Item> items;
    Map<ReceiptField, ValueLine> fieldToValueMap;
}
