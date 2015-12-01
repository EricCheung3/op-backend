package com.openprice.rest.site;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;

import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.common.ApiConstants;
import com.openprice.domain.account.user.UserRoleType;
import com.openprice.rest.AbstractRestApiIntegrationTest;

@SpringApplicationConfiguration(classes = {SiteApiTestApplication.class})
public class RegisterRestApiIT extends AbstractRestApiIntegrationTest {

    @Test
    public void registerNewUser_ShouldAddUserAccount() {
        String registrationUrl = when().get(ApiConstants.EXTERNAL_API_ROOT).then().extract().path("_links.registration.href");

        RegistrationForm registration = new RegistrationForm();
        registration.setFirstName("John");
        registration.setLastName("Doe");
        registration.setEmail("john.doe@openprice.com");
        registration.setPassword("abcd");

        Response response =
            given()
                .contentType(ContentType.JSON)
                .body(registration)
            .when()
                .post(registrationUrl)
            ;

        response
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            ;

        // verify user profile
        final SessionFilter sessionFilter = login("john.doe@openprice.com");

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(ApiConstants.EXTERNAL_API_ROOT + "/user")
            ;

        //response.prettyPrint();

        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("email", equalTo("john.doe@openprice.com"))
                .body("roles[0]", equalTo(UserRoleType.ROLE_USER.name()))
                .body("trustedAccount", equalTo(false))
                .body("activated", equalTo(true)) // FIXME
                .body("profile.firstName", equalTo("John"))
                .body("profile.lastName", equalTo("Doe"))
            ;

    }

}
