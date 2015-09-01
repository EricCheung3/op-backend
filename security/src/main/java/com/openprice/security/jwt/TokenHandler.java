package com.openprice.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Code copied from http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt/.
 * And changed to use generic Spring Security UserDetails, UserDetailsService.
 *
 * Handler to process JWT token with JJWT (https://github.com/jwtk/jjwt).
 *
 */
public class TokenHandler {
    private final String secret;
    private final UserDetailsService userService;

    public TokenHandler(final String secret, final UserDetailsService userService) {
        this.secret = secret;
        this.userService = userService;
    }

    public UserDetails parseUserFromToken(final String token) {
        final String username = Jwts.parser()
                                    .setSigningKey(secret)
                                    .parseClaimsJws(token)
                                    .getBody()
                                    .getSubject();
        return userService.loadUserByUsername(username);
    }

    public String createTokenForUser(final UserDetails user) {
        return Jwts.builder()
                   .setSubject(user.getUsername())
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }
}
