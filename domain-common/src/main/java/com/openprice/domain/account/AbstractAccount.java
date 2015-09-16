package com.openprice.domain.account;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class AbstractAccount extends BaseAuditableEntity implements UserDetails {

    @Getter @Setter
    @Column(name="password")
    @JsonIgnore
    private String password;

    @Getter @Setter
    @Column(name="account_locked")
    private boolean accountLocked = false;

    @Getter @Setter
    @Column(name="trusted_account")
    private boolean trustedAccount = false;

    @Getter @Setter
    @Column(name="activated")
    private boolean activated = false;

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return activated;
    }

}
