package com.openprice.rest.admin.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.store.StoreChain;

public class AdminStoreChainResource extends Resource<StoreChain> {

    public static final String LINK_NAME_BRANCHES = "branches";
    public static final String LINK_NAME_BRANCH = "branch";

    public AdminStoreChainResource(final StoreChain chain) {
        super(chain);
    }

}
