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

public class AdminStoreCatalogApiDocumentation extends AdminStoreApiDocumentationBase {

    @Test
    public void adminCatalogListExample() throws Exception {
        mockMvc
        .perform(get(getCatalogsUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-store-catalog-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.catalogProducts").description("An array of <<resources-admin-store-catalog, Admin Store Catalog resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-admin-store-catalog-list-links, Links>> to other resources")
            )
        ));
    }

    @Test
    public void adminStoreCatalogCreateExample() throws Exception {
        final AdminCatalogForm form =
                AdminCatalogForm.builder()
                                .name("Pork")
                                .number("56789")
                                .price("10.99")
                                .naturalName("Ground pork")
                                .labelCodes("food,meat,pork")
                                .productCategory("meat")
                                .build();
        mockMvc
        .perform(
            post(getCatalogsUrl())
            .with(user(ADMINNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isCreated())
        .andDo(document("admin-store-catalog-create-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("name").description("The catalog name."),
                fieldWithPath("number").description("The catalog number."),
                fieldWithPath("price").description("The price (unit price?)."),
                fieldWithPath("naturalName").description("Readable name for the catalog."),
                fieldWithPath("labelCodes").description("The labels of the catalog."),
                fieldWithPath("productCategory").description("The category this catalog product belongs to.")
            )
        ));
    }

    @Test
    public void adminStoreCatalogRetrieveExample() throws Exception {
        mockMvc
        .perform(get(getTestCatalogUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-store-catalog-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("chain").description("<<resources-admin-chain, Link>> to store chain this catalog belong to.")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("catalogCode").description("The catalog code."),
                fieldWithPath("price").description("The price (unit price?)."),
                fieldWithPath("naturalName").description("Readable name for the catalog."),
                fieldWithPath("labelCodes").description("The labels of the catalog."),
                fieldWithPath("productCategory").description("The category this catalog product belongs to."),
                fieldWithPath("_links").description("<<resources-admin-store-catalog-links, Links>> to other resources")
            )
        ));
    }

    @Test
    public void adminStoreBranchUpdateExample() throws Exception {
        final AdminCatalogForm form =
                AdminCatalogForm.builder()
                                .name("Pork")
                                .number("56789")
                                .price("10.99")
                                .naturalName("Ground pork")
                                .labelCodes("food,meat,pork")
                                .productCategory("meat")
                                .build();
        mockMvc
        .perform(
            put(getTestCatalogUrl())
            .with(user(ADMINNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isNoContent())
        .andDo(document("admin-store-catalog-update-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                    fieldWithPath("name").description("The catalog name."),
                    fieldWithPath("number").description("The catalog number."),
                    fieldWithPath("price").description("The price (unit price?)."),
                    fieldWithPath("naturalName").description("Readable name for the catalog."),
                    fieldWithPath("labelCodes").description("The labels of the catalog."),
                    fieldWithPath("productCategory").description("The category this catalog product belongs to.")
            )
        ));
    }

    @Test
    public void adminStoreCatalogDeleteExample() throws Exception {
        mockMvc
        .perform(delete(getTestCatalogUrl()).with(user(ADMINNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("admin-store-catalog-delete-example"));
    }


}
