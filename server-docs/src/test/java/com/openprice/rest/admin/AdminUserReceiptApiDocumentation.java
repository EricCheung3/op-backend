package com.openprice.rest.admin;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.jsonpath.JsonPath;

public class AdminUserReceiptApiDocumentation extends AdminApiDocumentationBase {

    @Test
    public void adminUserReceiptListExample() throws Exception {
        mockMvc
        .perform(get(userReceiptsUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-user-receipt-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receipts").description("An array of <<resources-admin-user-receipt, Admin User Receipt resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-admin-user-receipt-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void adminUserReceiptRetrieveExample() throws Exception {
        mockMvc
        .perform(get(testUserReceiptUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-user-receipt-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("user").description("The <<resources-admin-user, Admin User resource>>, owner of this receipt"),
                linkWithRel("images").description("<<resources-admin-user-receipt-images, Link>> to receipt images"),
                linkWithRel("image").description("<<resources-admin-user-receipt-image, Link>> to receipt image")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("_embedded.receiptImages").description("Receipt image list"),
                fieldWithPath("status").description("Receipt process status, can be WAIT_FOR_RESULT, OCR_ERROR, PARSER_ERROR, HAS_RESULT"),
                fieldWithPath("needFeedback").description("Whether user can give feedback."),
                fieldWithPath("_links").description("<<resources-admin-user-receipt-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void adminUserReceiptDeleteExample() throws Exception {
        mockMvc
        .perform(delete(testUserReceiptUrl()).with(user(ADMINNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("admin-user-receipt-delete-example"));

    }

    @Test
    public void adminUserReceiptImageListExample() throws Exception {
        mockMvc
        .perform(get(testUserReceiptImagesUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-user-receipt-image-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receiptImages").description("An array of <<resources-admin-user-receipt-image, Admin User Receipt Image resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-admin-user-receipt-image-list-links, Links>> to other resources")
            )
        ));

    }

    @Test
    public void adminUserReceiptImageRetrieveExample() throws Exception {
        mockMvc
        .perform(get(getTestUserReceiptImageUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-user-receipt-image-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("receipt").description("<<resources-admin-user-receipt, Link>> to owner receipt resource"),
                linkWithRel("download").description("URL for downloading image as jpg file"),
                linkWithRel("base64").description("URL for downloading image as base64 text string")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("status").description("Receipt image process status"),
                fieldWithPath("ocrResult").description("Receipt image ocr process result"),
                fieldWithPath("fileName").description("Receipt image file name"),
                fieldWithPath("_links").description("<<resources-admin-user-receipt-image-retrieve-links,Links>> to other resources")
            )
        ));
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestAdmin();
        createTestUser();
        createReceipts();
    }

    @After
    public void teardown() throws Exception {
        deleteReceipts();
        deleteTestAdmin();
        deleteTestUser();
    }

    private String userReceiptsUrl() throws Exception {
        final String responseContent =
                mockMvc
                .perform(get(usersUrl()).with(user(ADMINNAME)))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
        final String receiptsLink = JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.receipts.href");
        return UriTemplate.fromTemplate(receiptsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String testUserReceiptUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receipts[0]._links.self.href");
    }

    private String testUserReceiptImagesUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(testUserReceiptUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String imagesLink = JsonPath.read(responseContent, "_links.images.href");
        return UriTemplate.fromTemplate(imagesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String getTestUserReceiptImageUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(testUserReceiptImagesUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receiptImages[0]._links.self.href");
    }

}
