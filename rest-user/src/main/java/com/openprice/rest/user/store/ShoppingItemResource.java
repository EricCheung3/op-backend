package com.openprice.rest.user.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.shopping.ShoppingItem;

public class ShoppingItemResource extends Resource<ShoppingItem> {

    public static final String LINK_NAME_USER = "user";
    public static final String LINK_NAME_STORE = "store";

    public ShoppingItemResource(final ShoppingItem shoppingItem) {
        super(shoppingItem);
    }
}
