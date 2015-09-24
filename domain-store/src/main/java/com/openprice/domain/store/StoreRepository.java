package com.openprice.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, String> {

    Store findByCode(String code);

    Store findByName(String name); // TODO remove it
}
