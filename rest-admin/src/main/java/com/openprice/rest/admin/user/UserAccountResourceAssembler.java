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
import com.openprice.rest.admin.receipt.UserReceiptRestController;

@Component("admin_UserResourceAssembler")
public class UserAccountResourceAssembler implements ResourceAssembler<UserAccount, UserAccountResource> {

    @Override
    public UserAccountResource toResource(final UserAccount userAccount) {
        final UserAccountResource resource = new UserAccountResource(userAccount);

        resource.add(linkTo(methodOn(UserAccountRestController.class).getUserAccountByUserId(userAccount.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(UserAccountRestController.class).changeUserLockStatus(userAccount.getId(), null))
                .withRel(UserAccountResource.LINK_NAME_LOCK_STATE));

        resource.add(linkTo(methodOn(UserAccountRestController.class).getUserProfile(userAccount.getId()))
                .withRel(UserAccountResource.LINK_NAME_PROFILE));

        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUserReceipts(userAccount.getId(), null, null))
                .withRel(UserAccountResource.LINK_NAME_RECEIPTS));

        // Hack solution to build template links.
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link receiptLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/admin/users/" + userAccount.getId() + "/receipts/{receiptId}"),
                UserAccountResource.LINK_NAME_RECEIPT);
        resource.add(receiptLink);

        return resource;
    }
}
