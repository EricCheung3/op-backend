package com.openprice.rest.admin.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.rest.UtilConstants;

@Component
public class AdminUserAccountResourceAssembler implements ResourceAssembler<UserAccount, AdminUserAccountResource> {

    @Override
    public AdminUserAccountResource toResource(final UserAccount userAccount) {
        final AdminUserAccountResource resource = new AdminUserAccountResource(userAccount);

        resource.add(linkTo(methodOn(AdminUserAccountRestController.class).getUserAccountByUserId(userAccount.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(AdminUserAccountRestController.class).changeUserLockStatus(userAccount.getId(), null))
                .withRel(AdminUserAccountResource.LINK_NAME_LOCK_STATE));

        resource.add(linkTo(methodOn(AdminUserAccountRestController.class).getUserProfile(userAccount.getId()))
                .withRel(AdminUserAccountResource.LINK_NAME_PROFILE));

        // Hack solution to build template links.
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link receiptsLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/users/" + userAccount.getId() + "/receipts" + UtilConstants.PAGINATION_TEMPLATES),
                AdminUserAccountResource.LINK_NAME_RECEIPTS);
        resource.add(receiptsLink);

        final Link receiptLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/users/" + userAccount.getId() + "/receipts/{receiptId}"),
                AdminUserAccountResource.LINK_NAME_RECEIPT);
        resource.add(receiptLink);

        return resource;
    }
}
