package com.openprice.domain.account;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.openprice.domain.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * Super class for UserProfile and AdminProfile.
 * We are using "Table per concrete class with implicit polymorphism" for profile hierarchy.
 */
@MappedSuperclass
@SuppressWarnings("serial")
public class AbstractProfile extends BaseAuditableEntity {

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

}
