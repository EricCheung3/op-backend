package com.openprice.rest.user;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountRepository;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.product.ProductCategory;

/**
 * REST API Controller for current user account management.
 *
 */
@RestController
public class UserAccountRestController extends AbstractUserRestController {

    private final UserAccountRepository userAccountRepository;
    private final UserAccountResource.Assembler userAccountResourceAssembler;

    @Inject
    public UserAccountRestController(final UserAccountService userAccountService,
                                     final UserAccountRepository userAccountRepository,
                                     final UserAccountResource.Assembler userAccountResourceAssembler) {
        super(userAccountService);
        this.userAccountRepository = userAccountRepository;
        this.userAccountResourceAssembler = userAccountResourceAssembler;
    }

    /**
     * Get current user info. If not authenticated, return 401.
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = URL_USER)
    @Transactional(readOnly=true)
    public HttpEntity<UserAccountResource> getCurrentUserAccount() {
        return ResponseEntity.ok(userAccountResourceAssembler.toResource(getCurrentAuthenticatedUser()));
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_PROFILE)
    @Transactional(readOnly=true)
    public HttpEntity<UserProfileResource> getCurrentUserProfile() {
        final UserAccount currentUserAccount = getCurrentAuthenticatedUser();
        return ResponseEntity.ok(new UserProfileResource(currentUserAccount.getProfile()));
    }

    @RequestMapping(method = RequestMethod.PUT, value = URL_USER_PROFILE)
    @Transactional
    public HttpEntity<Void> updateCurrentUserProfile(@RequestBody final UserProfileForm profileForm) {
        final UserAccount currentUserAccount = getCurrentAuthenticatedUser();
        profileForm.updateProfile(currentUserAccount.getProfile());
        userAccountRepository.save(currentUserAccount);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_USER_CATEGORIES)
    @Transactional(readOnly=true)
    public HttpEntity<List<CategoryData>> getCategoryList() {
        final List<CategoryData> result = new ArrayList<>();
        for (ProductCategory pc : ProductCategory.values()){
            result.add(new CategoryData(pc));
        }
        return ResponseEntity.ok(result);
    }

}
