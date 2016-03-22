package com.openprice.domain.account.user;

import java.time.LocalDateTime;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Domain Service for user management. It also implements UserDetailsService.
 *
 */
@Service
@Slf4j
public class UserAccountService implements UserDetailsService {

    public static final int RESET_PASSWORD_REQUEST_EXPIRING_HOURS = 2;

    private final UserAccountRepository userAccountRepository;
    private final UserResetPasswordRequestRepository userResetPasswordRequestRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Inject
    public UserAccountService(final UserAccountRepository userAccountRepository,
                              final UserResetPasswordRequestRepository userResetPasswordRequestRepository) {
        this.userAccountRepository = userAccountRepository;
        this.userResetPasswordRequestRepository = userResetPasswordRequestRepository;
    }

    public UserAccount createUserAccountByRegistrationData(final String email,
                                                           final String password,
                                                           final String firstName,
                                                           final String lastName) {
        final String hashedPassword = passwordEncoder.encode(password);
        final UserAccount userAccount = new UserAccount();
        userAccount.setEmail(email);
        userAccount.setPassword(hashedPassword);
        userAccount.getRoles().add(UserRoleType.ROLE_USER);
        userAccount.getProfile().setFirstName(firstName);
        userAccount.getProfile().setLastName(lastName);
        userAccount.setTrustedAccount(false);
        userAccount.setActivated(true);  // Temp solution. FIXME: add activation process
        return userAccountRepository.save(userAccount);
    }

    /**
     * Add role to user account.
     *
     * @param userId
     * @param role
     */
    public UserAccount addRole(final String userId, final UserRoleType role) {
        final UserAccount userAccount = userAccountRepository.findOne(userId);
        if (!userAccount.getRoles().contains(role)) {
            userAccount.getRoles().add(role);
        }
        return userAccountRepository.save(userAccount);
    }

    /**
     * Remove role from user account.
     *
     * @param userId
     * @param role
     */
    public UserAccount removeRole(final String userId, final UserRoleType role) {
        final UserAccount userAccount = userAccountRepository.findOne(userId);
        if (userAccount.getRoles().contains(role)) {
            userAccount.getRoles().remove(role);
        }
        return userAccountRepository.save(userAccount);
    }

    public UserAccount activateAccount(final String userId) {
        final UserAccount userAccount = userAccountRepository.findOne(userId);
        userAccount.setActivated(true);
        return userAccountRepository.save(userAccount);
    }

    /**
     * Gets current logged in user. Reload UserAccount object from database.
     *
     * @return UserAccount object from database for current user. Null if no logged in user.
     */
    public UserAccount getCurrentUser() {
        try {
            return userAccountRepository.findByEmail(getCurrentUsername());
        } catch (IllegalStateException ex) {
            //no logged in user, return null
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserAccount userAccount = userAccountRepository.findByEmail(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("Cannot find user by username " + username);
        }
        return userAccount;
    }

    public UserResetPasswordRequest createResetPasswordRequest(final String email) {
        final UserAccount userAccount = userAccountRepository.findByEmail(email);
        if (userAccount == null) {
            log.warn("User forget password request with non-registered email '{}'.", email);
            return null;
        }
        // delete all old request from this user
        for (final UserResetPasswordRequest request : userResetPasswordRequestRepository.findByEmail(email)) {
            userResetPasswordRequestRepository.delete(request);
        }
        return userResetPasswordRequestRepository.save(UserResetPasswordRequest.createRequest(email));
    }

    public UserResetPasswordRequest getUserResetPasswordReqest(final String requestId) {
        final UserResetPasswordRequest request = userResetPasswordRequestRepository.findOne(requestId);
        if (request != null) {
            LocalDateTime expiringTime = request.getRequestTime().plusHours(RESET_PASSWORD_REQUEST_EXPIRING_HOURS);
            if (LocalDateTime.now().isBefore(expiringTime)) {
                return request;
            }
            // delete expired request
            log.warn("User '{}' reset password with expired request ID '{}'.", request.getEmail(), requestId);
            userResetPasswordRequestRepository.delete(request);
        } else {
            log.warn("User reset password with invalid request ID '{}'.", requestId);
        }

        return null;
    }

    public void resetPassword(final String email, final String newPassword) {
        UserAccount userAccount = userAccountRepository.findByEmail(email);
        final String hashedPassword = passwordEncoder.encode(newPassword);
        userAccount.setPassword(hashedPassword);
        userAccountRepository.save(userAccount);
    }

    public void resetPasswordWithRequest(final String requestId, final String email, final String newPassword) {
        final UserResetPasswordRequest request = getUserResetPasswordReqest(requestId);
        if (request != null) {
            UserAccount userAccount = userAccountRepository.findByEmail(email);
            final String hashedPassword = passwordEncoder.encode(newPassword);
            userAccount.setPassword(hashedPassword);
            userAccountRepository.save(userAccount);
            userResetPasswordRequestRepository.delete(request);
        }
    }

    private String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("No user signed in");
        }
        return authentication.getName();
    }
}
