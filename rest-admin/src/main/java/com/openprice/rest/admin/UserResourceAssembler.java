package com.openprice.rest.admin;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.UserAccount;
import com.openprice.rest.common.UserAccountResource;

@Component("admin_UserResourceAssembler")
public class UserResourceAssembler implements ResourceAssembler<UserAccount, UserAccountResource> {

    @Override
    public UserAccountResource toResource(final UserAccount userAccount) {
        final UserAccountResource resource = new UserAccountResource(userAccount);

        resource.add(linkTo(methodOn(UserRestController.class).getUserAccountByUserId(userAccount.getId()))
                .withSelfRel());

        resource.add(linkTo(methodOn(UserRestController.class).changeUserLockStatus(userAccount.getId(), null))
                .withRel(UserAccountResource.LINK_NAME_LOCK_STATE));
        
        resource.add(linkTo(methodOn(UserRestController.class).getUserProfile(userAccount.getId()))
                .withRel(UserAccountResource.LINK_NAME_PROFILE));
        
        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUserReceipts(userAccount.getId(), null, null))
                .withRel(UserAccountResource.LINK_NAME_RECEIPTS));

        // TODO find out how to add partial template url to hyperlinks
//        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUserReceiptById(userAccount.getId(), null))
//                .withRel(UserAccountResource.LINK_NAME_RECEIPT));

        resource.add(linkTo(methodOn(UserReceiptRestController.class).getUploadNewReceiptPath(userAccount.getId()))
                .withRel(UserAccountResource.LINK_NAME_UPLOAD));

        return resource;
    }
}
