package com.openprice.domain.receipt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, String> {
    Page<ReceiptItem> findByReceiptData(ReceiptData data, Pageable pageable);

    Page<ReceiptItem> findByReceiptDataAndIgnoreIsFalse(ReceiptData data, Pageable pageable);

}
