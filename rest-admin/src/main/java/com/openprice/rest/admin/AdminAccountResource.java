package com.openprice.rest.admin;

import org.springframework.hateoas.Resource;

import com.openprice.domain.account.UserAccount;

/**
 * REST resource for admin user account with links to users, blogs, comments.
 */
public class AdminAccountResource extends Resource<UserAccount> {
    public static final String LINK_NAME_USERS = "users";
    public static final String LINK_NAME_USER = "user";
    
    public AdminAccountResource(final UserAccount account) {
        super(account);
    }
}
