package com.openprice.rest.admin.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.store.Catalog;

public class AdminCatalogResource extends Resource<Catalog> {

    public AdminCatalogResource(Catalog catalog) {
        super(catalog);
    }

}
