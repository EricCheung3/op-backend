package com.openprice.rest.user.store;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;
import com.openprice.store.CatalogProduct;
import com.openprice.store.StoreChain;
import com.openprice.store.StoreMetadata;

import lombok.Getter;
import lombok.Setter;

public class ShoppingItemResource extends Resource<ShoppingItem> {

    @Getter @Setter
    private CatalogProduct catalog;

    public ShoppingItemResource(final ShoppingItem shoppingItem) {
        super(shoppingItem);
    }

    @Component
    public static class Assembler implements ResourceAssembler<ShoppingItem, ShoppingItemResource>, UserApiUrls {

        private final StoreMetadata storeMetadata;

        @Inject
        public Assembler(final StoreMetadata storeMetadata) {
            this.storeMetadata = storeMetadata;
        }

        @Override
        public ShoppingItemResource toResource(final ShoppingItem shoppingItem) {
            final String[] pairs = {"storeId", shoppingItem.getStore().getId(),
                                    "itemId", shoppingItem.getId()};
            final ShoppingItemResource resource = new ShoppingItemResource(shoppingItem);

            if (StringUtils.hasText(shoppingItem.getCatalogCode())) {
                final StoreChain storeChain = storeMetadata.getStoreChainByCode(shoppingItem.getStore().getChainCode());
                if (storeChain != null) {
                    final CatalogProduct catalog = storeChain.getCatalogProductByCode(shoppingItem.getCatalogCode());
                    if (catalog != null) {
                        resource.setCatalog(catalog);
                    }
                }
            }

            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_USER_SHOPPING_STORES_STORE_ITEMS_ITEM, false, pairs)
                       .addLink("user", URL_USER, false, pairs)
                       .addLink("store", URL_USER_SHOPPING_STORES_STORE, false, pairs)
                       ;
            return resource;
        }

    }

}
