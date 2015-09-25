package com.openprice.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreChainRepository extends JpaRepository<StoreChain, String> {

    StoreChain findByCode(String code);
}
