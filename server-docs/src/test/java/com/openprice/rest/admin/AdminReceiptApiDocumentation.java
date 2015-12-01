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

public class AdminReceiptApiDocumentation extends AdminApiDocumentationBase {
    @Test
    public void adminReceiptListExample() throws Exception {
        mockMvc
        .perform(get(getReceiptsUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-receipt-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receipts").description("An array of <<resources-admin-receipt, Admin Receipt resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-admin-receipt-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void adminReceiptRetrieveExample() throws Exception {
        mockMvc
        .perform(get(getTestReceiptUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-receipt-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("images").description("<<resources-admin-receipt-images, Link>> to receipt images"),
                linkWithRel("image").description("<<resources-admin-receipt-image, Link>> to receipt image")
                //linkWithRel("items").description("<<resources-admin-receipt-items, Link>> to receipt items")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("images").description("Receipt image list"),
                fieldWithPath("needFeedback").description("Whether user can give feedback"),
                fieldWithPath("user").description("Receipt owner display name"),
                fieldWithPath("uploadTimestamp").description("ISO 8601 formatted timestamp when user uploaded the receipt"),
                fieldWithPath("_links").description("<<resources-admin-receipt-links,Links>> to other resources")
            )
        ));


    }

    @Test
    public void adminReceiptDeleteExample() throws Exception {
        mockMvc
        .perform(delete(getTestReceiptUrl()).with(user(ADMINNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("admin-receipt-delete-example"));
    }

    @Test
    public void adminReceiptImageListExample() throws Exception {
        mockMvc
        .perform(get(getTestReceiptImagesUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-receipt-image-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receiptImages").description("An array of <<resources-admin-receipt-image, Admin User Receipt Image resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-admin-receipt-image-list-links, Links>> to other resources")
            )
        ));

    }

    @Test
    public void adminReceiptImageRetrieveExample() throws Exception {
        mockMvc
        .perform(get(getTestImageUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-receipt-image-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("receipt").description("<<resources-admin-receipt, Link>> to owner receipt resource"),
                linkWithRel("download").description("URL for downloading image as jpg file"),
                linkWithRel("base64").description("URL for downloading image as base64 text string")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("status").description("Receipt image process status"),
                fieldWithPath("ocrResult").description("Receipt image ocr process result"),
                fieldWithPath("fileName").description("Receipt image file name"),
                fieldWithPath("_links").description("<<resources-admin-receipt-image-retrieve-links,Links>> to other resources")
            )
        ));
    }

    // TODO add doc for admin receipt parser result and receipt item.
//    @Test
//    public void adminReceiptItemListExample() throws Exception {
//        mockMvc
//        .perform(get(getTestReceiptItemsUrl()).with(user(USERNAME)))
//        .andExpect(status().isOk())
//        .andDo(document("admin-receipt-item-list-example",
//            preprocessResponse(prettyPrint())
//        ));
//
//    }

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

    private String getReceiptsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(adminUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String receiptsLink = JsonPath.read(responseContent, "_links.receipts.href");
        return UriTemplate.fromTemplate(receiptsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String getTestReceiptUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getReceiptsUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receipts[0]._links.self.href");
    }

    private String getTestReceiptImagesUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getTestReceiptUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String imagesLink = JsonPath.read(responseContent, "_links.images.href");
        return UriTemplate.fromTemplate(imagesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String getTestImageUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getTestReceiptImagesUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receiptImages[0]._links.self.href");
    }

    private String getTestReceiptItemsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getTestReceiptUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String imagesLink = JsonPath.read(responseContent, "_links.items.href");
        return UriTemplate.fromTemplate(imagesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

}
