package com.openprice.rest.user.receipt;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class UserReceiptApiDocumentation extends UserReceiptApiDocumentationBase {

    @Test
    public void receiptListExample() throws Exception {
        mockMvc
        .perform(get(userReceiptsUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receipts").description("An array of <<resources-user-receipt,Receipt resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-user-receipt-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void uploadReceiptExample() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "base64codedimg".getBytes());

        mockMvc
        .perform(
            fileUpload(userReceiptUploadUrl())
            .file(file)
            .param("filename", "test.jpg")
            .with(user(USERNAME))
        )
        .andExpect(status().isCreated())
        .andDo(document("user-receipt-upload-example",
            requestParameters(
                parameterWithName("filename").description("The uploaded image file name")
            )
        ));
    }

    @Test
    public void receiptRetrieveExample() throws Exception {
        mockMvc
        .perform(get(userReceiptUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("images").description("<<resources-user-receipt-images, Link>> to receipt images"),
                linkWithRel("image").description("<<resources-user-receipt-image, Link>> to receipt image"),
                linkWithRel("result").description("<<resources-user-receipt-result, Link>> to receipt latest parser result"),
                linkWithRel("items").description("<<resources-user-receipt-items, Link>> to receipt latest parser result items"),
                linkWithRel("item").description("<<resources-user-receipt-item, Link>> to receipt parser result item"),
                linkWithRel("upload").description("<<resources-user-receipt-image-upload, Link>> to upload more image for this receipt"),
                linkWithRel("feedback").description("<<resources-user-receipt-feedback, Link>> to receipt feedback")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("_embedded.receiptImages").description("Receipt image list"),
                fieldWithPath("status").description("Receipt process status, can be WAIT_FOR_RESULT, OCR_ERROR, PARSER_ERROR, HAS_RESULT"),
                fieldWithPath("receiptDate").description("Date of receipt, default to upload date, change to shopping date after processing"),
                fieldWithPath("total").description("Total price from parser, null if no result yet."),
                fieldWithPath("chainCode").description("Parser result store chain code, 'generic' if no result."),
                fieldWithPath("storeName").description("Parser result store chain name, [Unknown] if no result yet."),
                fieldWithPath("needFeedback").description("Whether user can give feedback"),
                fieldWithPath("_links").description("<<resources-user-receipt-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void receiptFeedbackCreateExample() throws Exception {
        final FeedbackForm form = new FeedbackForm(1, "Poor result!");

        mockMvc
        .perform(
            post(userReceiptFeedbackUrl())
            .with(user(USERNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isNoContent())
        .andDo(document("user-receipt-feedback-create-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("rating").description("The user rating for the receipt, right now we only use 1 or 0 to indicate good or bad."),
                fieldWithPath("comment").description("The user comment for the receiptd.")
            )
        ));
    }

    @Test
    public void receiptDeleteExample() throws Exception {
        mockMvc
        .perform(delete(userReceiptUrl()).with(user(USERNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("user-receipt-delete-example"));
    }

}
