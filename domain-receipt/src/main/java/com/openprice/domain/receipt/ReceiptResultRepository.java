package com.openprice.domain.receipt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptResultRepository extends JpaRepository<ReceiptResult, String> {

    /**
     * Return latest parser result for the receipt. This is used for end user receipt view.
     *
     * @param receipt
     * @return
     */
    ReceiptResult findFirstByReceiptOrderByCreatedTimeDesc(Receipt receipt);

    Page<ReceiptResult> findByReceiptOrderByCreatedTime(Receipt receipt, Pageable pageable);
}
