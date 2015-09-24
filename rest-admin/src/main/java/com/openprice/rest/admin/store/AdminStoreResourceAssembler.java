package com.openprice.rest.admin.store;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.store.Store;

@Component
public class AdminStoreResourceAssembler implements ResourceAssembler<Store, AdminStoreResource> {

    @Override
    public AdminStoreResource toResource(Store store) {
        // TODO add links
        return null;
    }

}
