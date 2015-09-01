package com.openprice.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Slf4j
public class TestAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public TestAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("Try to authenticate "+authentication.getName());

        final TestAuthenticationToken authToken = (TestAuthenticationToken)authentication;
        final UserDetails user = userDetailsService.loadUserByUsername(authToken.getName());
        log.debug("Found user "+user.getUsername()+" with authorities "+user.getAuthorities());
        final Authentication authenticationToken = new TestAuthenticationToken(user.getUsername(), user.getAuthorities());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TestAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
