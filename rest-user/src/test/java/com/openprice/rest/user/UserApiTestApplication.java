package com.openprice.rest.user;

import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.account.user.UserRoleType;
import com.openprice.file.FileFolderSettings;
import com.openprice.mail.EmailProperties;
import com.openprice.process.ProcessSettings;
import com.openprice.rest.AbstractRestApiTestApplication;

@EnableConfigurationProperties( {FileFolderSettings.class, EmailProperties.class, ProcessSettings.class} )
public class UserApiTestApplication extends AbstractRestApiTestApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(UserApiTestApplication.class, args);
    }

    @Inject
    private UserAccountService userAccountService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/user/**").hasAuthority(UserRoleType.ROLE_USER.name())
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/signin").permitAll()
                .antMatchers("/api").permitAll()
                .anyRequest().authenticated()
                .and()
            .addFilterBefore(testAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
    }

    @Override
    protected UserDetailsService getUserDetailsService() {
        return userAccountService;
    }

}
