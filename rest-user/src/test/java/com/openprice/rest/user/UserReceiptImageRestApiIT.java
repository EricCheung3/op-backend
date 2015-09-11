package com.openprice.rest.user;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

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
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.rest.UtilConstants;

@DatabaseSetup("classpath:/data/testData.xml")
public class UserReceiptImageRestApiIT extends AbstractUserRestApiIntegrationTest {
    @Value("classpath:/data/sample1.txt")
    private Resource sampleReceipt1;

    @Value("classpath:/data/sample2.txt")
    private Resource sampleReceipt2;

    @Inject
    private ReceiptImageRepository receiptImageRepository;

    @Test
    public void getUserReceiptImages_ShouldReturnUserReceiptImages() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        final String receiptLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.receipt.href");
        final String receiptUrl =
                UriTemplate.fromTemplate(receiptLink)
                           .set("receiptId", "receipt001")
                           .expand();

        final String imagesUrl =
                given().filter(sessionFilter)
                       .when().get(receiptUrl)
                       .then().extract().path("_links.images.href");

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(imagesUrl)
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
            .body("_embedded.receiptImages[0].id", equalTo("image001"))
            .body("_embedded.receiptImages[0].status", equalTo(ProcessStatusType.UPLOADED.name()))
            .body("_embedded.receiptImages[1].id", equalTo("image003"))
            .body("_embedded.receiptImages[2].id", equalTo("image002"))
            .body("_embedded.receiptImages[3].id", equalTo("image004"))
        ;
    }

    @Test
    public void uploadReceiptImage_ShouldAddReceiptImage_AndSaveImageFile() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        String uploadUrl =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.upload.href");

        Response response =
            given()
                .filter(sessionFilter)
                .multiPart("file", sampleReceipt1.getFile())
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

        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            //.body("images[0].status", equalTo(ProcessStatusType.SCANNED.name()))
        ;

        //response.prettyPrint();

        // upload second image
        uploadUrl = response.then().extract().path("_links.upload.href");
        response = given()
            .filter(sessionFilter)
            .multiPart("file", sampleReceipt2.getFile())
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

        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            //.body("status", equalTo(ProcessStatusType.SCANNED.name()))
        ;

        //response.prettyPrint();

        // verify image in FileSystem
        String fileName = response.then().extract().path("fileName");
        Path imageFile = fileSystemService.getReceiptImageSubFolder(TEST_USERID_JOHN_DOE).resolve(fileName);
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals("Another Test", reader.readLine());

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

        String receiptLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.receipt.href");
        String receiptUrl =
                UriTemplate.fromTemplate(receiptLink)
                           .set("receiptId", "receipt001")
                           .expand();
        String imageLink =
                given().filter(sessionFilter)
                       .when().get(receiptUrl)
                       .then().extract().path("_links.image.href");
        String imageUrl =
                UriTemplate.fromTemplate(imageLink)
                           .set("imageId", "image001")
                           .expand();

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
        ReceiptImage image = receiptImageRepository.findOne("image001");
        assertNull(image);
    }

}
