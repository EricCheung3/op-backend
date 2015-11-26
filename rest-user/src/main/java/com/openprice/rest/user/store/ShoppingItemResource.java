package com.openprice.rest.user.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.shopping.ShoppingItem;

import lombok.Getter;
import lombok.Setter;

public class ShoppingItemResource extends Resource<ShoppingItem> {

    @Getter @Setter
    private String labelCodes; // labelCodes from catalog

    public ShoppingItemResource(final ShoppingItem shoppingItem) {
        super(shoppingItem);
    }
}
