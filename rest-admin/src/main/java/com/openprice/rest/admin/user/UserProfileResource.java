package com.openprice.rest.admin.user;

import org.springframework.hateoas.Resource;

import com.openprice.domain.account.UserProfile;

public class UserProfileResource extends Resource<UserProfile> {

    public UserProfileResource(final UserProfile profile) {
        super(profile);
    }
}
