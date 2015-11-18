package com.openprice.domain.account.admin;

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
import com.openprice.domain.account.AbstractAccount;

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
@Table( name="admin_account" )
public class AdminAccount extends AbstractAccount implements UserDetails {

    @Getter @Setter
    @ElementCollection(targetClass=AdminRoleType.class, fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="admin_role", joinColumns=@JoinColumn(name="admin_account_id"))
    @Column(name="role")
    private Collection<AdminRoleType> roles = new ArrayList<>();

    @Getter @Setter
    @Column(name="username")
    private String username;

    @Getter @Setter
    @Column(name="email")
    private String email;

    @Getter @Setter
    @Column(name="first_name")
    private String firstName;

    @Getter @Setter
    @Column(name="last_name")
    private String lastName;

    @Getter @Setter
    @Column(name="title")
    private String title;

    AdminAccount() {}

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
