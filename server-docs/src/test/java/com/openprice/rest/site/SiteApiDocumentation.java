package com.openprice.rest.site;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;

import com.openprice.common.ApiConstants;
import com.openprice.rest.ApiDocumentationBase;


@WithMockUser
public class SiteApiDocumentation extends ApiDocumentationBase {

    @Test
    public void websiteExample() throws Exception {
        mockMvc
        .perform(get(ApiConstants.EXTERNAL_API_ROOT))
        .andExpect(status().isOk())
        .andDo(document("website-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("signin").description("The user login url"),
                linkWithRel("registration").description("The <<resources-registration, Registration resource>>"),
                linkWithRel("forgetPassword").description("The <<resources-forget-password, Forget Password resource>>"),
                linkWithRel("resetPassword").description("The <<resources-reset-password, Reset Password resource>>")
            ),
            responseFields(
                fieldWithPath("authenticated").description("Whether the user is authenticated."),
                fieldWithPath("currentUser").description("Current logged in user"),
                fieldWithPath("_links").description("<<resources-website-links,Links>> to other resources")
            )
        ));
    }

}
