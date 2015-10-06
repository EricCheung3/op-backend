package com.openprice.parser.store;

import lombok.Builder;
import lombok.Data;

/**
 * a class of chain
 *
 * Chain.build() Chain.id(id value).build()
 *
 */
@Data
@Builder
public class MatchedChain {

    // matched StoreChain
    private final StoreChain chain;
    private final String matchedField;
    private final double matchedScore;
    private final int matchedOnLine;
}