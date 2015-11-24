package com.openprice.rest.admin.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.store.StoreChain;

import lombok.Getter;
import lombok.Setter;

public class AdminStoreChainResource extends Resource<StoreChain> {

    @Getter @Setter
    private String catalogUploadUrl;

    public AdminStoreChainResource(final StoreChain chain) {
        super(chain);
    }

}
