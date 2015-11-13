package com.openprice.rest.user;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

@DatabaseSetup("classpath:/data/testData.xml")
public class UserReceiptParserResultRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Test
    public void getUserReceiptParserResult_ShouldReturnParserResultFromDatabase() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        Response response =
                given()
                    .filter(sessionFilter)
                .when()
                    .get(userReceiptParserResultUrl(sessionFilter, "receipt001"))
                ;
        response.prettyPrint();

        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("chainCode", equalTo("rcss"))
            .body("branchName", equalTo("Calgary Trail"))
            .body("parsedTotal", equalTo("10.45"))
            .body("items[0].id", equalTo("recItem001"))
            .body("items[0].parsedName", equalTo("eggs"))
            .body("items[0].parsedPrice", equalTo("1.99"))
            .body("items[1].id", equalTo("recItem002"))
            .body("items[1].parsedName", equalTo("milk"))
            .body("items[1].parsedPrice", equalTo("4.99"))
        ;

    }
}
