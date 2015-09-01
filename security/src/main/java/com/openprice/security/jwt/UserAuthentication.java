package com.openprice.security.jwt;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Code copied from http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt/.
 * And changed to use generic Spring Security UserDetails.
 *
 * "This class implements Spring Security’s Authentication interface, which is how Spring ties users,
 * authorities/roles, principals, credentials, and authentication status together.
 * The implementation always assumes the user is authenticated and delegates the rest to Spring Security’s
 * own UserDetails class.
 *
 */
@SuppressWarnings("serial")
public class UserAuthentication implements Authentication {

    private final UserDetails user;
    private boolean authenticated = true;

    public UserAuthentication(final UserDetails user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public UserDetails getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(final boolean authenticated) {
        this.authenticated = authenticated;
    }
}
