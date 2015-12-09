package com.openprice.domain.receipt;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, String> {

    Page<ReceiptItem> findByReceiptResultOrderByLineNumber(ReceiptResult result, Pageable pageable);

    List<ReceiptItem> findByReceiptResultOrderByLineNumber(ReceiptResult result);

    Page<ReceiptItem> findByReceiptResultAndIgnoredIsFalseOrderByLineNumber(ReceiptResult result, Pageable pageable);

    List<ReceiptItem> findByReceiptResultAndIgnoredIsFalseOrderByLineNumber(ReceiptResult result);

}
