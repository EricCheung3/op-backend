package com.openprice.rest.admin.store;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.store.Catalog;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

public class AdminCatalogResource extends Resource<Catalog> {

    public AdminCatalogResource(Catalog catalog) {
        super(catalog);
    }

    @Component
    public static class Assembler implements ResourceAssembler<Catalog, AdminCatalogResource>, AdminApiUrls {

        @Override
        public AdminCatalogResource toResource(Catalog catalog) {
            final AdminCatalogResource resource = new AdminCatalogResource(catalog);
            final String[] pairs = {"chainId", catalog.getChain().getId(), "catalogId", catalog.getId()};
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_CHAINS_CHAIN_CATALOGS_CATALOG, false, pairs)
                       .addLink("chain", URL_ADMIN_CHAINS_CHAIN, false, pairs)
                       ;
            return resource;
        }

    }

}
