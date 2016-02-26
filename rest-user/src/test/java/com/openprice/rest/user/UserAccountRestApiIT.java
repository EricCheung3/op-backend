package com.openprice.rest.user;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.account.user.UserRoleType;
import com.openprice.rest.UtilConstants;

@DatabaseSetup("classpath:/data/test-data.xml")
public class UserAccountRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Test
    public void getCurrentUser_ShouldReturnLoggedInUserAccount() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userUrl())
            ;
        //response.prettyPrint();
        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("id", equalTo(TEST_USERID_JOHN_DOE))
                .body("email", equalTo(TEST_USERNAME_JOHN_DOE))
                .body("roles[0]", equalTo(UserRoleType.ROLE_STORE_MANAGER.name()))
                .body("roles[1]", equalTo(UserRoleType.ROLE_USER.name()))
                .body("trustedAccount", equalTo(true))
                .body("activated", equalTo(true))
                .body("profile.firstName", equalTo("John"))
                .body("profile.lastName", equalTo("Doe"))
                .body("uploadUrl", endsWith(URL_USER_RECEIPTS_UPLOAD))
                .body("_links.self.href", endsWith(URL_USER))
                .body("_links.profile.href", endsWith(URL_USER_PROFILE))
                .body("_links.receipts.href", endsWith(URL_USER_RECEIPTS + UtilConstants.PAGINATION_TEMPLATES))
                .body("_links.receipt.href", endsWith(URL_USER_RECEIPTS_RECEIPT))
                .body("_links.upload.href", endsWith(URL_USER_RECEIPTS_UPLOAD))
            ;
    }

    @Test
    public void getCurrentUserProfile_ShouldReturnLoggedInUserProfile() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JANE_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userProfileUrl())
            ;
        //response.prettyPrint();
        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("firstName", equalTo("Jane"))
                .body("lastName", equalTo("Doe"))
            ;

    }

    @Test
    public void updateProfile_ShouldChangeProfileAddress() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JANE_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userProfileUrl())
            ;
        //response.prettyPrint();
        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("address.address1", equalTo("101 123 street"))
                .body("address.city", equalTo("Edmonton"))
            ;

        UserProfileForm form =
            UserProfileForm.builder()
                           .address1("888 Broadway Ave")
                           .city("Calgary")
                           .build();

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .put(userProfileUrl())
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userProfileUrl())
            ;
        //response.prettyPrint();
        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("address.address1", equalTo("888 Broadway Ave"))
                .body("address.city", equalTo("Calgary"))
            ;
    }

    @Test
    public void getCategories_ShouldReturnAllProductCategories() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(categoriesUrl())
            ;
        //response.prettyPrint();
        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("[0].code", equalTo("apparel"))
                .body("[0].label", equalTo("Apparel"))
                .body("[35].code", equalTo("wine"))
                .body("[35].label", equalTo("Wine & Spirits"))
            ;
    }

}
