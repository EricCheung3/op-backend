package com.openprice.domain.store;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, String> {

    Page<Catalog> findByChain(StoreChain chain, Pageable pageable);

    Catalog findByChainAndCode(StoreChain chain, String catalogCode);

    List<Catalog> findTop20ByChainAndNaturalNameIgnoreCaseContaining(StoreChain chain, String query);
}
