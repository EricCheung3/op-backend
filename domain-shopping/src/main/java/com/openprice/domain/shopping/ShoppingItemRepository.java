package com.openprice.domain.shopping;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, String> {

    Page<ShoppingItem> findByStoreOrderByName(ShoppingStore store, Pageable pageable);

    List<ShoppingItem> findByStoreOrderByName(ShoppingStore store);
}
