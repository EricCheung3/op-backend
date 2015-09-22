package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.openprice.rest.UtilConstants;

public class AdminAccountRestApiIT extends AbstractAdminRestApiIntegrationTest {

    @Test
    public void getCurrentAdminAccount_ShouldReturn403_IfNotSignin() {
        when()
            .get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN)
        .then()
            .statusCode(HttpStatus.SC_FORBIDDEN)
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getCurrentAdminAccount_ShouldReturnLoggedInAdmiAccount() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("admin001"))
            .body("email", equalTo("john.doe@openprice.com"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN))
            .body("_links.users.href", endsWith(AdminApiUrls.URL_ADMIN_USERS + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.receipts.href", endsWith(AdminApiUrls.URL_ADMIN_RECEIPTS + UtilConstants.PAGINATION_TEMPLATES))
        ;
    }
}
