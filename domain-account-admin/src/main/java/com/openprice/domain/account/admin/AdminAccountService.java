package com.openprice.domain.account.admin;

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
 * Domain Service for admin user management. It also implements UserDetailsService.
 *
 */
@Service
@Slf4j
public class AdminAccountService implements UserDetailsService {

    private final AdminAccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Inject
    public AdminAccountService(final AdminAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AdminAccount createAdminAccount(final String username,
                                           final String password,
                                           final String firstName,
                                           final String lastName,
                                           final String email,
                                           final String title) {

        final String hashedPassword = passwordEncoder.encode(password);
        final AdminAccount account = AdminAccount.createAccount();
        account.setUsername(username);
        account.setPassword(hashedPassword);
        account.setEmail(email);
        account.getRoles().add(AdminRoleType.ROLE_USER_MANAGER);
        account.setActivated(true);
        account.getProfile().setFirstName(firstName);
        account.getProfile().setLastName(lastName);
        account.getProfile().setTitle(title);
        return accountRepository.save(account);
    }

    /**
     * Gets current logged in admin. Reload AdminAccount object from database.
     *
     * @return AdminAccount object from database for current admin. Null if no logged in admin.
     */
    public AdminAccount getCurrentAdmin() {
        try {
            return accountRepository.findByUsername(getCurrentUsername());
        } catch (IllegalStateException ex) {
            //no logged in user, return null
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final AdminAccount account = accountRepository.findByUsername(username);
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
