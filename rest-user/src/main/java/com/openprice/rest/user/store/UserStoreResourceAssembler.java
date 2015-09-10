package com.openprice.rest.user.store;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.store.Store;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.UserAccountRestController;

@Component
public class UserStoreResourceAssembler implements ResourceAssembler<Store, UserStoreResource> {

    @Override
    public UserStoreResource toResource(final Store store) {
        final UserStoreResource resource = new UserStoreResource(store);

        resource.add(linkTo(methodOn(UserStoreRestController.class).getUserStoreById(store.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(UserAccountRestController.class).getCurrentUserAccount())
                .withRel(UserStoreResource.LINK_NAME_USER));

        resource.add(linkTo(methodOn(ShoppingItemRestController.class).getStoreShoppingItems(store.getId(), null, null))
                .withRel(UserStoreResource.LINK_NAME_ITEMS));

        // Hack solution to build template links for "item".
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link itemLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/stores/"+ store.getId()+ "/items/{itemId}"),
                UserStoreResource.LINK_NAME_ITEM);
        resource.add(itemLink);

        return resource;
    }

}