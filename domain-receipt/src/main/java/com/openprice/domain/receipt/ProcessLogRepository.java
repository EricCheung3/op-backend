package com.openprice.domain.receipt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessLogRepository extends JpaRepository<ProcessLog, String> {

    List<ProcessLog> findByImageId(String imageId);

    List<ProcessLog> findByServerName(String serverName);
}
