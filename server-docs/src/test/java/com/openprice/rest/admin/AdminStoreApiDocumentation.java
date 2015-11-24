package com.openprice.rest.admin;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.jsonpath.JsonPath;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.store.AdminStoreBranchForm;
import com.openprice.rest.admin.store.AdminStoreChainForm;

public class AdminStoreApiDocumentation extends AdminApiDocumentationBase {
    @Test
    public void adminStoreChainListExample() throws Exception {
        mockMvc
        .perform(get(getStoreChainsUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-store-chain-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.storeChains").description("An array of <<resources-admin-store-chain, Admin Store Chain resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-admin-store-chain-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void adminStoreChainCreateExample() throws Exception {
        final AdminStoreChainForm form =
            AdminStoreChainForm.builder()
                               .code("BostonPizza")
                               .name("Boston Pizza")
                               .categories("Restaurant")
                               .identifyFields("Food")
                               .build();
        mockMvc
        .perform(
            post(getStoreChainsUrl())
            .with(user(ADMINNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isCreated())
        .andDo(document("admin-store-chain-create-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("code").description("The unique code of the new store chain."),
                fieldWithPath("name").description("The store chain name."),
                fieldWithPath("categories").description("The store chain categories."),
                fieldWithPath("identifyFields").description("The store chain identify fields for chain matching.")
            )
        ));
    }

    @Test
    public void adminStoreChainRetrieveExample() throws Exception {
        mockMvc
        .perform(get(getTestChainUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-store-chain-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("branches").description("<<resources-admin-store-branches, Link>> to store branches"),
                linkWithRel("branch").description("<<resources-admin-store-branch, Link>> to store branch"),
                linkWithRel("catalogs").description("<<resources-admin-catalogs, Link>> to store catalogs"),
                linkWithRel("catalog").description("<<resources-admin-catalog, Link>> to store catalog"),
                linkWithRel("upload").description("<<resources-admin-catalogs-upload, Link>> to upload store catalogs")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("code").description("Store chain unique code"),
                fieldWithPath("name").description("Store chain name."),
                fieldWithPath("categories").description("Categories. Not used now"),
                fieldWithPath("identifyFields").description("Comma separated strings used to match chain"),
                fieldWithPath("catalogUploadUrl").description("URL for uploading catalog json files"),
                fieldWithPath("_links").description("<<resources-admin-store-chain-links, Links>> to other resources")
            )
        ));
    }

    @Test
    public void adminStoreChainUpdateExample() throws Exception {
        final AdminStoreChainForm form =
            AdminStoreChainForm.builder()
                               .code("BostonPizza")
                               .name("Boston Pizza")
                               .categories("Restaurant")
                               .identifyFields("Food")
                               .build();
        mockMvc
        .perform(
            put(getTestChainUrl())
            .with(user(ADMINNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isNoContent())
        .andDo(document("admin-store-chain-update-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("code").description("The unique code of the new store chain."),
                fieldWithPath("name").description("The store chain name."),
                fieldWithPath("categories").description("The store chain categories."),
                fieldWithPath("identifyFields").description("The store chain identify fields for chain matching.")
            )
        ));
    }

    @Test
    public void adminStoreChainDeleteExample() throws Exception {
        mockMvc
        .perform(delete(getTestChainUrl()).with(user(ADMINNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("admin-store-chain-delete-example"));
    }

    @Test
    public void adminStoreBranchListExample() throws Exception {
        mockMvc
        .perform(get(getStoreBranchesUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-store-branch-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.storeBranches").description("An array of <<resources-admin-store-branch, Admin Store Branch resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-admin-store-branch-list-links, Links>> to other resources")
            )
        ));
    }

    @Test
    public void adminStoreBranchCreateExample() throws Exception {
        final AdminStoreBranchForm form =
            AdminStoreBranchForm.builder()
                                .name("Windermere RCSS")
                                .phone("780-438-9235")
                                .address1("1155, Windermere Way SW")
                                .city("Edmonton")
                                .state("AB")
                                .country("Canada")
                                .build();
        mockMvc
        .perform(
            post(getStoreBranchesUrl())
            .with(user(ADMINNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isCreated())
        .andDo(document("admin-store-branch-create-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("name").description("The store branch name."),
                fieldWithPath("gstNumber").description("The store branch GST Number."),
                fieldWithPath("phone").description("The store branch phone."),
                fieldWithPath("address1").description("The store branch address line 1."),
                fieldWithPath("address2").description("The store branch address line 2."),
                fieldWithPath("city").description("The store branch address city."),
                fieldWithPath("state").description("The store branch address state/province."),
                fieldWithPath("zip").description("The store branch address postal code."),
                fieldWithPath("country").description("The store branch address country.")
            )
        ));
    }

    @Test
    public void adminStoreBranchRetrieveExample() throws Exception {
        mockMvc
        .perform(get(getTestBranchUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-store-branch-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("chain").description("<<resources-admin-chain, Link>> to store chain this branch belong to.")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("name").description("Store branch name."),
                fieldWithPath("gstNumber").description("Store branch GST number"),
                fieldWithPath("phone").description("Store branch phone number"),
                fieldWithPath("address").description("Store branch address"),
                fieldWithPath("_links").description("<<resources-admin-store-branch-links, Links>> to other resources")
            )
        ));
    }

    @Test
    public void adminStoreBranchUpdateExample() throws Exception {
        final AdminStoreBranchForm form =
            AdminStoreBranchForm.builder()
                                .name("Windermere RCSS")
                                .phone("780-438-9235")
                                .address1("1155, Windermere Way SW")
                                .city("Edmonton")
                                .state("AB")
                                .country("Canada")
                                .build();
        mockMvc
        .perform(
            put(getTestBranchUrl())
            .with(user(ADMINNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isNoContent())
        .andDo(document("admin-store-branch-update-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                    fieldWithPath("name").description("The store branch name."),
                    fieldWithPath("gstNumber").description("The store branch GST Number."),
                    fieldWithPath("phone").description("The store branch phone."),
                    fieldWithPath("address1").description("The store branch address line 1."),
                    fieldWithPath("address2").description("The store branch address line 2."),
                    fieldWithPath("city").description("The store branch address city."),
                    fieldWithPath("state").description("The store branch address state/province."),
                    fieldWithPath("zip").description("The store branch address postal code."),
                    fieldWithPath("country").description("The store branch address country.")
            )
        ));
    }

    @Test
    public void adminStoreBranchDeleteExample() throws Exception {
        mockMvc
        .perform(delete(getTestBranchUrl()).with(user(ADMINNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("admin-store-branch-delete-example"));
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestAdmin();
        createStores();
    }

    @After
    public void teardown() throws Exception {
        deleteReceipts();
        deleteTestAdmin();
        deleteStores();
    }

    private String getStoreChainsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String chainsLink = JsonPath.read(responseContent, "_links.chains.href");
        return UriTemplate.fromTemplate(chainsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String getTestChainUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getStoreChainsUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.storeChains[0]._links.self.href");
    }

    private String getStoreBranchesUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getTestChainUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String branchesLink = JsonPath.read(responseContent, "_links.branches.href");
        return UriTemplate.fromTemplate(branchesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String getTestBranchUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getStoreBranchesUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.storeBranches[0]._links.self.href");
    }

}
