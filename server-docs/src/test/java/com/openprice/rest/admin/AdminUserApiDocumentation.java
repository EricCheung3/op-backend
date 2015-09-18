package com.openprice.rest.admin;

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

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import com.jayway.jsonpath.JsonPath;
import com.openprice.rest.ApiDocumentationBase;
import com.openprice.rest.UtilConstants;

public class AdminUserApiDocumentation extends ApiDocumentationBase {

    @Test
    public void adminUsersExample() throws Exception {
        mockMvc
        .perform(get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-user-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.userAccounts").description("An array of <<resources-admin-user,User resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-admin-user-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void adminUserExample() throws Exception {
        String responseContent =
            mockMvc
            .perform(get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_USERS).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String userLink = JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.self.href");

        mockMvc
        .perform(get(userLink).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-user-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("profile").description("The <<resources-admin-user-profile, Admin User Profile resource>>"),
                linkWithRel("receipts").description("The <<resources-admin-user-receipts, Admin User Receipts resource>>"),
                linkWithRel("receipt").description("The <<resources-admin-user-receipt, Admin User Receipt resource>>"),
                linkWithRel("lockState").description("The <<resources-admin-user-lockState, Admin User Lock State resource>>")
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
                fieldWithPath("_links").description("<<resources-admin-user-links, Links>> to other resources")
            )
        ));

        final Map<String, Boolean> lockState = new HashMap<>();
        lockState.put("locked", true);

        String userLockStateLink = JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.lockState.href");
        mockMvc
        .perform(
            put(userLockStateLink)
            .with(user(ADMINNAME))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(lockState))
        )
        .andExpect(status().isNoContent())
        .andDo(document("admin-user-lockState-update-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("locked").description("User locked state, can be true or false")
            )
        ));

    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestAdmin();
        createTestUser();

    }

    @After
    public void teardown() throws Exception {
        deleteTestAdmin();
        deleteTestUser();
    }

}