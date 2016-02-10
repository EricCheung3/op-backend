package com.openprice.rest.admin;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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

public class AdminReceiptResultApiDocumentation extends AdminApiDocumentationBase {

    @Test
    public void adminReceiptResultListExample() throws Exception {
        mockMvc
        .perform(get(getReceiptResultsUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-receipt-result-list-example",
                preprocessResponse(prettyPrint()),
                links(
                    linkWithRel("self").description("The self link")
                ),
                responseFields(
                    fieldWithPath("_embedded.receiptResults").description("An array of <<resources-admin-receipt-result, Admin Receipt result resources>>"),
                    fieldWithPath("page").description("Pagination data"),
                    fieldWithPath("_links").description("<<resources-admin-receipt-result-links,Links>> to other resources")
                )
            ));
    }

    @Test
    public void adminReceiptResultExample() throws Exception {
        mockMvc
        .perform(get(getReceiptResultUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-receipt-result-retrieve-example",
                preprocessResponse(prettyPrint()),
                links(
                    linkWithRel("self").description("The self link"),
                    linkWithRel("items").description("<<resources-admin-receipt-result-items, Link>> to receipt result items"),
                    linkWithRel("item").description("<<resources-admin-receipt-result-item, Link>> to receipt result items")
                ),
                responseFields(
                    fieldWithPath("id").description("Primary ID"),
                    fieldWithPath("chainCode").description("The chain code of receipt"),
                    fieldWithPath("branchName").description("The branch name of receipt"),
                    fieldWithPath("parsedTotal").description("The parsed total of receipt"),
                    fieldWithPath("parsedDate").description("The parsed date of receipt"),
                    fieldWithPath("fieldMap").description("The parsed result fields of receipt"),
                    fieldWithPath("_links").description("<<resources-admin-receipt-result-retrieve-links,Links>> to other resources"),
                    fieldWithPath("_embedded.receiptItems").description("An array of <<resources-admin-receipt-result-items, Admin Receipt result items resources>>")
                )
            ));
    }

    @Test
    public void adminReceiptResultItemListExample() throws Exception {
        mockMvc
        .perform(get(getReceiptResultItemsUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-receipt-result-item-list-example",
                preprocessResponse(prettyPrint()),
                links(
                    linkWithRel("self").description("The self link")
                ),
                responseFields(
                    fieldWithPath("_embedded.receiptItems").description("An array of <<resources-admin-receipt-result-item, Admin Receipt result item resources>>"),
                    fieldWithPath("page").description("Pagination data"),
                    fieldWithPath("_links").description("<<resources-admin-receipt-result-item-links,Links>> to other resources")
                )
            ));
    }

    @Test
    public void adminReceiptResultItemExample() throws Exception {
        mockMvc
        .perform(get(getReceiptResultItemUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-receipt-result-item-retrieve-example",
                preprocessResponse(prettyPrint()),
                links(
                    linkWithRel("self").description("The self link"),
                    linkWithRel("result").description("<<resources-admin-receipt-result, Link>> to receipt result resource>>"),
                    linkWithRel("items").description("<<resources-admin-receipt-result-items, Link>> to receipt result items resource>>")
                ),
                responseFields(
                    fieldWithPath("id").description("Primary ID"),
                    fieldWithPath("catalogCode").description("The catalog code of receipt item"),
                    fieldWithPath("parsedName").description("The parsed name of receipt item"),
                    fieldWithPath("displayName").description("The display name of receipt item"),
                    fieldWithPath("parsedPrice").description("The parsed price of receipt item"),
                    fieldWithPath("displayPrice").description("The display price of receipt item"),
                    fieldWithPath("lineNumber").description("The line number of receipt item"),
                    fieldWithPath("userDeleted").description("User can delete the item or not"),
                    fieldWithPath("catalog").description("The catalog of receipt item"),
                    fieldWithPath("_links").description("<<resources-admin-receipt-result-item-retrieve-links,Links>> to other resources")
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

    private String getReceiptResultsUrl() throws Exception{
        final String responseContent =
                mockMvc
                .perform(get(getReceiptsUrl()).with(user(ADMINNAME)))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
        final String resultsLink = JsonPath.read(responseContent, "_embedded.receipts[0]._links.results.href");
        return UriTemplate.fromTemplate(resultsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String getReceiptResultUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getReceiptResultsUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receiptResults[0]._links.self.href");
    }

    private String getReceiptResultItemsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getReceiptResultUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String itemsLink = JsonPath.read(responseContent, "_links.items.href");
        return UriTemplate.fromTemplate(itemsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String getReceiptResultItemUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getReceiptResultItemsUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receiptItems[0]._links.self.href");
    }
}
