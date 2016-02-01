package com.openprice.rest.user;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import com.openprice.common.ApiConstants;

public class UserApiDocumentation extends UserApiDocumentationBase {

    @Test
    public void currentUserExample() throws Exception {

        mockMvc
        .perform(get(userUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("profile").description("The <<resources-user-profile,Profile resource>>"),
                linkWithRel("receipts").description("The <<resources-user-receipts,Receipts resource>>"),
                linkWithRel("receipt").description("The <<resources-user-receipt,Receipt resource>>"),
                linkWithRel("shoppingList").description("The <<resources-user-shoppinglist,ShoppingList resource>>"),
                linkWithRel("stores").description("The <<resources-user-stores,Stores resource>>"),
                linkWithRel("store").description("The <<resources-user-store,Store resource>>"),
                linkWithRel("categories").description("The <<resources-product-category,List of system defined product categories>>"),
                linkWithRel("upload").description("The <<resources-user-receipt-upload,Upload New Receipt resource>>")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("username").description("Unique login username, same as user email"),
                fieldWithPath("email").description("User email"),
                fieldWithPath("roles").description("User security roles"),
                fieldWithPath("accountLocked").description("Whether this user account is locked"),
                fieldWithPath("trustedAccount").description("Whether this user account is trusted"),
                fieldWithPath("activated").description("Whether this user account is activated"),
                fieldWithPath("profile").description("User profile data"),
                fieldWithPath("uploadUrl").description("URL to upload receipt image for the user"),
                fieldWithPath("receiptsUrl").description("URL for user receipts, test purpose."),
                fieldWithPath("_links").description("<<resources-user-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void profileExample() throws Exception {

        mockMvc
        .perform(get(userProfileUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-profile-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(),
            responseFields(
                fieldWithPath("firstName").description("User first name"),
                fieldWithPath("middleName").description("User middle name"),
                fieldWithPath("lastName").description("User last name"),
                fieldWithPath("phone").description("User phone number"),
                fieldWithPath("address").description("User address"),
                fieldWithPath("displayName").description("User display name")
            )
        ));
    }

    @Test
    public void profileUpdateExample() throws Exception {
        UserProfileForm form =
            UserProfileForm.builder()
                           .firstName("Jonny")
                           .build();

        mockMvc
        .perform(
            put(userProfileUrl())
            .with(user(USERNAME))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isNoContent())
        .andDo(document("user-profile-update-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("firstName").description("User first name"),
                fieldWithPath("middleName").description("User middle name"),
                fieldWithPath("lastName").description("User last name"),
                fieldWithPath("phone").description("User phone number"),
                fieldWithPath("address1").description("User address line 1"),
                fieldWithPath("address2").description("User address line 2"),
                fieldWithPath("city").description("User address city"),
                fieldWithPath("state").description("User address state"),
                fieldWithPath("zip").description("User address zip code"),
                fieldWithPath("country").description("User address country")
            )
        ));
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestUser();
    }

    @After
    public void teardown() throws Exception {
        deleteTestUser();
    }

    private String userProfileUrl() {
        return ApiConstants.EXTERNAL_API_ROOT + URL_USER_PROFILE;
    }

}
