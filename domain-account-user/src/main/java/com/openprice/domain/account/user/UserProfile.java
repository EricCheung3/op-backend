package com.openprice.domain.account.user;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;
import com.openprice.domain.common.Address;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true, exclude={"user"})
@SuppressWarnings("serial")
@Entity
@Table( name="user_profile" )
public class UserProfile extends BaseAuditableEntity {

    @Getter @Setter
    @OneToOne(mappedBy="profile")
    @JsonIgnore
    private UserAccount user;

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
        return firstName + " " + lastName;
    }
}
