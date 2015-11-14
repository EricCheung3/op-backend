package com.openprice.domain.receipt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, String> {
    Page<ReceiptItem> findByResult(ParserResult result, Pageable pageable);

}
