package com.openprice.rest.user.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.shopping.ShoppingItemRepository;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;
import com.openprice.store.StoreChain;
import com.openprice.store.StoreMetadata;

import lombok.Getter;
import lombok.Setter;

public class ShoppingStoreResource extends Resource<ShoppingStore> {

    @Getter @Setter
    private String icon = "generic";

    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty("_embedded")
    @Getter @Setter
    private Map<String, List<ShoppingItemResource>> embeddedItems = new HashMap<String, List<ShoppingItemResource>>();

    public ShoppingStoreResource(ShoppingStore store) {
        super(store);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ShoppingStore, ShoppingStoreResource>, UserApiUrls {

        private final StoreMetadata storeMetadata;
        private final ShoppingItemRepository shoppingItemRepository;
        private final ShoppingItemResource.Assembler itemResourceAssembler;

        @Inject
        public Assembler(final StoreMetadata storeMetadata,
                         final ShoppingItemRepository shoppingItemRepository,
                         final ShoppingItemResource.Assembler itemResourceAssembler) {
            this.storeMetadata = storeMetadata;
            this.itemResourceAssembler = itemResourceAssembler;
            this.shoppingItemRepository = shoppingItemRepository;
        }

        @Override
        public ShoppingStoreResource toResource(final ShoppingStore store) {
            final String[] pairs = {"storeId", store.getId()};
            final ShoppingStoreResource resource = new ShoppingStoreResource(store);
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_USER_SHOPPING_STORES_STORE, false, pairs)
                       .addLink("user", URL_USER, false, pairs)
                       .addLink("items", URL_USER_SHOPPING_STORES_STORE_ITEMS, true, pairs)
                       .addLink("item", URL_USER_SHOPPING_STORES_STORE_ITEMS_ITEM, false, pairs)
                       .addLink("catalogs", URL_USER_SHOPPING_STORES_STORE_CATALOGS, false, pairs)
                       ;

            List<ShoppingItemResource> items = new ArrayList<>();
            for (ShoppingItem item : shoppingItemRepository.findByStoreOrderByName(store)) {
                items.add(itemResourceAssembler.toResource(item));
            }
            resource.getEmbeddedItems().put("shoppingItems", items);

            final String chainCode = store.getChainCode();
            if (!StringUtils.isEmpty(chainCode)) {
                final StoreChain chain = storeMetadata.getStoreChainByCode(chainCode);
                if (chain != null) {
                    resource.setIcon(chain.getIcon());
                }
            }

            return resource;
        }

    }
}
