package com.openprice.rest.user;

import static org.springframework.restdocs.RestDocumentation.document;
import static org.springframework.restdocs.RestDocumentation.modifyResponseTo;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.response.ResponsePostProcessors;

import com.openprice.domain.account.UserAccount;
import com.openprice.domain.account.UserProfile;
import com.openprice.rest.ApiDocumentationBase;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.common.UserProfileForm;

public class UserApiDocumentation extends ApiDocumentationBase {
    
    @Test
    public void currentUserExample() throws Exception {
                
        mockMvc
            .perform(get(UtilConstants.API_ROOT + UserApiUrls.URL_USER).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andDo(modifyResponseTo(ResponsePostProcessors.prettyPrintContent()).andDocument("user-example")
                .withLinks(
                    linkWithRel("self").description("The self link"),
                    linkWithRel("profile").description("The <<resources-user-profile,Profile resource>>"),
                    linkWithRel("receipts").description("The <<resources-user-receipts,Receipts resource>>"),
                    linkWithRel("receipt").description("The <<resources-user-receipt-retrieve,Receipt resource>>"),
                    linkWithRel("upload").description("The <<resources-user-receipt-upload,Upload resource>>")
                )
                .withResponseFields(
                    fieldWithPath("id").description("Primary ID"),
                    fieldWithPath("version").description("Entity version"),
                    fieldWithPath("createdBy").description("Who created the entity"),
                    fieldWithPath("createdTime").description("When created the entity"),
                    fieldWithPath("lastModifiedBy").description("Who last modified the entity"),
                    fieldWithPath("lastModifiedTime").description("When last modified the entity"),
                    fieldWithPath("username").description("Unique login username"),
                    fieldWithPath("email").description("User email"),
                    fieldWithPath("roles").description("User security roles"),
                    fieldWithPath("authorities").description("User security roles"),
                    fieldWithPath("accountLocked").description("Whether this user account is locked"),
                    fieldWithPath("accountNonLocked").description("Whether this user account is locked"),
                    fieldWithPath("accountNonExpired").description("Whether this user account is expired"),
                    fieldWithPath("credentialsNonExpired").description("Whether this user password is expired"),
                    fieldWithPath("trustedAccount").description("Whether this user account is trusted"),
                    fieldWithPath("trustedAccount").description("Whether this user account is trusted"),
                    fieldWithPath("activated").description("Whether this user account is activated"),
                    fieldWithPath("enabled").description("Whether this user account is enabled/activated"),
                    fieldWithPath("superAdmin").description("Whether this user account is SUPER_ADMIN"),
                    fieldWithPath("profile").description("User profile data"),
                    fieldWithPath("_links").description("<<resources-user-links,Links>> to other resources")
                )
            );
    }

    @Test
    public void profileExample() throws Exception {
                
        mockMvc
            .perform(get(UtilConstants.API_ROOT + UserApiUrls.URL_USER_PROFILE).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andDo(modifyResponseTo(ResponsePostProcessors.prettyPrintContent()).andDocument("user-profile-example")
                .withResponseFields(
                    fieldWithPath("id").description("Primary ID"),
                    fieldWithPath("version").description("Entity version"),
                    fieldWithPath("createdBy").description("Who created the entity"),
                    fieldWithPath("createdTime").description("When created the entity"),
                    fieldWithPath("lastModifiedBy").description("Who last modified the entity"),
                    fieldWithPath("lastModifiedTime").description("When last modified the entity"),
                    fieldWithPath("firstName").description("User first name"),
                    fieldWithPath("middleName").description("User middle name"),
                    fieldWithPath("lastName").description("User last name"),
                    fieldWithPath("phone").description("User phone number"),
                    fieldWithPath("address").description("User address"),
                    fieldWithPath("displayName").description("User display name")
                )
            );
    }
    
    @Test
    public void profileUpdateExample() throws Exception {
        UserAccount account = userAccountRepository.findByUsername(USERNAME);
        UserProfile profile = account.getProfile();
        UserProfileForm form = new UserProfileForm(profile);
        form.setFirstName("Jonny");
        
        mockMvc
            .perform(
                put(UtilConstants.API_ROOT + UserApiUrls.URL_USER_PROFILE)
                .with(user(USERNAME))
                .with(csrf())
                .contentType(MediaTypes.HAL_JSON).content(objectMapper.writeValueAsString(form))
            )
            .andExpect(status().isNoContent())
            .andDo(document("profile-update-example")
                .withRequestFields(
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
            );

    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestUser();
    }

    @After
    public void teardown() throws Exception {
        deleteTestUser();
    }
    
}
