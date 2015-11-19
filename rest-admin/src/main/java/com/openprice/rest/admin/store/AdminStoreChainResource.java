package com.openprice.rest.admin.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.store.StoreChain;

public class AdminStoreChainResource extends Resource<StoreChain> {

    public AdminStoreChainResource(final StoreChain chain) {
        super(chain);
    }

}
