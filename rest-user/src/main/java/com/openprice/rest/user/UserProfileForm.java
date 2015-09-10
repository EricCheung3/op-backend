package com.openprice.rest.user;

import com.openprice.domain.account.UserProfile;
import com.openprice.domain.common.Address;

import lombok.Getter;
import lombok.Setter;

public class UserProfileForm {
    @Getter @Setter
    private String firstName;
    
    @Getter @Setter
    private String middleName;
    
    @Getter @Setter
    private String lastName;
    
    @Getter @Setter
    private String phone;
    
    @Getter @Setter
    private String address1;
    
    @Getter @Setter
    private String address2;
    
    @Getter @Setter
    private String city;
    
    @Getter @Setter
    private String state;
    
    @Getter @Setter
    private String zip;
    
    @Getter @Setter
    private String country;
    
    public UserProfileForm() {
        
    }
    
    public UserProfileForm(UserProfile profile) {
        firstName = profile.getFirstName();
        middleName = profile.getMiddleName();
        lastName = profile.getLastName();
        phone = profile.getPhone();
        address1 = profile.getAddress().getAddress1();
        address2 = profile.getAddress().getAddress2();
        city = profile.getAddress().getCity();
        state = profile.getAddress().getState();
        zip = profile.getAddress().getZip();
        country = profile.getAddress().getCountry();
    }
    
    public void updateProfile(final UserProfile profile) {
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setMiddleName(middleName);
        profile.setPhone(phone);
        profile.setAddress(new Address(address1, address2, city, state, zip, country));
    }
}
