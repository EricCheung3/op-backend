package com.openprice.domain.receipt;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openprice.domain.account.user.UserAccount;

/**
 * JPA Repository for Receipt entity.
 *
 */
public interface ReceiptRepository extends JpaRepository<Receipt, String> {

    List<Receipt> findByUser(UserAccount user);

    Page<Receipt> findByUserOrderByCreatedTimeDesc(UserAccount user, Pageable pageable);

    Page<Receipt> findByUserOrderByReceiptDateDesc(UserAccount user, Pageable pageable);

}
