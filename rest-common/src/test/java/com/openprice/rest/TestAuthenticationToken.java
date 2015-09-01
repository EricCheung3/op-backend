package com.openprice.rest;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class TestAuthenticationToken extends AbstractAuthenticationToken {

    private final String userId;

    public TestAuthenticationToken(final String userId) {
        super(null);
        this.userId = userId;
    }

    public TestAuthenticationToken(final String userId, final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userId;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public String getName() {
        return userId;
    }

}
