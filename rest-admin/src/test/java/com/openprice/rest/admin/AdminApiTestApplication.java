package com.openprice.rest.admin;

import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.openprice.domain.admin.AdminAccountService;
import com.openprice.domain.admin.AdminRoleType;
import com.openprice.parser.simple.SimpleParser;
import com.openprice.rest.AbstractRestApiTestApplication;

public class AdminApiTestApplication extends AbstractRestApiTestApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AdminApiTestApplication.class, args);
    }

    @Inject
    private AdminAccountService adminAccountService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/admin/admins/**").hasAuthority(AdminRoleType.ROLE_SUPER_ADMIN.name())
                .antMatchers("/api/admin/users/**").hasAuthority(AdminRoleType.ROLE_USER_MANAGER.name())
                .antMatchers("/api").permitAll()
                .anyRequest().authenticated()
                .and()
            .addFilterBefore(testAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
    }

    @Override
    protected UserDetailsService getUserDetailsService() {
        return adminAccountService;
    }

    @Bean
    public SimpleParser simpleParser() {
        return new SimpleParser();
    }

}
