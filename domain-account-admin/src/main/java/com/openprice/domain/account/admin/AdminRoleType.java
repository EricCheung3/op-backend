package com.openprice.domain.account.admin;

import org.springframework.security.core.GrantedAuthority;

/**
 * Security role type for AdminAccount.
 *
 */
public enum AdminRoleType implements GrantedAuthority {
     ROLE_SUPER_ADMIN   // can manage all user account, all shops
    ,ROLE_USER_MANAGER  // can manage users
    ,ROLE_STORE_ADMIN    // can manage stores ?
    ;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
