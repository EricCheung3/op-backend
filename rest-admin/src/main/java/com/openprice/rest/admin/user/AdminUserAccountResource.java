package com.openprice.rest.admin.user;

import org.springframework.hateoas.Resource;

import com.openprice.domain.account.user.UserAccount;

import lombok.Getter;
import lombok.Setter;

public class AdminUserAccountResource extends Resource<UserAccount> {

    @Getter @Setter
    private AdminUserProfileResource profile;

    public AdminUserAccountResource(final UserAccount account) {
        super(account);
        profile = new AdminUserProfileResource(account.getProfile());
    }

}

