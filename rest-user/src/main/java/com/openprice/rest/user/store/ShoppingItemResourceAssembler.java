package com.openprice.rest.user.store;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.store.Catalog;
import com.openprice.domain.store.CatalogRepository;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.user.UserApiUrls;

@Component
public class ShoppingItemResourceAssembler implements ResourceAssembler<ShoppingItem, ShoppingItemResource>, UserApiUrls {

    private final StoreChainRepository chainRepository;
    private final CatalogRepository catalogRepository;

    @Inject
    public ShoppingItemResourceAssembler(final StoreChainRepository chainRepository,
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
                final Catalog catalog = catalogRepository.findByChainAndCode(chain, shoppingItem.getCatalogCode());
                if (catalog != null) {
                    resource.setLabelCodes(catalog.getLabelCodes());
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
