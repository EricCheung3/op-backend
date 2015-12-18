package com.openprice.rest.admin.store;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.jsonpath.JsonPath;
import com.openprice.rest.admin.AdminApiDocumentationBase;

public abstract class AdminStoreApiDocumentationBase extends AdminApiDocumentationBase {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestAdmin();
        createStores();
    }

    @After
    public void teardown() throws Exception {
        deleteTestAdmin();
        deleteStores();
    }

    protected String getStoreChainsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(adminUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String chainsLink = JsonPath.read(responseContent, "_links.chains.href");
        return UriTemplate.fromTemplate(chainsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String getTestChainUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getStoreChainsUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.storeChains[0]._links.self.href");
    }

    protected String getStoreBranchesUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getTestChainUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String branchesLink = JsonPath.read(responseContent, "_links.branches.href");
        return UriTemplate.fromTemplate(branchesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String getTestBranchUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getStoreBranchesUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.storeBranches[0]._links.self.href");
    }

    protected String getCatalogsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getTestChainUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String catalogsLink = JsonPath.read(responseContent, "_links.catalogs.href");
        return UriTemplate.fromTemplate(catalogsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String getTestCatalogUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getCatalogsUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.catalogProducts[0]._links.self.href");
    }
}
