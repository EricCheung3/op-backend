package com.openprice.domain.account.user;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.account.AbstractAccount;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

/**
 * Domain Entity for user account.
 *
 */
@ToString(callSuper=true, exclude={"profile"})
@SuppressWarnings("serial")
@Entity
@Table( name="user_account" )
public class UserAccount extends AbstractAccount {

    @Getter
    @ElementCollection(targetClass=UserRoleType.class, fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_role", joinColumns=@JoinColumn(name="user_account_id"))
    @Column(name="role")
    @org.hibernate.annotations.SortNatural
    private SortedSet<UserRoleType> roles = new TreeSet<>();

    @Getter @Setter
    @Column(name="email", unique=true)
    private String email;

    @Getter @Setter
    @Column(name="trusted_account")
    private Boolean trustedAccount = false;

    @Getter
    private UserProfile profile = new UserProfile();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    UserAccount() {}

    /**
     * We are using email as unique username.
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Builder(builderMethodName="testObjectBuilder")
    static UserAccount createTestUser(final String id,
                                      final String email,
                                      @Singular final Set<UserRoleType> roles,
                                      final String firstName,
                                      final String lastName) {
        final UserAccount userAccount = new UserAccount();
        userAccount.setId(id);
        userAccount.setEmail(email);
        userAccount.getRoles().addAll(roles);
        userAccount.getProfile().setFirstName(firstName);
        userAccount.getProfile().setLastName(lastName);
        return userAccount;
    }
}
