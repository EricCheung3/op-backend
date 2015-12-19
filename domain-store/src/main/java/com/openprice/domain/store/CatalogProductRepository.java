package com.openprice.domain.store;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogProductRepository extends JpaRepository<CatalogProduct, String> {

    Page<CatalogProduct> findByChain(StoreChain chain, Pageable pageable);

    CatalogProduct findByChainAndCatalogCode(StoreChain chain, String catalogCode);

    List<CatalogProduct> findTop20ByChainAndNaturalNameIgnoreCaseContaining(StoreChain chain, String query);
}
