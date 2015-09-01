package com.openprice.rest.common;

import org.springframework.hateoas.Resource;

import com.openprice.domain.account.UserProfile;

public class UserProfileResource extends Resource<UserProfile> {

    public UserProfileResource(final UserProfile profile) {
        super(profile);
    }
}
