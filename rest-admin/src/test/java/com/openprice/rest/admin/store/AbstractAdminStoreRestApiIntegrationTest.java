package com.openprice.rest.admin.store;

import static com.jayway.restassured.RestAssured.given;

import javax.inject.Inject;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.filter.session.SessionFilter;
import com.openprice.common.ApiConstants;
import com.openprice.domain.store.CatalogRepository;
import com.openprice.domain.store.StoreBranchRepository;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.rest.admin.AbstractAdminRestApiIntegrationTest;

public abstract class AbstractAdminStoreRestApiIntegrationTest extends AbstractAdminRestApiIntegrationTest {

    @Inject
    protected StoreChainRepository storeRepository;

    @Inject
    protected StoreBranchRepository storeBranchRepository;

    @Inject
    protected CatalogRepository catalogRepository;

    protected String storeChainsUrl(final SessionFilter sessionFilter) {
        final String chainsLink =
            given().filter(sessionFilter)
                   .when().get(ApiConstants.EXTERNAL_API_ROOT + URL_ADMIN)
                   .then().extract().path("_links.chains.href");
        return UriTemplate.fromTemplate(chainsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String storeChainUrl(final SessionFilter sessionFilter, final String chainId) {
        final String chainLink =
            given().filter(sessionFilter)
                   .when().get(ApiConstants.EXTERNAL_API_ROOT + URL_ADMIN)
                   .then().extract().path("_links.chain.href");
        return UriTemplate.fromTemplate(chainLink).set("chainId", chainId).expand();
    }

    protected String storeBranchesUrl(final SessionFilter sessionFilter, final String chainId) {
        final String branchesLink =
            given().filter(sessionFilter)
                   .when().get(storeChainUrl(sessionFilter, chainId))
                   .then().extract().path("_links.branches.href");
        return UriTemplate.fromTemplate(branchesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String storeBranchUrl(final SessionFilter sessionFilter, final String chainId, final String branchId) {
        final String branchLink =
            given().filter(sessionFilter)
                   .when().get(storeChainUrl(sessionFilter, chainId))
                   .then().extract().path("_links.branch.href");
        return UriTemplate.fromTemplate(branchLink).set("branchId", branchId).expand();
    }

    protected String catalogsUrl(final SessionFilter sessionFilter, final String chainId) {
        final String catalogsLink =
            given().filter(sessionFilter)
                   .when().get(storeChainUrl(sessionFilter, chainId))
                   .then().extract().path("_links.catalogs.href");
        return UriTemplate.fromTemplate(catalogsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String catalogUrl(final SessionFilter sessionFilter, final String chainId, final String catalogId) {
        final String catalogLink =
            given().filter(sessionFilter)
                   .when().get(storeChainUrl(sessionFilter, chainId))
                   .then().extract().path("_links.catalog.href");
        return UriTemplate.fromTemplate(catalogLink).set("catalogId", catalogId).expand();
    }

    protected String uploadUrl(final SessionFilter sessionFilter, final String chainId) {
        return
            given().filter(sessionFilter)
                   .when().get(storeChainUrl(sessionFilter, chainId))
                   .then().extract().path("_links.upload.href");
    }

}
