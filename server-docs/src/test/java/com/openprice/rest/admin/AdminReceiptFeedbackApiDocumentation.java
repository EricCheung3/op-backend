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

public class AdminReceiptFeedbackApiDocumentation extends AdminApiDocumentationBase {

    @Test
    public void adminReceiptFeedbackListExample() throws Exception {
        mockMvc
        .perform(get(getReceiptFeedbacksUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-receipt-feedback-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receiptFeedbacks").description("An array of <<resources-admin-receipt-feedback, Admin Receipt feedback resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-admin-receipt-feedback-links,Links>> to other resources")
            )
        ));
    }
    
    @Test
    public void adminReceiptFeedbackRetrieveExample() throws Exception {
        mockMvc
        .perform(get(getTestReceiptFeedbackUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-receipt-feedback-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("receipt").description("<<resources-admin-receipt, Link>> to owner receipt resource")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("rating").description("Receipt rating"),
                fieldWithPath("comment").description("Receipt comment"),
                fieldWithPath("_links").description("<<resources-admin-receipt-feedback-retrieve-links,Links>> to other resources")
                
            )
        ));

    }
    
    @Test
    public void adminReceiptFeedbackDeleteExample() throws Exception {
        mockMvc
        .perform(delete(getTestReceiptFeedbackUrl()).with(user(ADMINNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("admin-receipt-feedback-delete-example"));
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
    
    private String getReceiptFeedbacksUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getReceiptsUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String feedbacksLink = JsonPath.read(responseContent, "_embedded.receipts[0]._links.feedbacks.href");
        return UriTemplate.fromTemplate(feedbacksLink).set("page", null).set("size", null).set("sort", null).expand();
    }
    
    private String getTestReceiptFeedbackUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(getReceiptFeedbacksUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
//        System.out.println(responseContent);
        return JsonPath.read(responseContent, "_embedded.receiptFeedbacks[0]._links.self.href");
    }
}
