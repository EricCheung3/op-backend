package com.openprice.domain.admin;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Domain Entity for admin account.
 *
 */
@ToString(callSuper=true)
@SuppressWarnings("serial")
@Entity
@Table
public class AdminAccount extends BaseAuditableEntity implements UserDetails {

    @Getter @Setter
    @ElementCollection(targetClass=AdminRoleType.class, fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="admin_role", joinColumns=@JoinColumn(name="admin_account_id"))
    @Column(name="role")
    private Collection<AdminRoleType> roles;

    @Getter @Setter
    @Column
    private String username;

    @Getter @Setter
    @Column
    @JsonIgnore
    private String password;

    @Getter @Setter
    @Column
    private String email;

    @Getter @Setter
    @Column
    private String firstName;

    @Getter @Setter
    @Column
    private String lastName;

    @Getter @Setter
    @Column
    private String title;

    @Getter @Setter
    @Column
    private boolean accountLocked = false;

    @Getter @Setter
    @Column
    private boolean activated = true;

    public AdminAccount() {
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

    public boolean isSuperAdmin(){
        for (AdminRoleType role : getRoles()) {
            if (role == AdminRoleType.ROLE_SUPER_ADMIN){
                return true;
            }
        }
        return false;
    }
}
