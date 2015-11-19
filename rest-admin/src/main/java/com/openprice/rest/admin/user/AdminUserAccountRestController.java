package com.openprice.rest.admin.user;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountRepository;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.account.user.UserProfile;
import com.openprice.domain.account.user.UserProfileRepository;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.AbstractUserAdminRestController;
import com.openprice.rest.admin.AdminApiUrls;

/**
 * REST API Controller for user account management.
 *
 */
@RestController
public class AdminUserAccountRestController extends AbstractUserAdminRestController {

    private final UserProfileRepository userProfileRepository;
    private final AdminUserAccountResourceAssembler userResourceAssembler;

    @Inject
    public AdminUserAccountRestController(final AdminAccountService adminAccountService,
                                          final UserAccountService userAccountService,
                                          final UserAccountRepository userAccountRepository,
                                          final UserProfileRepository userProfileRepository,
                                          final AdminUserAccountResourceAssembler userResourceAssembler) {
        super(adminAccountService, userAccountService, userAccountRepository);
        this.userProfileRepository = userProfileRepository;
        this.userResourceAssembler = userResourceAssembler;
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_USERS)
    @Transactional(readOnly=true)
    public HttpEntity<PagedResources<AdminUserAccountResource>> getUserAccounts(
            @PageableDefault(size = UtilConstants.DEFAULT_RETURN_RECORD_COUNT, page = 0,
                             sort="createdTime", direction=Direction.DESC) final Pageable pageable,
            final PagedResourcesAssembler<UserAccount> assembler) {
        final Page<UserAccount> userAccounts = userAccountRepository.findAll(pageable);
        return ResponseEntity.ok(assembler.toResource(userAccounts, userResourceAssembler));
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_USERS_USER)
    @Transactional(readOnly=true)
    public HttpEntity<AdminUserAccountResource> getUserAccountByUserId(
            @PathVariable("userId") final String userId) throws ResourceNotFoundException {
        final UserAccount userAccount = getUserByUserId(userId);
        return ResponseEntity.ok(userResourceAssembler.toResource(userAccount));
    }

    @RequestMapping(method = RequestMethod.PUT, value = AdminApiUrls.URL_ADMIN_USERS_USER_LOCK_STATE)
    @Transactional
    public HttpEntity<Void> changeUserLockStatus(
            @PathVariable("userId") final String userId,
            @RequestBody final Map<String, Boolean> updateMap) throws ResourceNotFoundException {
        final UserAccount user = getUserByUserId(userId);
        final Boolean status = updateMap.get("locked");
        if (status != null) {
            user.setAccountLocked(status);
            userAccountRepository.save(user);
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = AdminApiUrls.URL_ADMIN_USERS_USER_PROFILE)
    @Transactional(readOnly=true)
    public HttpEntity<AdminUserProfileResource> getUserProfile(
            @PathVariable("userId") final String userId) {
        final UserAccount user = getUserByUserId(userId);
        final UserProfile profile = user.getProfile();
        return ResponseEntity.ok(new AdminUserProfileResource(profile));
    }

    @RequestMapping(method = RequestMethod.PUT, value = AdminApiUrls.URL_ADMIN_USERS_USER_PROFILE)
    @Transactional
    public HttpEntity<Void> updateUserProfile(
            @PathVariable("userId") final String userId,
            @RequestBody final AdminUserProfileForm profileForm) {
        final UserAccount user = getUserByUserId(userId);
        final UserProfile profile = user.getProfile();
        userProfileRepository.save(profileForm.updateProfile(profile));
        return ResponseEntity.noContent().build();
    }

}
