package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;

public class AdminUserReceiptRestApiIT extends AbstractAdminRestApiIntegrationTest {

    @Value("classpath:/data/sample1.txt")
    private Resource sampleReceipt1;

    @Value("classpath:/data/sample2.txt")
    private Resource sampleReceipt2;

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getUserReceipts_ShouldReturnAllUserReceipts() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        final String userUrl =  getUserLinkUrl(sessionFilter, TEST_USERID_JOHN_DOE); // John Doe

        // get receipts link
        String receiptsUrl = given().filter(sessionFilter)
                                     .when().get(userUrl)
                                     .then().extract().path("_links.receipts.href");

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptsUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(2))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receipts[0].id", equalTo("receipt002"))
            .body("_embedded.receipts[1].id", equalTo("receipt001"))
        ;
    }
}
