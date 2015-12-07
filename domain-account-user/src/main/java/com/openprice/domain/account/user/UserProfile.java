package com.openprice.domain.account.user;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import com.openprice.domain.account.AbstractProfile;
import com.openprice.domain.common.Address;

import lombok.Setter;

/**
 * User profile data.
 */
@Embeddable
public class UserProfile extends AbstractProfile {

    @Setter
    @Embedded
    private Address address;

    /**
     * Because Hibernate will set address to null if all properties of address are null,
     * we have to handle it by setting a new value object.
     * @return
     */
    public Address getAddress() {
        if (address == null) {
            address = new Address();
        }
        return address;
    }

}
