package com.openprice.rest.site;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.common.ApiConstants;
import com.openprice.domain.account.user.UserResetPasswordRequest;
import com.openprice.domain.account.user.UserResetPasswordRequestRepository;

@DatabaseSetup("classpath:/data/test-data.xml")
public class ResetPasswordRestApiIT extends AbstractSiteRestApiIntegrationTest {

    @Test
    public void forgetPassword_ShouldReturn404_WithInvalidEmail() {
        ForgetPasswordForm form = new ForgetPasswordForm("non@email.com");

        Response response =
            given()
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(forgetPasswordUrl())
            ;

        response
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            ;
    }

    @Test
    public void forgetPassword_ShouldAddForgetPasswordRequest() {
        ForgetPasswordForm form = new ForgetPasswordForm(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(forgetPasswordUrl())
            ;

        response
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            ;

        // verify request in database
        List<UserResetPasswordRequest> requests = userResetPasswordRequestRepository.findByEmail(TEST_USERNAME_JOHN_DOE);
        assertEquals(1, requests.size());
        assertEquals(TEST_USERNAME_JOHN_DOE, requests.get(0).getEmail());
    }

    @Test
    public void getResetPasswordRequest_ShouldReturnPasswordRequest() {
        ForgetPasswordForm form = new ForgetPasswordForm(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(forgetPasswordUrl())
            ;

        response
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            ;

        List<UserResetPasswordRequest> requests = userResetPasswordRequestRepository.findByEmail(TEST_USERNAME_JOHN_DOE);
        String requestId = requests.get(0).getId();
        String resetPasswordRequestUrl = when().get(ApiConstants.EXTERNAL_API_ROOT).then().extract().path("_links.resetPassword.href");
        response =
            given()
            .when()
                .get(resetPasswordRequestUrl, requestId)
            ;
        //response.prettyPrint();
        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("id", equalTo(requestId))
                .body("email", equalTo(TEST_USERNAME_JOHN_DOE))
            ;

    }

    @Test
    public void getResetPasswordRequest_ShouldReturn404_WhenRequestExpired() {
        ForgetPasswordForm form = new ForgetPasswordForm(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(forgetPasswordUrl())
            ;

        response
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            ;

        List<UserResetPasswordRequest> requests = userResetPasswordRequestRepository.findByEmail(TEST_USERNAME_JOHN_DOE);
        UserResetPasswordRequest request = requests.get(0);
        String requestId = request.getId();
        request.setRequestTime(request.getRequestTime().minusHours(2));
        userResetPasswordRequestRepository.save(request);

        given()
            .when()
                .get(resetPasswordRequestUrl(), requestId)
            .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
            ;

    }

    @Test
    public void resetPassword_ShouldResetPassword() {

        ForgetPasswordForm form = new ForgetPasswordForm(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(forgetPasswordUrl())
            ;

        response
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            ;

        List<UserResetPasswordRequest> requests = userResetPasswordRequestRepository.findByEmail(TEST_USERNAME_JOHN_DOE);
        String requestId = requests.get(0).getId();
        ResetPasswordForm resetForm = new ResetPasswordForm("newpassword");

        given()
            .contentType(ContentType.JSON)
            .body(resetForm)
        .when()
            .put(resetPasswordRequestUrl(), requestId)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

    }

    @Test
    public void resetPassword_ShouldReturn404_WhenRequestExpired() {
        ForgetPasswordForm form = new ForgetPasswordForm(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(forgetPasswordUrl())
            ;

        response
            .then()
            .statusCode(HttpStatus.SC_ACCEPTED)
            ;

        List<UserResetPasswordRequest> requests = userResetPasswordRequestRepository.findByEmail(TEST_USERNAME_JOHN_DOE);
        UserResetPasswordRequest request = requests.get(0);
        String requestId = request.getId();
        request.setRequestTime(request.getRequestTime().minusHours(2));
        userResetPasswordRequestRepository.save(request);

        ResetPasswordForm resetForm = new ResetPasswordForm("newpassword");

        given()
            .contentType(ContentType.JSON)
            .body(resetForm)
        .when()
            .put(resetPasswordRequestUrl(), requestId)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

    }

    @Inject
    private UserResetPasswordRequestRepository userResetPasswordRequestRepository;
}
