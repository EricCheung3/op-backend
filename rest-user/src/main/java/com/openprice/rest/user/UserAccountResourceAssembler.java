package com.openprice.rest.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.UserAccount;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.common.UserAccountResource;
import com.openprice.rest.user.store.ShoppingItemRestController;
import com.openprice.rest.user.store.UserStoreRestController;


@Component
public class UserAccountResourceAssembler implements ResourceAssembler<UserAccount, UserAccountResource> {

    @Override
    public UserAccountResource toResource(final UserAccount userAccount) {
        final UserAccountResource resource = new UserAccountResource(userAccount);

        resource.add(linkTo(methodOn(UserAccountRestController.class).getCurrentUserAccount())
                .withSelfRel());

        resource.add(linkTo(methodOn(UserAccountRestController.class).getCurrentUserProfile())
                .withRel(UserAccountResource.LINK_NAME_PROFILE));

        resource.add(linkTo(methodOn(UserReceiptRestController.class).getCurrentUserReceipts(null, null))
                .withRel(UserAccountResource.LINK_NAME_RECEIPTS));

        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUploadNewReceiptPath())
                .withRel(UserAccountResource.LINK_NAME_UPLOAD));

        resource.add(linkTo(methodOn(UserStoreRestController.class).getCurrentUserStores(null, null))
                .withRel(UserAccountResource.LINK_NAME_STORES));

        resource.add(linkTo(methodOn(ShoppingItemRestController.class).getUploadShoppingListPath())
                .withRel(UserAccountResource.LINK_NAME_SHOPPING_LIST));

        // Hack solution to build template links for "receipt", "store". 
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link receiptLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + UserApiUrls.URL_USER_RECEIPTS_RECEIPT),
                UserAccountResource.LINK_NAME_RECEIPT);
        resource.add(receiptLink);

        final Link storeLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + UserApiUrls.URL_USER_SHOPPING_STORES_STORE),
                UserAccountResource.LINK_NAME_STORE);
        resource.add(storeLink);

        return resource;
    }

}
