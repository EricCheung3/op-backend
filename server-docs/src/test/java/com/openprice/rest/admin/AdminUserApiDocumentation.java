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
import com.openprice.rest.user.UserProfileForm;

public class AdminUserApiDocumentation extends AdminApiDocumentationBase {

    @Test
    public void adminUserListExample() throws Exception {
        mockMvc
        .perform(get(usersUrl()).with(user(ADMINNAME)))
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
    public void adminUserRetrieveExample() throws Exception {
        mockMvc
        .perform(get(testUserUrl()).with(user(ADMINNAME)))
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
    }

    @Test
    public void adminUserLockStateUpdateExample() throws Exception {
        final Map<String, Boolean> lockState = new HashMap<>();
        lockState.put("locked", true);
        mockMvc
        .perform(
            put(testUserLockStateUrl())
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

    @Test
    public void adminUserProfileExample() throws Exception {
        mockMvc
        .perform(get(testUserProfileUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-user-profile-retrieve-example",
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
    public void adminUserProfileUpdateExample() throws Exception {
        UserProfileForm form =
            UserProfileForm.builder()
                           .firstName("Jonny")
                           .lastName("Doe")
                           .middleName("")
                           .phone("780-888-1234")
                           .address1("GroundTruth Inc.")
                           .address2("123 Street")
                           .city("Edmonton")
                           .state("AB")
                           .zip("")
                           .country("Canada")
                           .build();
        mockMvc
        .perform(
            put(testUserProfileUrl())
            .with(user(ADMINNAME))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isNoContent())
        .andDo(document("admin-user-profile-update-example",
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
        createTestAdmin();
        createTestUser();
    }

    @After
    public void teardown() throws Exception {
        deleteTestAdmin();
        deleteTestUser();
    }

    private String testUserLockStateUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(usersUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.lockState.href");
    }

    private String testUserProfileUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(usersUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.profile.href");
    }

}
