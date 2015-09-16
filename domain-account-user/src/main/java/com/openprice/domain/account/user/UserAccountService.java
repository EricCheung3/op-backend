package com.openprice.domain.account.user;

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
 * Domain Service for user administration. It also implements UserDetailsService.
 *
 */
@Service
@Slf4j
public class UserAccountService implements UserDetailsService {

    private final UserAccountRepository accountRepository;
    private final UserProfileRepository profileRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Inject
    public UserAccountService(final UserAccountRepository accountRepository,
                              final UserProfileRepository profileRepository) {
        this.accountRepository = accountRepository;
        this.profileRepository = profileRepository;
    }

    public UserAccount createUserAccountByRegistrationData(final String email,
                                                           final String password,
                                                           final String firstName,
                                                           final String lastName) {


        String hashedPassword = passwordEncoder.encode(password);
        UserAccount account = new UserAccount();
        account.setEmail(email);
        account.setPassword(hashedPassword);
        account.getRoles().add(UserRoleType.ROLE_USER);
        account.setTrustedAccount(false);
        account.setActivated(true);  // Temp solution. FIXME: add activation process

        final UserProfile profile = new UserProfile();
        profile.setUser(account);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);

        account.setProfile(profile);
        return accountRepository.save(account);
    }

    /**
     * Add role to user account.
     *
     * @param userId
     * @param role
     */
    public UserAccount addRole(final String userId, final UserRoleType role) {
        final UserAccount account = accountRepository.findOne(userId);
        if (!account.getRoles().contains(role)) {
            account.getRoles().add(role);
        }
        return accountRepository.save(account);
    }

    /**
     * Remove role from user account.
     *
     * @param userId
     * @param role
     */
    public UserAccount removeRole(final String userId, final UserRoleType role) {
        final UserAccount account = accountRepository.findOne(userId);
        if (account.getRoles().contains(role)) {
            account.getRoles().remove(role);
        }
        return accountRepository.save(account);
    }

    public UserAccount activateAccount(final String userId) {
        final UserAccount account = accountRepository.findOne(userId);
        account.setActivated(true);
        return accountRepository.save(account);
    }

    /**
     * Gets current logged in user. Reload UserAccount object from database.
     *
     * @return UserAccount object from database for current user. Null if no logged in user.
     */
    public UserAccount getCurrentUser() {
        try {
            return accountRepository.findByEmail(getCurrentUsername());
        } catch (IllegalStateException ex) {
            //no logged in user, return null
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("==>loadUserByUsername("+username+")");

        final UserAccount account = accountRepository.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("Cannot find user by username " + username);
        }
        return account;
    }

    private String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("No user signed in");
        }
        return authentication.getName();
    }
}
