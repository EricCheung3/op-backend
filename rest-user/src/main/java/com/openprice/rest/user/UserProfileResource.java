package com.openprice.rest.user;

import org.springframework.hateoas.Resource;

import com.openprice.domain.account.user.UserProfile;

public class UserProfileResource extends Resource<UserProfile> {

    public UserProfileResource(final UserProfile profile) {
        super(profile);
    }
}
