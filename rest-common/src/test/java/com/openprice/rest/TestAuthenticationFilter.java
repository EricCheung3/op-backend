package com.openprice.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

@Slf4j
public class TestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public TestAuthenticationFilter(final String filterUrl, final AuthenticationManager authenticationManager) {
        super(filterUrl);
        setAuthenticationManager(authenticationManager);

    }
    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        final String username = request.getParameter("username");
        final TestAuthenticationToken authentication = new TestAuthenticationToken(username);
        log.debug("TestAuthenticationFilter get username "+username);
        return getAuthenticationManager().authenticate(authentication);
    }

}
