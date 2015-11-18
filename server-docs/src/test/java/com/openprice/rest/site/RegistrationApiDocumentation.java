package com.openprice.rest.site;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.rest.ApiDocumentationBase;
import com.openprice.rest.UtilConstants;

public class RegistrationApiDocumentation extends ApiDocumentationBase {

    @Test
    public void userRegisterExample() throws Exception {
        RegistrationForm form = new RegistrationForm();
        form.setFirstName("John");
        form.setLastName("Doe");
        form.setEmail(USERNAME);
        form.setPassword("password");

        mockMvc
        .perform(
            post(UtilConstants.API_ROOT + SiteApiUrls.URL_PUBLIC_REGISTRATION)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isCreated())
        .andDo(document("registration-register-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("firstName").description("User first name"),
                fieldWithPath("lastName").description("User last name"),
                fieldWithPath("email").description("User email address as username"),
                fieldWithPath("password").description("User login password")
            )
        ));

        UserAccount account = userAccountRepository.findByEmail(USERNAME);
        userAccountRepository.delete(account);
    }

    @Test
    public void userForgetPasswordExample() throws Exception {
        createTestUser();

        ForgetPasswordForm form = new ForgetPasswordForm(USERNAME);

        mockMvc
        .perform(
            post(UtilConstants.API_ROOT + SiteApiUrls.URL_PUBLIC_RESET_PASSWORD_REQUESTS)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isCreated())
        .andDo(document("forget-password-request-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("email").description("User email address")
            )
        ));

        deleteTestUser();
    }

    @Test
    public void resetPasswordRetrieveExample() throws Exception {
        createTestUser();

        ForgetPasswordForm form = new ForgetPasswordForm(USERNAME);

        String requestLocation =
            mockMvc
            .perform(
                post(UtilConstants.API_ROOT + SiteApiUrls.URL_PUBLIC_RESET_PASSWORD_REQUESTS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(form))
            )
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");


        mockMvc
        .perform(get(requestLocation))
        .andExpect(status().isOk())
        .andDo(document("reset-password-retrieve-example",
            preprocessResponse(prettyPrint()),
            responseFields(
                fieldWithPath("id").description("UUID for the request."),
                fieldWithPath("email").description("User email who requested the password reset")
            )
        ));

        ResetPasswordForm resetForm = new ResetPasswordForm("newpassword");

        mockMvc
        .perform(
            put(requestLocation)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(resetForm))
        )
        .andExpect(status().isNoContent())
        .andDo(document("reset-password-update-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("newPassword").description("User new password")
            )
        ));

        userResetPasswordRequestRepository.deleteAll();
        deleteTestUser();
    }

}
