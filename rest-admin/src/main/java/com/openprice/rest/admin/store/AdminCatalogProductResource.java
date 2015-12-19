package com.openprice.rest.admin.store;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.openprice.domain.store.CatalogProduct;
import com.openprice.rest.LinkBuilder;
import com.openprice.rest.admin.AdminApiUrls;

public class AdminCatalogProductResource extends Resource<CatalogProduct> {

    public AdminCatalogProductResource(CatalogProduct catalog) {
        super(catalog);
    }

    @Component
    public static class Assembler implements ResourceAssembler<CatalogProduct, AdminCatalogProductResource>, AdminApiUrls {

        @Override
        public AdminCatalogProductResource toResource(CatalogProduct catalog) {
            final AdminCatalogProductResource resource = new AdminCatalogProductResource(catalog);

            final String[] pairs = {"chainId", catalog.getChain().getId(), "catalogId", catalog.getId()};
            final LinkBuilder linkBuilder = new LinkBuilder(resource);
            linkBuilder.addLink(Link.REL_SELF, URL_ADMIN_CHAINS_CHAIN_CATALOGS_CATALOG, false, pairs)
                       .addLink("chain", URL_ADMIN_CHAINS_CHAIN, false, pairs)
                       ;
            return resource;
        }

    }

}
