package com.openprice.rest.site;

import static org.springframework.restdocs.RestDocumentation.modifyResponseTo;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.restdocs.response.ResponsePostProcessors;
import org.springframework.security.test.context.support.WithMockUser;

import com.openprice.rest.ApiDocumentationBase;

@WithMockUser
public class SiteApiDocumentation extends ApiDocumentationBase {

    @Test
    public void websiteExample() throws Exception {
        this.mockMvc.perform(get("/api"))
            .andExpect(status().isOk())
            .andDo(modifyResponseTo(ResponsePostProcessors.prettyPrintContent()).andDocument("website-example")
                .withLinks(
                    linkWithRel("self").description("The self link"),
                    linkWithRel("signin").description("The <<resources-signin,Signin resource>>"),
                    linkWithRel("registration").description("The <<resources-registration,Registration resource>>")
                )
                .withResponseFields(
                    fieldWithPath("authenticated").description("Whether the user is authenticated."),
                    fieldWithPath("currentUser").description("Current logged in user"),
                    fieldWithPath("_links").description("<<resources-website-links,Links>> to other resources")
                )
            );
    }

}
