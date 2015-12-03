package com.openprice.rest.admin;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.admin.AdminAccount;
import com.openprice.rest.LinkBuilder;

/**
 * REST resource for admin user account with links to users, receipts, store chains etc.
 */
public class AdminAccountResource extends Resource<AdminAccount> {

    public AdminAccountResource(final AdminAccount account) {
        super(account);
    }

    @Component
    public static class Assembler implements ResourceAssembler<AdminAccount, AdminAccountResource>, AdminApiUrls {

        @Override
        public AdminAccountResource toResource(final AdminAccount adminAccount) {
            final AdminAccountResource resource = new AdminAccountResource(adminAccount);
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN, false, null)
                       .addLink("users", URL_ADMIN_USERS, true, null)
                       .addLink("user", URL_ADMIN_USERS_USER, false, null)
                       .addLink("receipts", URL_ADMIN_RECEIPTS, true, null)
                       .addLink("receipt", URL_ADMIN_RECEIPTS_RECEIPT, false, null)
                       .addLink("chains", URL_ADMIN_CHAINS, true, null)
                       .addLink("chain", URL_ADMIN_CHAINS_CHAIN, false, null)
                       ;
            return resource;
        }
    }
}
