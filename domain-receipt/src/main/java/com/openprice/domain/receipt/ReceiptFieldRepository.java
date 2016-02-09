package com.openprice.domain.receipt;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptFieldRepository extends JpaRepository<ReceiptField, String> {

    Page<ReceiptField> findByReceiptResultOrderByCreatedTime(ReceiptResult result, Pageable pageable);

    List<ReceiptField> findByReceiptResult(ReceiptResult result);
}
