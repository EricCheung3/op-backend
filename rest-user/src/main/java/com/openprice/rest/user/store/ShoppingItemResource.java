package com.openprice.rest.user.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.store.Catalog;

import lombok.Getter;
import lombok.Setter;

public class ShoppingItemResource extends Resource<ShoppingItem> {

    @Getter @Setter
    private Catalog catalog;

    public ShoppingItemResource(final ShoppingItem shoppingItem) {
        super(shoppingItem);
    }
}
