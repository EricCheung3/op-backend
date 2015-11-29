package com.openprice.rest.user;

import org.springframework.hateoas.Resource;

import com.openprice.domain.account.user.UserAccount;

import lombok.Getter;
import lombok.Setter;

public class UserAccountResource extends Resource<UserAccount> {

    @Getter @Setter
    private UserProfileResource profile;

    @Getter @Setter
    private String uploadUrl;

    public UserAccountResource(final UserAccount account) {
        super(account);
        profile = new UserProfileResource(account.getProfile());
    }

}