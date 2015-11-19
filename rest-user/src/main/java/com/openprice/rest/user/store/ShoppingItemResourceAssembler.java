package com.openprice.rest.user.store;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

@Component
public class ShoppingItemResourceAssembler implements ResourceAssembler<ShoppingItem, ShoppingItemResource>, UserApiUrls {

    public static final String LINK_NAME_USER = "user";
    public static final String LINK_NAME_STORE = "store";

    @Override
    public ShoppingItemResource toResource(final ShoppingItem shoppingItem) {
        final String[] pairs = {"storeId", shoppingItem.getStore().getId(), "itemId", shoppingItem.getId()};
        final ShoppingItemResource resource = new ShoppingItemResource(shoppingItem);
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_USER_SHOPPING_STORES_STORE_ITEMS_ITEM, false, pairs)
                   .addLink(LINK_NAME_USER, URL_USER, false, pairs)
                   .addLink(LINK_NAME_STORE, URL_USER_SHOPPING_STORES_STORE, false, pairs)
                   ;
        return resource;
    }

}
