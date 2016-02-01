package com.openprice.rest.user.receipt;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
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

public class UserReceiptResultApiDocumentation extends UserReceiptApiDocumentationBase {

    @Test
    public void receiptParserResultRetrieveExample() throws Exception {
        mockMvc
        .perform(get(userReceiptParserResultUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-parser-result-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("items").description("<<resources-user-receipt-parser-result-item-list, Link>> to Receipt Parser Result Items list resource"),
                linkWithRel("item").description("<<resources-user-receipt-parser-result-item, Link>> to Receipt Parser Result Items resource")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("chainCode").description("Recognized store chain code, maybe null"),
                fieldWithPath("storeName").description("Recognized store chain name, default to '[Unknown]'"),
                fieldWithPath("branchName").description("Recognized store branch name, maybe null"),
                fieldWithPath("parsedTotal").description("parsed field value for Total"),
                fieldWithPath("parsedDate").description("parsed field value for Date"),
                fieldWithPath("_embedded.receiptItems").description("parsed receipt items"),
                fieldWithPath("_links").description("<<resources-user-receipt-parser-result-links, Links>> to other resources")
            )
       ));
    }

    @Test
    public void receiptParserResultItemListExample() throws Exception {
        mockMvc
        .perform(get(userReceiptParserResultItemsUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-parser-result-item-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receiptItems").description("An array of <<resources-user-receipt-parser-result-item, Parser Result Receipt Item resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-user-receipt-parser-result-item-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void receiptParserResultItemRetrieveExample() throws Exception {
        mockMvc
        .perform(get(userReceiptParserResultItemUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-parser-result-item-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("result").description("<<resources-user-receipt-result, Link>> to receipt latest parser result"),
                linkWithRel("items").description("<<resources-user-receipt-items, Link>> to receipt latest parser result items")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("catalogCode").description("Parser parsed matching catalog code"),
                fieldWithPath("parsedName").description("Parser parsed item name"),
                fieldWithPath("displayName").description("User editable item name"),
                fieldWithPath("parsedPrice").description("Parser parsed item price"),
                fieldWithPath("displayPrice").description("User editable item price"),
                fieldWithPath("catalog").description("Catalog object for the item"),
                fieldWithPath("_links").description("<<resources-user-receipt-parser-result-item-retrieve-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void receiptParserResultItemUpdateExample() throws Exception {
        final UserReceiptItemForm form = new UserReceiptItemForm("eggs", "3.99");

        mockMvc
        .perform(
            put(userReceiptParserResultItemUrl())
            .with(user(USERNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isNoContent())
        .andDo(document("user-receipt-parser-result-item-update-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("name").description("User edited item name."),
                fieldWithPath("price").description("User edited item price.")
            )
        ));
    }

    @Test
    public void receiptParserResultItemDeleteExample() throws Exception {
        mockMvc
        .perform(delete(userReceiptParserResultItemUrl()).with(user(USERNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("user-receipt-parser-result-item-delete-example"));
    }

}
