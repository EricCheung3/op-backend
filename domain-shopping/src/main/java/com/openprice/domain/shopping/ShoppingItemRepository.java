package com.openprice.domain.shopping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.store.StoreChain;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, String> {

    Page<ShoppingItem> findByUserAndStoreOrderByCreatedTime(UserAccount user, StoreChain store, Pageable pageable);

    Page<ShoppingItem> findByUserAndStoreOrderByItemName(UserAccount user, StoreChain store, Pageable pageable);
}
