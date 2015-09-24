package com.openprice.domain.store;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreBranchRepository extends JpaRepository<StoreBranch, String> {
    List<StoreBranch> findByStore(Store store);

    Page<StoreBranch> findByStore(Store store, Pageable pageable);
}
