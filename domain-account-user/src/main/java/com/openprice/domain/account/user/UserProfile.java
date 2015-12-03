package com.openprice.domain.account.user;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.account.AbstractProfile;
import com.openprice.domain.common.Address;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User profile data.
 */
@ToString(callSuper=true, exclude={"user"})
@SuppressWarnings("serial")
@Entity
@Table( name="user_profile" )
public class UserProfile extends AbstractProfile {

    @Getter @Setter
    @OneToOne(mappedBy="profile")
    @JsonIgnore
    private UserAccount user;

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

    public String getDisplayName() {
        return getFirstName() + " " + getLastName();
    }
}
