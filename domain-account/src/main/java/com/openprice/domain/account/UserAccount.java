package com.openprice.domain.account;

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
import com.openprice.domain.BaseAuditableEntity;

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
@Table
public class UserAccount extends BaseAuditableEntity implements UserDetails {

    @Getter @Setter
    @ElementCollection(targetClass=UserRoleType.class, fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_role", joinColumns=@JoinColumn(name="user_account_id"))
    @Column(name="role")
    private Collection<UserRoleType> roles;

    @Getter @Setter
    @Column
    private String email;

    @Getter @Setter
    @Column
    @JsonIgnore
    private String password;

    @Getter @Setter
    @Column
    private boolean accountLocked = false;

    @Getter @Setter
    @Column
    private boolean trustedAccount = false;

    @Getter @Setter
    @Column
    private boolean activated = false;

    @Getter @Setter
    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private UserProfile profile;

    public UserAccount() {
        this.roles = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activated;
    }

    /**
     * We are using email as unique username.
     */
    @Override
    public String getUsername() {
        return email;
    }


}
