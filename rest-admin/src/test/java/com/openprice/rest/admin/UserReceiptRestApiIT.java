package com.openprice.rest.admin;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.damnhandy.uri.template.UriTemplate;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.UtilConstants;

public class UserReceiptRestApiIT extends AbstractAdminRestApiIntegrationTest {

    @Value("classpath:/data/sample1.txt")
    private Resource sampleReceipt1;

    @Value("classpath:/data/sample2.txt")
    private Resource sampleReceipt2;

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getUserReceipts_ShouldReturnAllUserReceipts() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        final String userUrl =  getUserLinkUrl(sessionFilter, TEST_USERID_JOHN_DOE); // John Doe

        // get receipts link
        String receiptsUrl = given().filter(sessionFilter)
                                     .when().get(userUrl)
                                     .then().extract().path("_links.receipts.href");

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptsUrl)
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
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void getUserReceiptById_ShouldReturnCorrectReceipt() {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        String receiptUrl =  UriTemplate.fromTemplate(UtilConstants.API_ROOT+AdminApiUrls.URL_ADMIN_USERS_USER_RECEIPTS_RECEIPT)
                                        .set("userId", TEST_USERID_JOHN_DOE)
                                        .set("receiptId", "receipt001")
                                        .expand();

        given()
            .filter(sessionFilter)
        .when()
            .get(receiptUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("receipt001"))
        ;
    }

    @Test
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void uploadNewReceipt_ShouldCreateReceipt_AndSaveImageFile() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        final String userUrl =  getUserLinkUrl(sessionFilter, TEST_USERID_JANE_DOE);

        String uploadUrl =
            given().filter(sessionFilter)
                   .when().get(userUrl)
                   .then().extract().path("_links.upload.href");

        // add new image
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

        // verify new receipt
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

        // verify image in FileSystem
        String fileName = response.then().extract().path("images[0].fileName");
        Path imageFilePath = fileSystemService.getImageSubFolder(TEST_USERID_JANE_DOE).resolve(fileName);
        Assert.assertTrue(Files.exists(imageFilePath));

        String downloadUrl = response.then().extract().path("images[0]._links.download.href");
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
    @DatabaseSetup("classpath:/data/testAdmin.xml")
    public void uploadReceiptImage_ShouldAddReceiptImage_AndSaveImage() throws Exception {
        final SessionFilter sessionFilter = login(TEST_ADMIN_USERNAME_JOHN_DOE);

        final String userUrl =  getUserLinkUrl(sessionFilter, TEST_USERID_JANE_DOE);
        String uploadUrl =
                given().filter(sessionFilter)
                       .when().get(userUrl)
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

        String fileName = response.then().extract().path("fileName");
        Path imageFilePath = fileSystemService.getImageSubFolder(TEST_USERID_JANE_DOE).resolve(fileName);
        Assert.assertTrue(Files.exists(imageFilePath));

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

}
