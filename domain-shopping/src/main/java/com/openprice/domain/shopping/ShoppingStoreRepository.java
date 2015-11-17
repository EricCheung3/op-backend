package com.openprice.domain.shopping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openprice.domain.account.user.UserAccount;

public interface ShoppingStoreRepository extends JpaRepository<ShoppingStore, String> {

    ShoppingStore findByUserAndChainCode(UserAccount user, String chainCode);

    Page<ShoppingStore> findByUserOrderByDisplayName(UserAccount user, Pageable pageable);
}
