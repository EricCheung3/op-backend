package com.openprice.rest.admin.store;

import org.springframework.hateoas.ResourceAssembler;

import com.openprice.domain.store.StoreBranch;

public class AdminStoreBranchResourceAssembler implements ResourceAssembler<StoreBranch, AdminStoreBranchResource> {
    @Override
    public AdminStoreBranchResource toResource(StoreBranch storeBranch) {
        // TODO add links
        return null;
    }

}
