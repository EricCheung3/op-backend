package com.openprice.rest.admin;

import org.springframework.hateoas.Resource;

import com.openprice.domain.account.admin.AdminAccount;

/**
 * REST resource for admin user account with links to users,receipts, etc.
 */
public class AdminAccountResource extends Resource<AdminAccount> {
    public static final String LINK_NAME_USERS = "users";
    public static final String LINK_NAME_USER = "user";
    public static final String LINK_NAME_RECEIPTS = "receipts";
    public static final String LINK_NAME_RECEIPT = "receipt";
    public static final String LINK_NAME_STORES = "stores";
    public static final String LINK_NAME_STORE = "store";

    public AdminAccountResource(final AdminAccount account) {
        super(account);
    }
}
