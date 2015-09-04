package com.openprice.rest.user.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.rest.user.UserAccountRestController;

@Component
public class ShoppingItemResourceAssembler implements ResourceAssembler<ShoppingItem, ShoppingItemResource> {

    @Override
    public ShoppingItemResource toResource(final ShoppingItem shoppingItem) {
        final ShoppingItemResource resource = new ShoppingItemResource(shoppingItem);

        resource.add(linkTo(methodOn(ShoppingItemRestController.class).getStoreShoppingItemById(shoppingItem.getStore().getId(), shoppingItem.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(UserAccountRestController.class).getCurrentUserAccount())
                .withRel(ShoppingItemResource.LINK_NAME_USER));

        resource.add(linkTo(methodOn(UserStoreRestController.class).getUserStoreById(shoppingItem.getStore().getId()))
                .withRel(ShoppingItemResource.LINK_NAME_STORE));

        return resource;
    }

}
