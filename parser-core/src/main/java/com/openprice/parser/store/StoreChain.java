package com.openprice.parser.store;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * In memory store chain info, like code, categories, identify fields, etc.
 *
 */
@Data
@Builder
public class StoreChain {
    private final String code;
    private final String categories;
    private final String identifyFields;
    private final List<StoreBranch> branches;
    private final StoreParserSelector selector;
}
