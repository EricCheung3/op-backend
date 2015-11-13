package com.openprice.domain.receipt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParserResultRepository extends JpaRepository<ParserResult, String> {
    /**
     * Return latest parser result for the receipt.
     * @param receipt
     * @return
     */
    ParserResult findFirstByReceiptOrderByCreatedTimeDesc(Receipt receipt);
}
