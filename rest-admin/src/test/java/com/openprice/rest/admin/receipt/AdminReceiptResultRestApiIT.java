package com.openprice.rest.admin.receipt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

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
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("_embedded.receiptResults[0].id", equalTo("recData001"))
            .body("_embedded.receiptResults[0].chainCode", equalTo("rcss"))
            .body("_embedded.receiptResults[0].branchName", equalTo("Calgary Trail"))
            .body("_embedded.receiptResults[0].parsedTotal", equalTo("10.45"))
            .body("_embedded.receiptResults[0].parsedDate", equalTo("2015/11/11"))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[0].id", equalTo("recItem003"))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[0].lineNumber", equalTo(10))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[0].catalogCode", equalTo("PORK"))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[0].userDeleted", equalTo(false))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[1].id", equalTo("recItem001"))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[1].lineNumber", equalTo(15))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[1].catalogCode", equalTo("EGG_1235"))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[1].userDeleted", equalTo(false))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[2].id", equalTo("recItem002"))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[2].lineNumber", equalTo(18))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[2].catalogCode", equalTo("MILK_1234"))
            .body("_embedded.receiptResults[0]._embedded.receiptItems[2].userDeleted", equalTo(true))
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
            .body("_embedded.receiptItems[0].id", equalTo("recItem003"))
            .body("_embedded.receiptItems[1].id", equalTo("recItem001"))
            .body("_embedded.receiptItems[2].id", equalTo("recItem002"))
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
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(100))
            .body("page.totalElements", equalTo(3))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receiptItems[0].id", equalTo("recItem003"))
            .body("_embedded.receiptItems[0].parsedName", equalTo("pork"))
            .body("_embedded.receiptItems[0].parsedPrice", equalTo("5.99"))
            .body("_embedded.receiptItems[0].catalog", nullValue())
            .body("_embedded.receiptItems[1].id", equalTo("recItem001"))
            .body("_embedded.receiptItems[1].parsedName", equalTo("eggs"))
            .body("_embedded.receiptItems[1].parsedPrice", equalTo("1.99"))
            .body("_embedded.receiptItems[1].catalog", nullValue())
            .body("_embedded.receiptItems[2].id", equalTo("recItem002"))
            .body("_embedded.receiptItems[2].parsedName", equalTo("milk"))
            .body("_embedded.receiptItems[2].parsedPrice", equalTo("4.99"))
            .body("_embedded.receiptItems[2].catalog", nullValue())
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
            .body("catalog", nullValue())
        ;
    }

    @Test
    public void getReceiptResultFields_ShouldReturnFromDatabase() throws Exception{
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        Response response =
                given()
                    .filter(sessionFilter)
                .when()
                    .get(receiptFieldsUrl(sessionFilter, "receipt001", "recData001"))
                ;
        response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(100))
            .body("page.totalElements", equalTo(2))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receiptFields[0].id", equalTo("recField001"))
            .body("_embedded.receiptFields[0].type", equalTo("AddressCity"))
            .body("_embedded.receiptFields[0].value", equalTo("Edmonton"))
            .body("_embedded.receiptFields[0].lineNumber", equalTo(3))
        ;
    }

    @Test
    public void getReceiptResultFieldById_ShouldReturnField() throws Exception{
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        Response response =
                given()
                    .filter(sessionFilter)
                .when()
                    .get(receiptFieldUrl(sessionFilter, "receipt001", "recData001", "recField001"))
                ;
        response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("recField001"))
            .body("type", equalTo("AddressCity"))
            .body("value", equalTo("Edmonton"))
            .body("lineNumber", equalTo(3))
        ;
    }

}
