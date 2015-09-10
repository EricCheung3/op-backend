package com.openprice.rest.admin;

import org.springframework.hateoas.Resource;

import com.openprice.domain.admin.AdminAccount;

/**
 * REST resource for admin user account with links to users, blogs, comments.
 */
public class AdminAccountResource extends Resource<AdminAccount> {
    public static final String LINK_NAME_USERS = "users";
    public static final String LINK_NAME_USER = "user";

    public AdminAccountResource(final AdminAccount account) {
        super(account);
    }
}
