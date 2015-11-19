package com.openprice.rest.user.store;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

@Component
public class ShoppingStoreResourceAssembler implements ResourceAssembler<ShoppingStore, ShoppingStoreResource>, UserApiUrls {

    public static final String LINK_NAME_USER = "user";
    public static final String LINK_NAME_STORE = "store";
    public static final String LINK_NAME_ITEMS = "items";
    public static final String LINK_NAME_ITEM = "item";

    private final ShoppingItemResourceAssembler itemResourceAssembler;

    @Inject
    public ShoppingStoreResourceAssembler(final ShoppingItemResourceAssembler itemResourceAssembler) {
        this.itemResourceAssembler = itemResourceAssembler;
    }

    @Override
    public ShoppingStoreResource toResource(final ShoppingStore store) {
        final String[] pairs = {"storeId", store.getId()};
        final ShoppingStoreResource resource = new ShoppingStoreResource(store);
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_USER_SHOPPING_STORES_STORE, false, pairs)
                   .addLink(LINK_NAME_USER, URL_USER, false, pairs)
                   .addLink(LINK_NAME_ITEMS, URL_USER_SHOPPING_STORES_STORE_ITEMS, true, pairs)
                   .addLink(LINK_NAME_ITEM, URL_USER_SHOPPING_STORES_STORE_ITEMS_ITEM, false, pairs)
                   ;

        // TODO fix _embedded issue
        List<ShoppingItemResource> items = new ArrayList<>();
        for (ShoppingItem item : store.getItems()) {
            items.add(itemResourceAssembler.toResource(item));
        }
        resource.setItems(items);

        return resource;
    }

}