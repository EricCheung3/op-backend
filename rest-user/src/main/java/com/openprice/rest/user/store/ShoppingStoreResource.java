package com.openprice.rest.user.store;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.openprice.domain.shopping.ShoppingStore;

import lombok.Getter;
import lombok.Setter;

public class ShoppingStoreResource extends Resource<ShoppingStore> {

    @Getter @Setter
    private List<ShoppingItemResource> items;

    public ShoppingStoreResource(ShoppingStore store) {
        super(store);
    }

}
