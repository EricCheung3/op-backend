package com.openprice;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.openprice.domain.account.user.UserAccountService;
import com.openprice.rest.UtilConstants;
import com.openprice.security.jwt.StatelessAuthenticationFilter;
import com.openprice.security.jwt.StatelessLoginFilter;
import com.openprice.security.jwt.TokenAuthenticationService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("application.token.secret")
    private String secret;

    @Inject
    private UserAccountService userAccountService;

    public SecurityConfig() {
        super(true);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .exceptionHandling().and()
            .anonymous().and()
            .servletApi().and()
            //.headers().cacheControl().and()
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/user/**").authenticated()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/signin").permitAll()
                .antMatchers("/api").permitAll()
                .anyRequest().authenticated()
                .and()
            .csrf().disable()
            .addFilterBefore(new CorsFilter(), AbstractPreAuthenticatedProcessingFilter.class)
            .addFilterBefore(statelessLoginFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(statelessAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
            ;
        // @formatter:on
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        final TokenAuthenticationService service = new TokenAuthenticationService(secret, userAccountService);
        return service;
    }

    private StatelessLoginFilter statelessLoginFilter() throws Exception {
        return new StatelessLoginFilter(UtilConstants.URL_SIGNIN, tokenAuthenticationService(), userAccountService, authenticationManager());
    }

    private StatelessAuthenticationFilter statelessAuthenticationFilter() {
        return new StatelessAuthenticationFilter(tokenAuthenticationService());
    }

    @Inject
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userAccountService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }
}
