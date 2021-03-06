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

public class AdminApiDocumentation extends AdminApiDocumentationBase {
    @Test
    public void currentAdminExample() throws Exception {

        mockMvc
        .perform(get(adminUrl()).with(user(ADMINNAME)))
        .andExpect(status().isOk())
        .andDo(document("admin-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("users").description("The <<resources-admin-users, Admin Users resource>>"),
                linkWithRel("user").description("The <<resources-admin-user, Admin User resource>>"),
                linkWithRel("receipts").description("The <<resources-admin-receipts, Admin Receipts resource>>"),
                linkWithRel("receipt").description("The <<resources-admin-receipt, Admin Receipt resource>>"),
                linkWithRel("chains").description("The <<resources-admin-chains, Admin Store Chains resource>>"),
                linkWithRel("chain").description("The <<resources-admin-chain, Admin Store Chain resource>>")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("username").description("Unique login username"),
                fieldWithPath("email").description("Admin email"),
                fieldWithPath("profile").description("Admin profile object"),
                fieldWithPath("roles").description("Admin security roles"),
                fieldWithPath("accountLocked").description("Whether this admin account is locked"),
                fieldWithPath("activated").description("Whether this admin account is activated"),
                fieldWithPath("superAdmin").description("Whether this admin account is super admin"),
                fieldWithPath("_links").description("<<resources-admin-links,Links>> to other resources")
            )
        ));
    }


    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestAdmin();
    }

    @After
    public void teardown() throws Exception {
        deleteTestAdmin();
    }

}
