package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.damnhandy.uri.template.UriTemplate;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.common.ApiConstants;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.rest.UtilConstants;

public class AdminReceiptRestApiIT extends AbstractAdminRestApiIntegrationTest {

    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private ReceiptImageRepository receiptImageRepository;

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getReceipts_ShouldReturnAllReceipts() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptsUrl(sessionFilter))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(4))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receipts[0].id", equalTo("receipt102"))
            .body("_embedded.receipts[1].id", equalTo("receipt101"))
            .body("_embedded.receipts[2].id", equalTo("receipt002"))
            .body("_embedded.receipts[3].id", equalTo("receipt001"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getReceiptById_ShouldReturnCorrectReceipt() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(receiptUrl(sessionFilter, "receipt001"))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("receipt001"))
            .body("user", equalTo("John Doe"))
            .body("_links.self.href", endsWith(AdminApiUrls.URL_ADMIN_RECEIPTS+"/receipt001"))
            .body("_links.images.href", endsWith(AdminApiUrls.URL_ADMIN_RECEIPTS + "/receipt001/images" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.image.href", endsWith(AdminApiUrls.URL_ADMIN_RECEIPTS + "/receipt001/images/{imageId}"))
        ;
        //TODO test images, feedback
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getReceiptById_ShouldReturn404_WithInvalidId() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptUrl(sessionFilter, "invalid"))
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void deleteReceiptById_ShouldDeleteReceiptAndImages() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String receiptUrl = receiptUrl(sessionFilter, "receipt001");

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
        assertNull(receiptRepository.findOne("receipt001"));
        assertNull(receiptImageRepository.findOne("image001"));
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getReceiptImages_ShouldReturnAllImagesOfReceipt() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptImagesUrl(sessionFilter, "receipt001"))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(100))
            .body("page.totalElements", equalTo(1))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receiptImages[0].id", equalTo("image001"))
            .body("_embedded.receiptImages[0].status", equalTo("UPLOADED"))
            .body("_links.self.href", endsWith("/admin/receipts/receipt001/images"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getReceiptImageById_ShouldReturnCorrectReceiptImage() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptImageUrl(sessionFilter, "receipt001", "image001"))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("image001"))
            .body("status", equalTo("UPLOADED"))
            .body("_links.self.href", endsWith("/admin/receipts/receipt001/images/image001"))
            .body("_links.receipt.href", endsWith("/admin/receipts/receipt001"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void deleteReceiptImageById_ShouldDeleteImage() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);
        final String imageUrl = receiptImageUrl(sessionFilter, "receipt001", "image001");

        given()
            .filter(sessionFilter)
        .when()
            .delete(imageUrl)
        .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
        ;

        given()
            .filter(sessionFilter)
        .when()
            .get(imageUrl)
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
        ;

        // check branch record
        assertNull(receiptImageRepository.findOne("image001"));
        final Receipt receipt = receiptRepository.findOne("receipt001");
        assertEquals(0, receiptImageRepository.findByReceiptOrderByCreatedTime(receipt).size());
    }

    private String receiptsUrl(final SessionFilter sessionFilter) {
        final String receiptsLink =
            given().filter(sessionFilter)
                   .when().get(ApiConstants.EXTERNAL_API_ROOT + AdminApiUrls.URL_ADMIN)
                   .then().extract().path("_links.receipts.href");
        return UriTemplate.fromTemplate(receiptsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String receiptUrl(final SessionFilter sessionFilter, final String receiptId) {
        final String receiptLink =
            given().filter(sessionFilter)
                   .when().get(ApiConstants.EXTERNAL_API_ROOT + AdminApiUrls.URL_ADMIN)
                   .then().extract().path("_links.receipt.href");
        return UriTemplate.fromTemplate(receiptLink).set("receiptId", receiptId).expand();
    }

    private String receiptImagesUrl(final SessionFilter sessionFilter, final String receiptId) {
        final String imagesLink =
            given().filter(sessionFilter)
                   .when().get(receiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.images.href");
        return UriTemplate.fromTemplate(imagesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String receiptImageUrl(final SessionFilter sessionFilter, final String receiptId, final String imageId) {
        final String imageLink =
            given().filter(sessionFilter)
                   .when().get(receiptUrl(sessionFilter, receiptId))
                   .then().extract().path("_links.image.href");
        return UriTemplate.fromTemplate(imageLink).set("imageId", imageId).expand();
    }
}
