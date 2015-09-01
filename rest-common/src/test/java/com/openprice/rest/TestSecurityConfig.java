package com.openprice.rest;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.openprice.domain.account.UserAccountService;
import com.openprice.domain.account.UserRoleType;

@Configuration
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    private UserAccountService userAccountService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/admin/**").hasAuthority(UserRoleType.ROLE_SUPER_ADMIN.name())
                .antMatchers("/api/user/**").hasAuthority(UserRoleType.ROLE_USER.name())
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api").permitAll()
                .anyRequest().authenticated()
                .and()
            .addFilterBefore(testAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder builder) throws Exception {
        builder
        	.authenticationProvider(testAuthenticationProvider());
    }

    @Bean
    public TestAuthenticationProvider testAuthenticationProvider() {
        return new TestAuthenticationProvider(userAccountService);
    }

    @Bean
    public TestAuthenticationFilter testAuthenticationFilter() throws Exception {
        return new TestAuthenticationFilter(UtilConstants.URL_SIGNIN, authenticationManagerBean());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
