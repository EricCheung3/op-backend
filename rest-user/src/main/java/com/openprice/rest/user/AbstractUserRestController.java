package com.openprice.rest.user;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.rest.AbstractRestController;

public class AbstractUserRestController extends AbstractRestController {

    protected final UserAccountService userAccountService;

    public AbstractUserRestController(final UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    protected UserAccount getCurrentAuthenticatedUser() {
        final UserAccount currentUser = userAccountService.getCurrentUser();
        if (currentUser == null) {
            throw new AuthenticationCredentialsNotFoundException("User not logged in.");
        }
        return currentUser;
    }

}
