package com.openprice.rest.user;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.account.user.UserProfile;
import com.openprice.domain.account.user.UserProfileRepository;



/**
 * REST API Controller for current user account management.
 *
 */
@RestController
public class UserAccountRestController extends AbstractUserRestController {

    private final UserProfileRepository userProfileRepository;
    private final UserAccountResourceAssembler userAccountResourceAssembler;

    @Inject
    public UserAccountRestController(final UserAccountService userAccountService,
                                     final UserProfileRepository userProfileRepository,
                                     final UserAccountResourceAssembler userAccountResourceAssembler) {
        super(userAccountService);
        this.userProfileRepository = userProfileRepository;
        this.userAccountResourceAssembler = userAccountResourceAssembler;
    }

    /**
     * Get current user info. If not authenticated, return 401.
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER)
    @Transactional(readOnly=true)
    public HttpEntity<UserAccountResource> getCurrentUserAccount() {
        return ResponseEntity.ok(userAccountResourceAssembler.toResource(getCurrentAuthenticatedUser()));
    }

    @RequestMapping(method = RequestMethod.GET, value = UserApiUrls.URL_USER_PROFILE)
    @Transactional(readOnly=true)
    public HttpEntity<UserProfileResource> getCurrentUserProfile() {
        final UserAccount currentUserAccount = getCurrentAuthenticatedUser();
        return ResponseEntity.ok(new UserProfileResource(currentUserAccount.getProfile()));
    }

    @RequestMapping(method = RequestMethod.PUT, value = UserApiUrls.URL_USER_PROFILE)
    @Transactional
    public HttpEntity<Void> updateCurrentUserProfile(@RequestBody final UserProfileForm profileForm) {
        final UserAccount currentUserAccount = getCurrentAuthenticatedUser();
        final UserProfile profile = currentUserAccount.getProfile();
        profileForm.updateProfile(profile);
        userProfileRepository.save(profile);
        return ResponseEntity.noContent().build();
    }
}
