package com.openprice.rest.admin.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

@Component
public class AdminUserAccountResourceAssembler implements ResourceAssembler<UserAccount, AdminUserAccountResource>, AdminApiUrls {

    public static final String LINK_NAME_LOCK_STATE = "lockState";
    public static final String LINK_NAME_PROFILE = "profile";
    public static final String LINK_NAME_RECEIPTS = "receipts";
    public static final String LINK_NAME_RECEIPT = "receipt";

    @Override
    public AdminUserAccountResource toResource(final UserAccount userAccount) {
        final AdminUserAccountResource resource = new AdminUserAccountResource(userAccount);
        final String[] pairs = {"userId", userAccount.getId()};
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_USERS_USER, false, pairs)
                   .addLink(LINK_NAME_LOCK_STATE, URL_ADMIN_USERS_USER_LOCK_STATE, false, pairs)
                   .addLink(LINK_NAME_PROFILE, URL_ADMIN_USERS_USER_PROFILE, false, pairs)
                   .addLink(LINK_NAME_RECEIPTS, URL_ADMIN_USERS_USER_RECEIPTS, true, pairs)
                   .addLink(LINK_NAME_RECEIPT, URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT, false, pairs)
                   ;
        return resource;
    }
}
