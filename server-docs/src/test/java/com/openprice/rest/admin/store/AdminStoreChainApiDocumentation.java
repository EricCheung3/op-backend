package com.openprice.rest.admin.store;

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

import org.junit.Test;
import org.springframework.http.MediaType;

public class AdminStoreChainApiDocumentation extends AdminStoreApiDocumentationBase {

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
                fieldWithPath("name").description("The store chain name.")
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
                fieldWithPath("name").description("The store chain name.")
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
}
