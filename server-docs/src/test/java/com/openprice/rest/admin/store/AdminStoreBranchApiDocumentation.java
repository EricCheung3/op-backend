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

public class AdminStoreBranchApiDocumentation extends AdminStoreApiDocumentationBase {

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
}
