package com.openprice.rest;

import org.springframework.hateoas.MediaTypes;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;

import com.openprice.domain.account.UserAccount;
import com.openprice.domain.account.UserAccountService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping( value = UtilConstants.API_ROOT, produces = MediaTypes.HAL_JSON_VALUE )
@Slf4j
public abstract class AbstractRestController {
    
    protected final UserAccountService userAccountService;
    
    public AbstractRestController(final UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }
    
    protected UserAccount getCurrentUser() {
        return userAccountService.getCurrentUser();
    }
    
    protected UserAccount getCurrentAuthenticatedUser() {
        final UserAccount currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new AuthenticationCredentialsNotFoundException("User not logged in.");
        }
        return currentUser;
    }
    
    protected UserAccount getCurrentAuthenticatedAdmin() {
        final UserAccount currentUser = getCurrentUser();
        if (currentUser == null || !currentUser.isSuperAdmin()) {
            //ERROR! Should not happen.
            log.error("Fatal Error! Unauthorized data access!");
            throw new AccessDeniedException("User not logged in or not ADMIN.");
        }
        return currentUser;
    }

}
