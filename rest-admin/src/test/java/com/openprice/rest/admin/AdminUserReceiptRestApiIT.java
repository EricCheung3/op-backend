package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.damnhandy.uri.template.UriTemplate;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.rest.UtilConstants;

@DatabaseSetup("classpath:/data/testAdmin.xml")
public class AdminUserReceiptRestApiIT extends AbstractAdminRestApiIntegrationTest {

    @Value("classpath:/data/sample1.txt")
    private Resource sampleReceipt1;

    @Value("classpath:/data/sample2.txt")
    private Resource sampleReceipt2;

    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private ReceiptImageRepository receiptImageRepository;

    @Test
    public void getUserReceipts_ShouldReturnAllUserReceipts() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        given()
            .filter(sessionFilter)
        .when()
            .get(userReceiptsUrl(sessionFilter, TEST_USERID_JOHN_DOE))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(10))
            .body("page.totalElements", equalTo(2))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receipts[0].id", equalTo("receipt002"))
            .body("_embedded.receipts[1].id", equalTo("receipt001"))
        ;
    }

    @Test
    public void getUserReceiptById_ShouldReturnCorrectReceipt() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userReceiptUrl(sessionFilter, TEST_USERID_JOHN_DOE, "receipt001"))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("receipt001"))
            .body("_links.self.href", endsWith("/admin/users/user001/receipts/receipt001"))
            .body("_links.images.href", endsWith("/admin/users/user001/receipts/receipt001/images" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.image.href", endsWith("/admin/users/user001/receipts/receipt001/images/{imageId}"))
        ;
        //TODO test images, feedback
    }

    @Test
    public void deleteUserReceiptById_ShouldDeleteReceiptAndImages() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);
        final String receiptUrl = userReceiptUrl(sessionFilter, TEST_USERID_JOHN_DOE, "receipt001");

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
    public void getUserReceiptImages_ShouldReturnAllImagesOfReceipt() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        given()
            .filter(sessionFilter)
        .when()
            .get(userReceiptImagesUrl(sessionFilter, TEST_USERID_JOHN_DOE, "receipt001"))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(100))
            .body("page.totalElements", equalTo(1))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receiptImages[0].id", equalTo("image001"))
            .body("_embedded.receiptImages[0].status", equalTo("UPLOADED"))
            .body("_links.self.href", endsWith("/admin/users/user001/receipts/receipt001/images"))
        ;
    }

    @Test
    public void getUserReceiptImageById_ShouldReturnCorrectReceiptImage() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);

        given()
            .filter(sessionFilter)
        .when()
            .get(userReceiptImageUrl(sessionFilter, TEST_USERID_JOHN_DOE, "receipt001", "image001"))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("image001"))
            .body("status", equalTo("UPLOADED"))
            .body("_links.self.href", endsWith("/admin/users/user001/receipts/receipt001/images/image001"))
            .body("_links.receipt.href", endsWith("/admin/users/user001/receipts/receipt001"))
        ;
    }

    @Test
    public void deleteUserReceiptImageById_ShouldDeleteImage() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_NEWTON);
        final String imageUrl = userReceiptImageUrl(sessionFilter, TEST_USERID_JOHN_DOE, "receipt001", "image001");

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

    private String userReceiptsUrl(final SessionFilter sessionFilter, final String userId) {
        final String branchesLink =
            given().filter(sessionFilter)
                   .when().get(userUrl(sessionFilter, userId))
                   .then().extract().path("_links.receipts.href");
        return UriTemplate.fromTemplate(branchesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String userReceiptUrl(final SessionFilter sessionFilter, final String userId, final String receiptId) {
        final String receiptLink =
            given().filter(sessionFilter)
                   .when().get(userUrl(sessionFilter, userId))
                   .then().extract().path("_links.receipt.href");
        return UriTemplate.fromTemplate(receiptLink).set("receiptId", receiptId).expand();
    }

    private String userReceiptImagesUrl(final SessionFilter sessionFilter, final String userId, final String receiptId) {
        final String imagesLink =
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, userId, receiptId))
                   .then().extract().path("_links.images.href");
        return UriTemplate.fromTemplate(imagesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String userReceiptImageUrl(final SessionFilter sessionFilter, final String userId,
                                          final String receiptId, final String imageId) {
        final String imageLink =
            given().filter(sessionFilter)
                   .when().get(userReceiptUrl(sessionFilter, userId, receiptId))
                   .then().extract().path("_links.image.href");
        return UriTemplate.fromTemplate(imageLink).set("imageId", imageId).expand();
    }
}
