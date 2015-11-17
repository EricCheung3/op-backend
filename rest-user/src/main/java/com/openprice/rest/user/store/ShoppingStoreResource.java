package com.openprice.rest.user.store;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.openprice.domain.shopping.ShoppingStore;

import lombok.Getter;
import lombok.Setter;

public class ShoppingStoreResource extends Resource<ShoppingStore> {

    public static final String LINK_NAME_USER = "user";
    public static final String LINK_NAME_STORE = "store";
    public static final String LINK_NAME_ITEMS = "items";
    public static final String LINK_NAME_ITEM = "item";

    @Getter @Setter
    private List<ShoppingItemResource> items;

    public ShoppingStoreResource(ShoppingStore store) {
        super(store);
    }

}
