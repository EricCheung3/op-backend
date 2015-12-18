package com.openprice.rest.user.store;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.store.CatalogProduct;
import com.openprice.domain.store.CatalogRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

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

        private final StoreChainRepository chainRepository;
        private final CatalogRepository catalogRepository;

        @Inject
        public Assembler(final StoreChainRepository chainRepository,
                         final CatalogRepository catalogRepository) {
            this.chainRepository = chainRepository;
            this.catalogRepository = catalogRepository;
        }

        @Override
        public ShoppingItemResource toResource(final ShoppingItem shoppingItem) {
            final String[] pairs = {"storeId", shoppingItem.getStore().getId(),
                                    "itemId", shoppingItem.getId()};
            final ShoppingItemResource resource = new ShoppingItemResource(shoppingItem);

            if (StringUtils.hasText(shoppingItem.getCatalogCode())) {
                final StoreChain chain = chainRepository.findByCode(shoppingItem.getStore().getChainCode());
                if (chain != null) {
                    final CatalogProduct catalog = catalogRepository.findByChainAndCatalogCode(chain, shoppingItem.getCatalogCode());
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
