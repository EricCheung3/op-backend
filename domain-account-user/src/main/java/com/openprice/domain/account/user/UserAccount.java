package com.openprice.domain.account.user;

import java.util.Collection;
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

    /**
     * Static builder method to create a normal UserAccount object with profile from input.
     * Can be used from self-registration or user management.
     *
     * @param email
     * @param password
     * @param firstName
     * @param lastName
     * @return
     */
    public static UserAccount createNormalUser(final String email,
                                               final String password,
                                               final String firstName,
                                               final String lastName) {
        final UserAccount userAccount = new UserAccount();
        userAccount.setEmail(email);
        userAccount.setPassword(password);
        userAccount.getRoles().add(UserRoleType.ROLE_USER);
        userAccount.getProfile().setFirstName(firstName);
        userAccount.getProfile().setLastName(lastName);
        return userAccount;
    }

    /**
     * Static builder method to create a UserAccount object without profile for test purpose. FIXME ???
     *
     * @param id
     * @param email
     * @return
     */
    public static UserAccount createTestUser(final String id, final String email) {
        final UserAccount user = new UserAccount();
        user.setId(id);
        user.setEmail(email);
        return user;
    }
}
