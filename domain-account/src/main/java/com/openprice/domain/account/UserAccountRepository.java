package com.openprice.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * JPA Repository for UserAccount entity.
 *
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    UserAccount findByEmail(String email);

}
