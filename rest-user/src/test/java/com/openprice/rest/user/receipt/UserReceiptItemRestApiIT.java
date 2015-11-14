package com.openprice.rest.user.receipt;

import static com.jayway.restassured.RestAssured.given;

import java.util.Base64;

import org.apache.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;
import com.openprice.rest.user.receipt.ImageDataForm;

@DatabaseSetup("classpath:/data/testData.xml")
@Ignore
public class UserReceiptItemRestApiIT extends AbstractUserRestApiIntegrationTest {
    @Test
    public void getUserReceiptItems_ShouldReturnCorrectReceiptItems() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        // add new image as base64 encoded string
        final String base64String = Base64.getEncoder().encodeToString("test".getBytes());
        final ImageDataForm form =
            ImageDataForm.builder()
                         .base64String(base64String)
                         .build();

        Response response =
            given()
                .filter(sessionFilter)
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(userReceiptsUrl(sessionFilter))
            ;

        response
        .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        // verify new receipt
        String receiptUrl = response.getHeader("Location");

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(receiptUrl)
            ;
        final String receiptItemsUrl = response.then().extract().path("_links.items.href");

        //load items
        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(receiptItemsUrl)
            ;

        //response.prettyPrint();
        // TODO verify returned items after parser is stable
    }

}
