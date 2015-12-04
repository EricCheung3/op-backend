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
public class AdminReceiptResultRestApiIT extends AbstractAdminReceiptRestApiIntegrationTest {

    @Test
    public void getReceiptResults_ShouldReturntReceiptResultsFromDatabase() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        Response response =
                given()
                    .filter(sessionFilter)
                .when()
                    .get(receiptResultsUrl(sessionFilter, "receipt001"))
                ;
        response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("_embedded.receiptResults[0].id", equalTo("recData001"))
            .body("_embedded.receiptResults[0].chainCode", equalTo("rcss"))
            .body("_embedded.receiptResults[0].branchName", equalTo("Calgary Trail"))
            .body("_embedded.receiptResults[0].parsedTotal", equalTo("10.45"))
            .body("_embedded.receiptResults[0].parsedDate", equalTo("2015/11/11"))
            .body("_embedded.receiptResults[0].items[0].id", equalTo("recItem003"))
            .body("_embedded.receiptResults[0].items[0].lineNumber", equalTo(10))
            .body("_embedded.receiptResults[0].items[0].catalogCode", equalTo("PORK"))
            .body("_embedded.receiptResults[0].items[0].userDeleted", equalTo(false))
            .body("_embedded.receiptResults[0].items[1].id", equalTo("recItem001"))
            .body("_embedded.receiptResults[0].items[1].lineNumber", equalTo(15))
            .body("_embedded.receiptResults[0].items[1].catalogCode", equalTo("EGG_1235"))
            .body("_embedded.receiptResults[0].items[1].userDeleted", equalTo(false))
            .body("_embedded.receiptResults[0].items[2].id", equalTo("recItem002"))
            .body("_embedded.receiptResults[0].items[2].lineNumber", equalTo(18))
            .body("_embedded.receiptResults[0].items[2].catalogCode", equalTo("MILK_1234"))
            .body("_embedded.receiptResults[0].items[2].userDeleted", equalTo(true))
        ;
    }

    @Test
    public void getUserReceiptResult_ShouldReturnParserResult() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        Response response =
                given()
                    .filter(sessionFilter)
                .when()
                    .get(receiptResultUrl(sessionFilter, "receipt001", "recData001"))
                ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("recData001"))
            .body("chainCode", equalTo("rcss"))
            .body("branchName", equalTo("Calgary Trail"))
            .body("parsedTotal", equalTo("10.45"))
            .body("parsedDate", equalTo("2015/11/11"))
            .body("items[0].id", equalTo("recItem003"))
            .body("items[1].id", equalTo("recItem001"))
            .body("items[2].id", equalTo("recItem002"))
        ;
    }

    @Test
    public void getReceiptResultItems_ShouldReturnReceiptItems() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(receiptItemsUrl(sessionFilter, "receipt001", "recData001"))
            ;
        response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(100))
            .body("page.totalElements", equalTo(3))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receiptItems[0].id", equalTo("recItem001"))
            .body("_embedded.receiptItems[0].parsedName", equalTo("eggs"))
            .body("_embedded.receiptItems[0].parsedPrice", equalTo("1.99"))
            .body("_embedded.receiptItems[0].catalog.id", equalTo("chain001cat002"))
            .body("_embedded.receiptItems[1].id", equalTo("recItem002"))
            .body("_embedded.receiptItems[1].parsedName", equalTo("milk"))
            .body("_embedded.receiptItems[1].parsedPrice", equalTo("4.99"))
            .body("_embedded.receiptItems[1].catalog.id", equalTo("chain001cat001"))
            .body("_embedded.receiptItems[2].id", equalTo("recItem003"))
            .body("_embedded.receiptItems[2].parsedName", equalTo("pork"))
            .body("_embedded.receiptItems[2].parsedPrice", equalTo("5.99"))
            .body("_embedded.receiptItems[2].catalog.id", equalTo("chain001cat003"))
        ;
    }

    @Test
    public void getUserReceiptItemById_ShouldReturnItem() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        Response response =
                given()
                    .filter(sessionFilter)
                .when()
                    .get(receiptItemUrl(sessionFilter, "receipt001", "recData001", "recItem001"))
                ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("parsedName", equalTo("eggs"))
            .body("displayName", equalTo("eggs"))
            .body("parsedPrice", equalTo("1.99"))
            .body("displayPrice", equalTo("1.99"))
            .body("catalog.id", equalTo("chain001cat002"))
        ;
    }

}
