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

import com.jayway.jsonpath.JsonPath;
import com.openprice.rest.UtilConstants;

public class AdminUserReceiptApiDocumentation extends AdminApiDocumentationBase {

    @Test
    public void adminUserReceiptListExample() throws Exception {
        String responseContent =
            mockMvc
            .perform(get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String receiptsLink = JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.receipts.href");

        mockMvc
        .perform(get(receiptsLink).with(user(ADMINNAME)))
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
        String responseContent =
            mockMvc
            .perform(get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String receiptsLink = JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.receipts.href");
        responseContent =
            mockMvc
            .perform(get(receiptsLink).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String receiptLink = JsonPath.read(responseContent, "_embedded.receipts[0]._links.self.href");

        mockMvc
        .perform(get(receiptLink).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-user-receipt-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("user").description("The <<resources-admin-user, Admin User resource>>, owner of this receipt"),
                linkWithRel("images").description("<<resources-admin-user-receipt-images, Link>> to receipt images"),
                linkWithRel("image").description("<<resources-admin-user-receipt-image, Link>> to receipt image"),
                linkWithRel("items").description("<<resources-admin-user-receipt-items, Link>> to receipt items")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("images").description("Receipt image list"),
                fieldWithPath("rating").description("User rating for the receipt quality (0 or 1 for bad/good), default is null."),
                fieldWithPath("_links").description("<<resources-admin-user-receipt-links,Links>> to other resources")
            )
        ));

        mockMvc
        .perform(delete(receiptLink).with(user(ADMINNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("admin-user-receipt-delete-example"));

    }

    @Test
    public void adminUserReceiptImageListExample() throws Exception {
        String responseContent =
            mockMvc
            .perform(get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String receiptsLink = JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.receipts.href");
        responseContent =
            mockMvc
            .perform(get(receiptsLink).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String imagesLink = JsonPath.read(responseContent, "_embedded.receipts[0]._links.images.href");

        mockMvc
        .perform(get(imagesLink).with(user(ADMINNAME)))
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
        String responseContent =
            mockMvc
            .perform(get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String receiptsLink = JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.receipts.href");
        responseContent =
            mockMvc
            .perform(get(receiptsLink).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String imagesLink = JsonPath.read(responseContent, "_embedded.receipts[0]._links.images.href");
        responseContent =
            mockMvc
            .perform(get(imagesLink).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String imageLink = JsonPath.read(responseContent, "_embedded.receiptImages[0]._links.self.href");

        mockMvc
        .perform(get(imageLink).with(user(ADMINNAME)))
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

    @Test
    public void adminUserReceiptItemListExample() throws Exception {
        String responseContent =
            mockMvc
            .perform(get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String receiptsLink = JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.receipts.href");
        responseContent =
            mockMvc
            .perform(get(receiptsLink).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String itemsLink = JsonPath.read(responseContent, "_embedded.receipts[0]._links.items.href");

        mockMvc
        .perform(get(itemsLink).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-user-receipt-item-list-example",
            preprocessResponse(prettyPrint())
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

}
