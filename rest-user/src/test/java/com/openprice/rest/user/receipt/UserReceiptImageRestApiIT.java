package com.openprice.rest.user.receipt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/test-data.xml")
public class UserReceiptImageRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Value("classpath:/data/BostonPizza.JPG")
    private Resource sampleImage;

    @Inject
    private ReceiptImageRepository receiptImageRepository;

    @Test
    public void getUserReceiptImages_ShouldReturnUserReceiptImages() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(userReceiptImagesUrl(sessionFilter, "receipt001"))
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(100))
            .body("page.totalElements", equalTo(4))
            .body("page.totalPages", equalTo(1))
            .body("page.number", equalTo(0))
            .body("_embedded.receiptImages[0].id", equalTo("rec001image001"))
            .body("_embedded.receiptImages[0].status", equalTo(ProcessStatusType.SCANNED.name()))
            .body("_embedded.receiptImages[1].id", equalTo("rec001image003"))
            .body("_embedded.receiptImages[1].status", equalTo(ProcessStatusType.SCANNED.name()))
            .body("_embedded.receiptImages[2].id", equalTo("rec001image002"))
            .body("_embedded.receiptImages[2].status", equalTo(ProcessStatusType.SCANNED.name()))
            .body("_embedded.receiptImages[3].id", equalTo("rec001image004"))
            .body("_embedded.receiptImages[3].status", equalTo(ProcessStatusType.SCANNED.name()))
        ;
    }

    @Test
    public void uploadReceiptImage_ShouldAddReceiptImage_AndSaveImageFile() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        String uploadUrl = userReceiptUploadUrl(sessionFilter);

        Response response =
            given()
                .filter(sessionFilter)
                .multiPart("file", sampleImage.getFile())
            .when()
                .post(uploadUrl)
            ;

        response
        .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        String receiptUrl = response.getHeader("Location");

        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(receiptUrl)
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
        ;

        // upload second image
        uploadUrl = response.then().extract().path("_links.upload.href");
        response = given()
            .filter(sessionFilter)
            .multiPart("file", sampleImage.getFile())
        .when()
            .post(uploadUrl)
        ;

        // verify image in FileSystem
        String imageUrl = response.getHeader("Location");
        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(imageUrl)
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("downloadUrl", endsWith("/download"))
            .body("base64Url", endsWith("/base64"))
        ;

        String downloadUrl = response.then().extract().path("_links.download.href");
        given()
            .filter(sessionFilter)
        .when()
            .get(downloadUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType("image/jpeg")
        ;
    }

    @Test
    public void deleteUserReceiptImageById_ShouldDeleteReceiptImage() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);
        final String imageUrl = userReceiptImageUrl(sessionFilter, "receipt001", "rec001image001");

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

        // check image record
        ReceiptImage image = receiptImageRepository.findOne("rec001image001");
        assertNull(image);
    }

}
