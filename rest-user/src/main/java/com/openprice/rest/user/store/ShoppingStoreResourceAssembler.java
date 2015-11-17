package com.openprice.rest.user.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.shopping.ShoppingItem;
import com.openprice.domain.shopping.ShoppingStore;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.UserAccountRestController;

@Component
public class ShoppingStoreResourceAssembler implements ResourceAssembler<ShoppingStore, ShoppingStoreResource> {

    private final ShoppingItemResourceAssembler itemResourceAssembler;

    @Inject
    public ShoppingStoreResourceAssembler(final ShoppingItemResourceAssembler itemResourceAssembler) {
        this.itemResourceAssembler = itemResourceAssembler;
    }

    @Override
    public ShoppingStoreResource toResource(final ShoppingStore store) {
        final ShoppingStoreResource resource = new ShoppingStoreResource(store);

        resource.add(linkTo(methodOn(ShoppingStoreRestController.class).getUserShoppingStoreById(store.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(UserAccountRestController.class).getCurrentUserAccount())
                .withRel(ShoppingStoreResource.LINK_NAME_USER));

        // Hack solution to build template links.
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link itemsLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/stores/"+ store.getId()+ "/items" + UtilConstants.PAGINATION_TEMPLATES),
                ShoppingStoreResource.LINK_NAME_ITEMS);
        resource.add(itemsLink);

        final Link itemLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/stores/"+ store.getId()+ "/items/{itemId}"),
                ShoppingStoreResource.LINK_NAME_ITEM);
        resource.add(itemLink);

        // TODO fix _embedded issue
        List<ShoppingItemResource> items = new ArrayList<>();
        for (ShoppingItem item : store.getItems()) {
            items.add(itemResourceAssembler.toResource(item));
        }
        resource.setItems(items);

        return resource;
    }

}