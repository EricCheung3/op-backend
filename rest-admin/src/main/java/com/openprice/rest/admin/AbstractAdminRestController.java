package com.openprice.rest.admin;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.openprice.domain.account.admin.AdminAccount;
import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.rest.AbstractExternalRestController;

public abstract class AbstractAdminRestController extends AbstractExternalRestController implements AdminApiUrls {

    protected final AdminAccountService adminAccountService;

    public AbstractAdminRestController(final AdminAccountService adminAccountService) {
        this.adminAccountService = adminAccountService;
    }

    protected AdminAccount getCurrentAuthenticatedAdmin() {
        final AdminAccount currentAdmin = adminAccountService.getCurrentAdmin();
        if (currentAdmin == null) {
            throw new AuthenticationCredentialsNotFoundException("Admin not logged in.");
        }
        return currentAdmin;
    }

}
