package com.openprice.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * Code copied from http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt/.
 * And changed to use generic Spring Security UserDetails, UserDetailsService.
 *
 * Handler to process JWT token with JJWT (https://github.com/jwtk/jjwt).
 *
 */
@Slf4j
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
        try {
            return userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            log.warn("Cannot load user by username: '{}'!", username);
            return null;
        }
    }

    public String createTokenForUser(final UserDetails user) {
        return Jwts.builder()
                   .setSubject(user.getUsername())
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }
}
