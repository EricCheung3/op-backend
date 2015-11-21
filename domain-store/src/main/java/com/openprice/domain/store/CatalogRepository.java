package com.openprice.domain.store;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<Catalog, String> {

    List<Catalog> findByChain(StoreChain chain);

    Catalog findByChainAndCode(StoreChain chain, String code);
}
