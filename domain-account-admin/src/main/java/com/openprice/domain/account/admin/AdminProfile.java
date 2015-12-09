package com.openprice.domain.account.admin;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.openprice.domain.account.AbstractProfile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@ToString(callSuper=true)
@SuppressWarnings("serial")
public class AdminProfile extends AbstractProfile {

    @Getter @Setter
    @Column(name="title")
    private String title;

}
