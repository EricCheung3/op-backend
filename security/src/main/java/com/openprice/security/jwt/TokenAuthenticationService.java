package com.openprice.security.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Code copied from http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt/.
 * And changed to use generic Spring Security UserDetails, UserDetailsService.
 *
 */
public class TokenAuthenticationService {
    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    private final TokenHandler tokenHandler;

    public TokenAuthenticationService(final String secret, final UserDetailsService userService) {
        tokenHandler = new TokenHandler(secret, userService);
    }

    public void addAuthentication(final HttpServletResponse response, final UserAuthentication authentication) {
        final UserDetails user = authentication.getDetails();
        response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
    }

    public Authentication getAuthentication(final HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final UserDetails user = tokenHandler.parseUserFromToken(token);
            if (user != null && user.isAccountNonExpired() && user.isAccountNonLocked() && user.isEnabled()) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }

}
