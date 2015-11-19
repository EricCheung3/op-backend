package com.openprice.rest.admin;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.account.admin.AdminAccount;
import com.openprice.rest.LinkBuilder;

@Component
public class AdminAccountResourceAssembler implements ResourceAssembler<AdminAccount, AdminAccountResource>, AdminApiUrls {

    public static final String LINK_NAME_USERS = "users";
    public static final String LINK_NAME_USER = "user";
    public static final String LINK_NAME_RECEIPTS = "receipts";
    public static final String LINK_NAME_RECEIPT = "receipt";
    public static final String LINK_NAME_CHAINS = "chains";
    public static final String LINK_NAME_CHAIN = "chain";

    @Override
    public AdminAccountResource toResource(final AdminAccount adminAccount) {
        final AdminAccountResource resource = new AdminAccountResource(adminAccount);
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_ADMIN, false, null)
                   .addLink(LINK_NAME_USERS, URL_ADMIN_USERS, true, null)
                   .addLink(LINK_NAME_USER, URL_ADMIN_USERS_USER, false, null)
                   .addLink(LINK_NAME_RECEIPTS, URL_ADMIN_RECEIPTS, true, null)
                   .addLink(LINK_NAME_RECEIPT, URL_ADMIN_RECEIPTS_RECEIPT, false, null)
                   .addLink(LINK_NAME_CHAINS, URL_ADMIN_CHAINS, true, null)
                   .addLink(LINK_NAME_CHAIN, URL_ADMIN_CHAINS_CHAIN, false, null)
                   ;
        return resource;
    }
}
