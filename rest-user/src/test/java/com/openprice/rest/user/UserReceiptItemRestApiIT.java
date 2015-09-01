package com.openprice.rest.user;

import static com.jayway.restassured.RestAssured.given;

import java.util.Base64;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.damnhandy.uri.template.UriTemplate;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.AbstractRestApiIntegrationTest;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.common.ImageDataForm;

public class UserReceiptItemRestApiIT extends AbstractRestApiIntegrationTest {
    @Test
    @DatabaseSetup("classpath:/data/testReceipt.xml")
    public void getUserReceiptItems_ShouldReturn() throws Exception {
        final SessionFilter sessionFilter = login(USERNAME_JOHN_DOE);
        
        String receiptsLink = 
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.receipts.href");
        String receiptsUrl =  UriTemplate.fromTemplate(receiptsLink)
                                         .set("page", null)
                                         .set("size", null)
                                         .set("sort", null)
                                         .expand();
        
        // add new image as base64 encoded string
        final String base64String = Base64.getEncoder().encodeToString("test".getBytes());
        ImageDataForm form = new ImageDataForm();
        form.setBase64String(base64String);
        Response response = 
            given()
                .filter(sessionFilter)
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(receiptsUrl)
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

        response.prettyPrint();
    }

}
