package com.openprice.parser.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.Getter;

/**
 * Service to store all store chains in the system as registry. Each StoreParserSelector service will register
 * a StoreChain to this registry.
 *
 */
@Service
public class ChainRegistry {
    @Getter
    private final List<StoreChain> storeChains = new ArrayList<>();

    public List<StoreChain> addChain(StoreChain chain) {
        storeChains.add(chain);
        return storeChains;
    }
}
