package com.openprice.rest.admin.user;

import org.springframework.hateoas.Resource;

import com.openprice.domain.account.user.UserProfile;

public class AdminUserProfileResource extends Resource<UserProfile> {

    public AdminUserProfileResource(final UserProfile profile) {
        super(profile);
    }
}
