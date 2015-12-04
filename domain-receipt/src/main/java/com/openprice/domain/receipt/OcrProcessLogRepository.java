package com.openprice.domain.receipt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OcrProcessLogRepository extends JpaRepository<OcrProcessLog, String> {

    List<OcrProcessLog> findByImageId(String imageId);

    List<OcrProcessLog> findByServerName(String serverName);
}
