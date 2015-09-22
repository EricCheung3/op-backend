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
import com.openprice.rest.UtilConstants;

public class AdminReceiptRestApiIT extends AbstractAdminRestApiIntegrationTest {
    @Value("classpath:/data/sample1.txt")
    private Resource sampleReceipt1;

    @Value("classpath:/data/sample2.txt")
    private Resource sampleReceipt2;

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getReceipts_ShouldReturnAllReceipts() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(UtilConstants.API_ROOT + AdminApiUrls.URL_ADMIN_RECEIPTS)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(4))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receipts[0].id", equalTo("receipt001"))
            .body("_embedded.receipts[1].id", equalTo("receipt002"))
            .body("_embedded.receipts[2].id", equalTo("receipt101"))
            .body("_embedded.receipts[3].id", equalTo("receipt102"))
        ;
    }

}
