package com.openprice.rest.admin.receipt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

@DatabaseSetup("classpath:/data/testAdmin.xml")
public class AdminReceiptFeedbackRestApiIT extends AbstractAdminReceiptRestApiIntegrationTest {
    
    @Test
    public void getReceiptFeedbacks_ShouldReturnFeedbacksFromDatabase() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);
        
        Response response = 
                given()
                    .filter(sessionFilter)
                .when()
                    .get(receiptFeedbacksUrl(sessionFilter, "receipt001"));
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("_embedded.receiptFeedbacks[0].id", equalTo("receipt001feedback001"))
            .body("_embedded.receiptFeedbacks[0].rating", equalTo(5))
            .body("_embedded.receiptFeedbacks[0].comment", equalTo("Excellent!"))
        ;
    }
    
    @Test
    public void getReceiptFeedbacks_ShouldReturnFeedbackFromDatabase() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);
        
        Response response = 
                given()
                    .filter(sessionFilter)
                .when()
                    .get(receiptFeedbackUrl(sessionFilter, "receipt001", "receipt001feedback001"));

        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("receipt001feedback001"))
            .body("rating", equalTo(5))
            .body("comment", equalTo("Excellent!"))
        ;
    }
}
