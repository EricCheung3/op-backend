package com.openprice.domain.receipt;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptFeedbackRepository extends JpaRepository<ReceiptFeedback, String> {
    Page<ReceiptFeedback> findByReceiptOrderByCreatedTime(Receipt receipt, Pageable pageable);

    List<ReceiptFeedback> findByReceiptOrderByCreatedTime(Receipt receipt);
}
