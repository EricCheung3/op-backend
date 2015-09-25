package com.openprice.rest.user.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.store.StoreChain;

public class UserStoreResource extends Resource<StoreChain> {

    public static final String LINK_NAME_USER = "user";
    public static final String LINK_NAME_ITEMS = "items";
    public static final String LINK_NAME_ITEM = "item";

    public UserStoreResource(StoreChain store) {
        super(store);
    }

}
