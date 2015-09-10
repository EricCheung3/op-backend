package com.openprice.domain.account;

import org.springframework.security.core.GrantedAuthority;

/**
 * Security role type for UserAccount.
 *
 */
public enum UserRoleType implements GrantedAuthority {
    ROLE_STORE_MANAGER, // can see data for their shop
    ROLE_USER           // can manage own profile and data
    ;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
