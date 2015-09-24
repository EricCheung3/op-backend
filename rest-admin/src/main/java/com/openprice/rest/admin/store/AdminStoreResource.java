package com.openprice.rest.admin.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.store.Store;

public class AdminStoreResource extends Resource<Store> {
    public static final String LINK_NAME_BRANCHES = "branches";
    public static final String LINK_NAME_BRANCH = "branch";

    public AdminStoreResource(final Store store) {
        super(store);
    }

}
