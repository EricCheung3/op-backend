package com.openprice.rest.admin.store;

import org.springframework.hateoas.Resource;

import com.openprice.domain.store.StoreBranch;

public class AdminStoreBranchResource extends Resource<StoreBranch> {

    public AdminStoreBranchResource(final StoreBranch storeBranch) {
        super(storeBranch);
    }

}
