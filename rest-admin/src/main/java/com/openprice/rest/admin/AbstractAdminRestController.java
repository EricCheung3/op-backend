package com.openprice.rest.admin;

import javax.inject.Inject;

import com.openprice.domain.account.UserAccount;
import com.openprice.domain.account.UserAccountRepository;
import com.openprice.domain.account.UserAccountService;
import com.openprice.rest.AbstractRestController;
import com.openprice.rest.ResourceNotFoundException;

public abstract class AbstractAdminRestController extends AbstractRestController {
    protected final UserAccountRepository userAccountRepository;
    
    @Inject
    public AbstractAdminRestController(final UserAccountService userAccountService,
                                       final UserAccountRepository userAccountRepository) {
        super(userAccountService);
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
