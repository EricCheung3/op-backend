package com.openprice.domain.store;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreBranchRepository extends JpaRepository<StoreBranch, String> {

    List<StoreBranch> findByChain(StoreChain chain);

    Page<StoreBranch> findByChain(StoreChain chain, Pageable pageable);
}
