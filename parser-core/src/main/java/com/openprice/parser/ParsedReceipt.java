package com.openprice.parser;

import java.util.List;

import com.openprice.parser.data.Item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParsedReceipt {
    private final StoreBranch branch;
    private final List<Item> items;
}
