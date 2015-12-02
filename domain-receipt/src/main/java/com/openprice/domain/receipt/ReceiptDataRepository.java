package com.openprice.domain.receipt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptDataRepository extends JpaRepository<ReceiptData, String> {
    /**
     * Return latest parser result data for the receipt. This is used for end user receipt view.
     *
     * @param receipt
     * @return
     */
    ReceiptData findFirstByReceiptOrderByCreatedTimeDesc(Receipt receipt);

    Page<ReceiptData> findByReceiptOrderByCreatedTime(Receipt receipt, Pageable pageable);
}
