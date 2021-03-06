package com.openprice.rest.user.receipt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptItemRepository;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/test-data.xml")
public class UserReceiptResultRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Inject
    private ReceiptItemRepository receiptItemRepository;

    @Test
    public void getUserReceiptResult_ShouldReturnRecentParsedReceiptResultFromDatabase() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        Response response =
                given()
                    .filter(sessionFilter)
                .when()
                    .get(userReceiptResultUrl(sessionFilter, "receipt001"))
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
            .body("storeName", equalTo("Superstore"))
            .body("_embedded.receiptItems[0].id", equalTo("recItem001"))
            .body("_embedded.receiptItems[0].catalogCode", equalTo("EGG_1235"))
            .body("_embedded.receiptItems[0].parsedName", equalTo("eggs"))
            .body("_embedded.receiptItems[0].parsedPrice", equalTo("1.99"))
            .body("_embedded.receiptItems[0].catalog", nullValue())
            .body("_embedded.receiptItems[1].id", equalTo("recItem003"))
            .body("_embedded.receiptItems[1].catalogCode", equalTo("PORK"))
            .body("_embedded.receiptItems[1].parsedName", equalTo("pork"))
            .body("_embedded.receiptItems[1].parsedPrice", equalTo("5.99"))
            .body("_embedded.receiptItems[1].catalog", nullValue())
        ;
    }

    @Test
    public void getUserReceiptResult_ShouldReturn404NotFound_IfNotExist() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        given()
            .filter(sessionFilter)
        .when()
            .get(userReceiptResultUrl(sessionFilter, "receipt002"))
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    public void getUserReceiptItems_ShouldReturnReceiptItems() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        Response response =
                given()
                    .filter(sessionFilter)
                .when()
                    .get(userReceiptItemsUrl(sessionFilter, "receipt001"))
                ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(100))
            .body("page.totalElements", equalTo(2))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receiptItems[0].id", equalTo("recItem001"))
            .body("_embedded.receiptItems[0].parsedName", equalTo("eggs"))
            .body("_embedded.receiptItems[0].parsedPrice", equalTo("1.99"))
            .body("_embedded.receiptItems[0].catalog", nullValue())
            .body("_embedded.receiptItems[1].id", equalTo("recItem003"))
            .body("_embedded.receiptItems[1].parsedName", equalTo("pork"))
            .body("_embedded.receiptItems[1].parsedPrice", equalTo("5.99"))
            .body("_embedded.receiptItems[1].catalog", nullValue())
        ;
    }

    @Test
    public void getUserReceiptItemById_ShouldReturnItem() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        Response response =
                given()
                    .filter(sessionFilter)
                .when()
                    .get(userReceiptItemUrl(sessionFilter, "receipt001", "recItem001"))
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
    public void getUserReceiptItemById_ShouldReturn404_IfItemWasIgnored() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        given()
            .filter(sessionFilter)
        .when()
            .get(userReceiptItemUrl(sessionFilter, "receipt001", "recItem002"))
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    public void updateUserReceiptItem_ShouldUpdateNameAndPrice() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String itemUrl = userReceiptItemUrl(sessionFilter, "receipt001", "recItem001");
        final UserReceiptItemForm form = new UserReceiptItemForm("organic eggs", "4.49");

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .put(itemUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(itemUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("recItem001"))
            .body("parsedName", equalTo("eggs"))
            .body("displayName", equalTo("organic eggs"))
            .body("parsedPrice", equalTo("1.99"))
            .body("displayPrice", equalTo("4.49"))
            .body("_links.self.href", endsWith(itemUrl))
        ;
    }


    @Test
    public void updateUserReceiptResultOfToatlAndDate_ShouldUpdateDateAndTotal() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String resultUrl = userReceiptResultUrl(sessionFilter, "receipt001");
        final ReceiptResultForm form = new ReceiptResultForm("2016/11/11", "100.5");

        given()
            .filter(sessionFilter)
            .contentType(ContentType.JSON)
            .body(form)
        .when()
            .put(resultUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        Response res = given()
            .filter(sessionFilter)
        .when()
            .get(resultUrl);
        res.prettyPrint();
        res.then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("recData001"))
            .body("chainCode", equalTo("rcss"))
            .body("branchName", equalTo("Calgary Trail"))
            .body("storeName", equalTo("Superstore"))
            .body("parsedDate", equalTo("2016/11/11"))
            .body("parsedTotal", equalTo("100.5"))

            .body("_links.self.href", endsWith(resultUrl))
        ;
    }

    @Test
    public void deleteUserReceiptItemById_ShouldDeleteReceiptItem() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String itemUrl = userReceiptItemUrl(sessionFilter, "receipt001", "recItem001");

        given()
            .filter(sessionFilter)
        .when()
            .delete(itemUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(itemUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        // check image record
        ReceiptItem item = receiptItemRepository.findOne("recItem001");
        assertTrue(item.getIgnored());
    }
}
