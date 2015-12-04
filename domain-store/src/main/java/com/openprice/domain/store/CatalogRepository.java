package com.openprice.domain.store;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CatalogRepository extends JpaRepository<Catalog, String> {

    Page<Catalog> findByChain(StoreChain chain, Pageable pageable);

    List<Catalog> findByChain(StoreChain chain);

    Catalog findByChainAndCode(StoreChain chain, String catalogCode);

    @Query("select c from Catalog c where c.chain = ?1 and upper(c.naturalName) like %?2%")
    List<Catalog> searchCatalog(StoreChain chain, String query);
}
