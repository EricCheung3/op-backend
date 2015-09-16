package com.openprice.domain.account.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.account.AbstractAccount;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Domain Entity for user account.
 *
 */
@ToString(callSuper=true, exclude={"profile"})
@SuppressWarnings("serial")
@Entity
@Table( name="user_account" )
public class UserAccount extends AbstractAccount implements UserDetails {

    @Getter @Setter
    @ElementCollection(targetClass=UserRoleType.class, fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_role", joinColumns=@JoinColumn(name="user_account_id"))
    @Column(name="role")
    private Collection<UserRoleType> roles;

    @Getter @Setter
    @Column(name="email")
    private String email;

    @Getter @Setter
    @Column(name="trusted_account")
    private boolean trustedAccount = false;

    @Getter @Setter
    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private UserProfile profile;

    public UserAccount() {
        this.roles = new ArrayList<>();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     * We are using email as unique username.
     */
    @Override
    public String getUsername() {
        return email;
    }

}
