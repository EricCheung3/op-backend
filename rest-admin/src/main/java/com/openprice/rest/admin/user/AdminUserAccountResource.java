package com.openprice.rest.admin.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

import lombok.Getter;
import lombok.Setter;

public class AdminUserAccountResource extends Resource<UserAccount> {

    @Getter @Setter
    private AdminUserProfileResource profile;

    public AdminUserAccountResource(final UserAccount account) {
        super(account);
        profile = new AdminUserProfileResource(account.getProfile());
    }

    @Component
    public static class Assembler implements ResourceAssembler<UserAccount, AdminUserAccountResource>, AdminApiUrls {

        @Override
        public AdminUserAccountResource toResource(final UserAccount userAccount) {
            final AdminUserAccountResource resource = new AdminUserAccountResource(userAccount);
            final String[] pairs = {"userId", userAccount.getId()};
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_USERS_USER, false, pairs)
                       .addLink("lockState", URL_ADMIN_USERS_USER_LOCK_STATE, false, pairs)
                       .addLink("profile", URL_ADMIN_USERS_USER_PROFILE, false, pairs)
                       .addLink("receipts", URL_ADMIN_USERS_USER_RECEIPTS, true, pairs)
                       .addLink("receipt", URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT, false, pairs)
                       ;
            return resource;
        }
    }

}

