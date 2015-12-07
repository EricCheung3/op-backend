package com.openprice.domain.account;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * Super class for UserProfile and AdminProfile.
 * Profiles are embedded components inside account (UserAccount or AdminAccount).
 */
@MappedSuperclass
public class AbstractProfile {

    @Getter @Setter
    @Column(name="first_name")
    private String firstName;

    @Getter @Setter
    @Column(name="middle_name")
    private String middleName;

    @Getter @Setter
    @Column(name="last_name")
    private String lastName;

    @Getter @Setter
    @Column(name="phone")
    private String phone;

    public String getDisplayName() {
        return getFirstName() + " " + getLastName();
    }

}
