package com.openprice.domain.shopping;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, String> {

//    Page<ShoppingItem> findByUserAndStoreOrderByCreatedTime(UserAccount user, StoreChain store, Pageable pageable);

//    Page<ShoppingItem> findByUserAndStoreOrderByItemName(UserAccount user, StoreChain store, Pageable pageable);
}
