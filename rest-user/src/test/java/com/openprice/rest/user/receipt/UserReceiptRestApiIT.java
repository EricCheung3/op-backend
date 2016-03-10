package com.openprice.rest.user.receipt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/test-data.xml")
public class UserReceiptRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private ReceiptImageRepository receiptImageRepository;

    @Test
    public void getCurrentUserReceipts_ShouldReturnFirstPageOfUserReceipts() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userReceiptsUrl(sessionFilter))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(14))
            .body("page.totalPages", equalTo(2))
            .body("page.number", equalTo(0))
            .body("_embedded.receipts[0].id", equalTo("receipt014"))
            .body("_embedded.receipts[1].id", equalTo("receipt013"))
            .body("_embedded.receipts[2].id", equalTo("receipt012"))
        ;

        final String nextUrl = response.then().extract().path("_links.next.href");
        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(nextUrl)
            ;
        //response.prettyPrint();
    }

    @Test
    public void getUserReceiptById_ShouldReturnUserReceipt_WithResult_WhenHasResult() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userReceiptUrl(sessionFilter, "receipt001"))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("receipt001"))
            .body("receiptDate[0]", equalTo(2009))
            .body("receiptDate[1]", equalTo(1))
            .body("receiptDate[2]", equalTo(1))
            .body("status", equalTo("HAS_RESULT"))
            .body("needFeedback", equalTo(true))
            .body("chainCode", equalTo("rcss"))
            .body("icon", equalTo("rcss"))
            .body("storeName", equalTo("Superstore"))
            .body("total", equalTo("10.45"))
            .body("_links.images.href", endsWith("/images" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.image.href", endsWith("/images/{imageId}"))
            .body("_links.items.href", endsWith("/items" + UtilConstants.PAGINATION_TEMPLATES))
        ;
    }

    @Test
    public void getUserReceiptById_ShouldReturnUserReceipt_WhenWaitForResult() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userReceiptUrl(sessionFilter, "receipt002"))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("receipt002"))
            .body("_links.images.href", endsWith("/images" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.image.href", endsWith("/images/{imageId}"))
            .body("_links.items.href", endsWith("/items" + UtilConstants.PAGINATION_TEMPLATES))
        ;
    }

    @Test
    public void deleteUserReceiptById_ShouldDeleteReceiptAndImages() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String receiptUrl = userReceiptUrl(sessionFilter, "receipt001");

        given()
            .filter(sessionFilter)
        .when()
            .delete(receiptUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        // check receipt and image record
        Receipt receipt = receiptRepository.findOne("receipt001");
        assertNull(receipt);
        ReceiptImage image = receiptImageRepository.findOne("image001");
        assertNull(image);
    }
}
