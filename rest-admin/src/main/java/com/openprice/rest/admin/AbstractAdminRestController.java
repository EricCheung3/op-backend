package com.openprice.rest.admin;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import com.openprice.domain.admin.AdminAccount;
import com.openprice.domain.admin.AdminAccountService;
import com.openprice.rest.AbstractRestController;

public abstract class AbstractAdminRestController extends AbstractRestController {
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
