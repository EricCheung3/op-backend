package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.account.user.UserAccountRepository;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.admin.user.AdminUserProfileForm;

@DatabaseSetup("classpath:/data/test-admin.xml")
public class AdminUserRestApiIT extends AbstractAdminRestApiIntegrationTest {

    @Inject
    UserAccountRepository userAccountRepository;

    @Test
    public void getUserAccounts_ShouldReturnAllUserAccountsAndOrderByCreatedTime() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        given()
            .filter(sessionFilter)
        .when()
            .get(usersUrl(sessionFilter))
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
    public void getUserAccountByUserId_ShouldReturnSpecificUserAccount() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);
        final String userUrl =  userUrl(sessionFilter, TEST_USERID_JANE_DOE);

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
            .body("_links.lockState.href", endsWith("/admin/users/user002/lockState"))
            .body("_links.profile.href", endsWith("/admin/users/user002/profile"))
            .body("_links.receipts.href", endsWith("/admin/users/user002/receipts" + UtilConstants.PAGINATION_TEMPLATES))
        ;
    }

    @Test
    public void changeUserLockState_ShouldLockUserAccount() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);
        final String userUrl =  userUrl(sessionFilter, TEST_USERID_JANE_DOE);
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
    public void updateUserProfile_ShouldChangeProfileAddress() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);
        final String userUrl =  userUrl(sessionFilter, TEST_USERID_JANE_DOE);
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
            .body("address.address1", equalTo("101 123 street"))
            .body("address.city", equalTo("Edmonton"))
        ;

        AdminUserProfileForm form =
            AdminUserProfileForm.builder()
                                .address1("888 Broadway Ave")
                                .city("Calgary")
                                .build();

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
            .body("address.address1", equalTo("888 Broadway Ave"))
            .body("address.city", equalTo("Calgary"))
        ;
    }

    @Test
    public void deleteUserProfile_ShouldDeleteUserAccount() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);
        final String userUrl =  userUrl(sessionFilter, "user003");

        given()
            .filter(sessionFilter)
        .when()
            .delete(userUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(userUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        assertNull(userAccountRepository.findOne("user003"));
    }

}
