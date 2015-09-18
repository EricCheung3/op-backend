package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.damnhandy.uri.template.UriTemplate;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.admin.user.AdminUserAccountResource;
import com.openprice.rest.admin.user.AdminUserProfileForm;

public class AdminUserRestApiIT extends AbstractAdminRestApiIntegrationTest {

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getUserAccounts_ShouldReturnAllUserAccounts() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        String usersLink = getAdminAccountUsersLink(sessionFilter);
        String usersUrl =  UriTemplate.fromTemplate(usersLink).set("page", 0).set("size", 10).set("sort", null).expand();

        given()
            .filter(sessionFilter)
        .when()
            .get(usersUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(3))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.userAccounts[0].id", equalTo("user003"))
            .body("_embedded.userAccounts[1].id", equalTo("user002"))
            .body("_embedded.userAccounts[2].id", equalTo("user001"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getUserAccount_ShouldReturnSpecificUserAccount() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        final String userUrl =  getUserLinkUrl(sessionFilter, TEST_USERID_JANE_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(userUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo(TEST_USERID_JANE_DOE))
            .body("email", equalTo(TEST_USERNAME_JANE_DOE))
            .body("accountLocked", equalTo(false))
            .body("profile.firstName", equalTo("Jane"))
            .body("profile.lastName", equalTo("Doe"))
            .body("profile.address.address1", equalTo("101 123 street"))
            .body("_links.self.href", equalTo(userUrl))
            .body("_links.lockState.href", endsWith(AdminUserAccountResource.LINK_NAME_LOCK_STATE))
            .body("_links.profile.href", endsWith(AdminUserAccountResource.LINK_NAME_PROFILE))
            .body("_links.receipts.href", endsWith(AdminUserAccountResource.LINK_NAME_RECEIPTS))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void changeUserLockState_ShouldLockUserAccount() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        final String userUrl =  getUserLinkUrl(sessionFilter, TEST_USERID_JANE_DOE);

        // get lockState link
        final String lockStateLink = given().filter(sessionFilter).when().get(userUrl).then().extract().path("_links.lockState.href");

        // lock user
        final Map<String, Boolean> lockState = new HashMap<>();
        lockState.put("locked", true);

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(lockState)
        .when()
            .put(lockStateLink)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        // verify user lock state
        given()
            .filter(sessionFilter)
        .when()
            .get(userUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("accountLocked", equalTo(true))
        ;

    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void updateUserProfile_ShouldChangeProfileAddress() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        final String userUrl =  getUserLinkUrl(sessionFilter, TEST_USERID_JANE_DOE);
        final String profileLink = given().filter(sessionFilter).when().get(userUrl).then().extract().path("_links.profile.href");

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(profileLink)
            ;

        //response.prettyPrint();

        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("id", equalTo("profile002"))
                .body("address.address1", equalTo("101 123 street"))
                .body("address.city", equalTo("Edmonton"))
            ;

        AdminUserProfileForm form = constructUserProfileFormByProfileResource(response);
        form.setAddress1("888 Broadway Ave");
        form.setCity("Calgary");

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .put(profileLink)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(profileLink)
            ;
        //response.prettyPrint();

        response
            .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("id", equalTo("profile002"))
                .body("address.address1", equalTo("888 Broadway Ave"))
                .body("address.city", equalTo("Calgary"))
            ;
    }
}
