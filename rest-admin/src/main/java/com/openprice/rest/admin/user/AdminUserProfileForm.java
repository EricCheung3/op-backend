package com.openprice.rest.admin.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.account.user.UserProfile;
import com.openprice.domain.common.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class AdminUserProfileForm {
    private String firstName;

    private String middleName;

    private String lastName;

    private String phone;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;

    private String country;

    public UserProfile updateProfile(final UserProfile profile) {
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setMiddleName(middleName);
        profile.setPhone(phone);
        profile.setAddress(new Address(address1, address2, city, state, zip, country));
        return profile;
    }
}
