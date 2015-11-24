package com.openprice.rest.admin.store;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.store.StoreChain;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

@Component
public class AdminStoreChainResourceAssembler implements ResourceAssembler<StoreChain, AdminStoreChainResource>, AdminApiUrls {

    @Override
    public AdminStoreChainResource toResource(StoreChain chain) {
        final AdminStoreChainResource resource = new AdminStoreChainResource(chain);
        final String[] pairs = {"chainId", chain.getId()};
        final LinkBuilder linkBuilder = new LinkBuilder(resource);
        linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_CHAINS_CHAIN, false, pairs)
                   .addLink("branches", URL_ADMIN_CHAINS_CHAIN_BRANCHES, true, pairs)
                   .addLink("branch", URL_ADMIN_CHAINS_CHAIN_BRANCHES_BRANCH, false, pairs)
                   .addLink("catalogs", URL_ADMIN_CHAINS_CHAIN_CATALOGS, true, pairs)
                   .addLink("catalog", URL_ADMIN_CHAINS_CHAIN_CATALOGS_CATALOG, false, pairs)
                   ;
        return resource;
    }

}
