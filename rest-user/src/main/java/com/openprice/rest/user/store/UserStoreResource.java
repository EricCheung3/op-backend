package com.openprice.rest.user.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.store.Store;

public class UserStoreResource extends Resource<Store> {

    public static final String LINK_NAME_USER = "user";
    public static final String LINK_NAME_ITEMS = "items";
    public static final String LINK_NAME_ITEM = "item";

    public UserStoreResource(Store store) {
        super(store);
    }

}
