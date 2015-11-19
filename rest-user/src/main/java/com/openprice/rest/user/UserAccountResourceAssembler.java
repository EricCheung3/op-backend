package com.openprice.rest.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.rest.UtilConstants;

@Component
public class UserAccountResourceAssembler implements ResourceAssembler<UserAccount, UserAccountResource> {

    @Override
    public UserAccountResource toResource(final UserAccount userAccount) {
        final UserAccountResource resource = new UserAccountResource(userAccount);

        // Hack solution to build template links
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();

        final Link selfLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + UserApiUrls.URL_USER), Link.REL_SELF);
        resource.add(selfLink);

        final Link profileLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + UserApiUrls.URL_USER_PROFILE), UserAccountResource.LINK_NAME_PROFILE);
        resource.add(profileLink);

        final Link uploadLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + UserApiUrls.URL_USER_RECEIPTS_UPLOAD), UserAccountResource.LINK_NAME_UPLOAD);
        resource.add(uploadLink);

        final Link shoppinglistLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + UserApiUrls.URL_USER_SHOPPINGLIST), UserAccountResource.LINK_NAME_SHOPPING_LIST);
        resource.add(shoppinglistLink);

        final Link receiptsLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + UserApiUrls.URL_USER_RECEIPTS + UtilConstants.PAGINATION_TEMPLATES),
                UserAccountResource.LINK_NAME_RECEIPTS);
        resource.add(receiptsLink);

        final Link receiptLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + UserApiUrls.URL_USER_RECEIPTS_RECEIPT),
                UserAccountResource.LINK_NAME_RECEIPT);
        resource.add(receiptLink);

        final Link storesLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + UserApiUrls.URL_USER_SHOPPING_STORES + UtilConstants.PAGINATION_TEMPLATES),
                UserAccountResource.LINK_NAME_STORES);
        resource.add(storesLink);

        final Link storeLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + UserApiUrls.URL_USER_SHOPPING_STORES_STORE),
                UserAccountResource.LINK_NAME_STORE);
        resource.add(storeLink);

        return resource;
    }

}
