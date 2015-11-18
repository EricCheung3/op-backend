package com.openprice.domain.account.admin;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for AdminAccount entity.
 *
 */
public interface AdminAccountRepository extends JpaRepository<AdminAccount, String> {

    AdminAccount findByUsername(String username);

}
