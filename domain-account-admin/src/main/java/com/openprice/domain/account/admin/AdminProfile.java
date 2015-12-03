package com.openprice.domain.account.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.account.AbstractProfile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true, exclude={"admin"})
@SuppressWarnings("serial")
@Entity
@Table( name="admin_profile" )
public class AdminProfile extends AbstractProfile {

    @Getter @Setter
    @OneToOne(mappedBy="profile")
    @JsonIgnore
    private AdminAccount admin;

    @Getter @Setter
    @Column(name="title")
    private String title;

}
