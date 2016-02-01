package com.openprice.domain.receipt;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptImageRepository extends JpaRepository<ReceiptImage, String> {

    Page<ReceiptImage> findByReceiptOrderByCreatedTime(Receipt receipt, Pageable pageable);

    List<ReceiptImage> findByReceiptOrderByCreatedTime(Receipt receipt);

    Long countByReceipt(Receipt receipt);

    ReceiptImage findFirstByReceiptOrderByCreatedTime(Receipt receipt);

    Long countByReceiptAndStatus(Receipt receipt, ProcessStatusType status);

    List<ReceiptImage> findByReceiptAndStatusOrderByCreatedTime(Receipt receipt, ProcessStatusType status);

}
