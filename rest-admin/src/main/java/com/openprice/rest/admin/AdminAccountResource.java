package com.openprice.rest.admin;

import org.springframework.hateoas.Resource;

import com.openprice.domain.account.admin.AdminAccount;

/**
 * REST resource for admin user account with links to users,receipts, etc.
 */
public class AdminAccountResource extends Resource<AdminAccount> {

    public AdminAccountResource(final AdminAccount account) {
        super(account);
    }
}
