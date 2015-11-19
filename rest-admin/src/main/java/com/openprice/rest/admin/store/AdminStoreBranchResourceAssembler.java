package com.openprice.rest.admin.store;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.store.StoreBranch;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

@Component
public class AdminStoreBranchResourceAssembler implements ResourceAssembler<StoreBranch, AdminStoreBranchResource>, AdminApiUrls {

    public static final String LINK_NAME_CHAIN = "chain";

    @Override
    public AdminStoreBranchResource toResource(StoreBranch storeBranch) {
        final AdminStoreBranchResource resource = new AdminStoreBranchResource(storeBranch);
        final String[] pairs = {"chainId", storeBranch.getChain().getId(), "branchId", storeBranch.getId()};
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_CHAINS_CHAIN_BRANCHES_BRANCH, false, pairs)
                   .addLink(LINK_NAME_CHAIN, URL_ADMIN_CHAINS_CHAIN, false, pairs)
                   ;
        return resource;
    }

}
