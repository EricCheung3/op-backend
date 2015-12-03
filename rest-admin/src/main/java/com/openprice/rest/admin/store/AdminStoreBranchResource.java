package com.openprice.rest.admin.store;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.store.StoreBranch;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

public class AdminStoreBranchResource extends Resource<StoreBranch> {

    public AdminStoreBranchResource(final StoreBranch storeBranch) {
        super(storeBranch);
    }

    @Component
    public static class Assembler implements ResourceAssembler<StoreBranch, AdminStoreBranchResource>, AdminApiUrls {

        @Override
        public AdminStoreBranchResource toResource(StoreBranch storeBranch) {
            final AdminStoreBranchResource resource = new AdminStoreBranchResource(storeBranch);
            final String[] pairs = {"chainId", storeBranch.getChain().getId(), "branchId", storeBranch.getId()};
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_CHAINS_CHAIN_BRANCHES_BRANCH, false, pairs)
                       .addLink("chain", URL_ADMIN_CHAINS_CHAIN, false, pairs)
                       ;
            return resource;
        }

    }

}
