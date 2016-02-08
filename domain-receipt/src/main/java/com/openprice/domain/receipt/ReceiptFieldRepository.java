package com.openprice.domain.receipt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptFieldRepository extends JpaRepository<ReceiptField, String> {

    List<ReceiptField> findByReceiptResult(ReceiptResult result);
}
