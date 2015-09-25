package com.openprice.rest.admin;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.admin.AdminAccount;
import com.openprice.rest.UtilConstants;

@Component
public class AdminAccountResourceAssembler implements ResourceAssembler<AdminAccount, AdminAccountResource> {

    @Override
    public AdminAccountResource toResource(final AdminAccount adminAccount) {
        final AdminAccountResource resource = new AdminAccountResource(adminAccount);

        resource.add(linkTo(methodOn(AdminAccountRestController.class).getAdminAccount()).withSelfRel());

        // Hack solution to build template links.
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link usersLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS + UtilConstants.PAGINATION_TEMPLATES),
                AdminAccountResource.LINK_NAME_USERS);
        resource.add(usersLink);

        final Link userLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS_USER),
                AdminAccountResource.LINK_NAME_USER);
        resource.add(userLink);

        final Link receiptsLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_RECEIPTS + UtilConstants.PAGINATION_TEMPLATES),
                AdminAccountResource.LINK_NAME_RECEIPTS);
        resource.add(receiptsLink);

        final Link receiptLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_RECEIPTS_RECEIPT),
                AdminAccountResource.LINK_NAME_RECEIPT);
        resource.add(receiptLink);

        final Link storesLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_STORES + UtilConstants.PAGINATION_TEMPLATES),
                AdminAccountResource.LINK_NAME_STORES);
        resource.add(storesLink);

        final Link storeLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_STORES_STORE),
                AdminAccountResource.LINK_NAME_STORE);
        resource.add(storeLink);

        return resource;
    }
}
