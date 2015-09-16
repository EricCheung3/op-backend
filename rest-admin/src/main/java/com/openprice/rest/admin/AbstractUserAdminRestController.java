package com.openprice.rest.admin;

import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountRepository;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.rest.ResourceNotFoundException;

public abstract class AbstractUserAdminRestController extends AbstractAdminRestController {

    protected final UserAccountService userAccountService;
    protected final UserAccountRepository userAccountRepository;

    public AbstractUserAdminRestController(final AdminAccountService adminAccountService,
                                           final UserAccountService userAccountService,
                                           final UserAccountRepository userAccountRepository) {
        super(adminAccountService);
        this.userAccountService = userAccountService;
        this.userAccountRepository = userAccountRepository;
    }

    protected UserAccount getUserByUserId(String userId) throws ResourceNotFoundException {
        UserAccount user = userAccountRepository.findOne(userId);
        if (user == null) {
            throw new ResourceNotFoundException("No such user for id "+user);
        }
        return user;
    }

}
