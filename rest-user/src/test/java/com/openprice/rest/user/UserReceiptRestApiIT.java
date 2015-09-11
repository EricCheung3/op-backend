package com.openprice.rest.user;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

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
import com.openprice.rest.UtilConstants;

@DatabaseSetup("classpath:/data/testData.xml")
public class UserReceiptRestApiIT extends AbstractUserRestApiIntegrationTest {
    @Value("classpath:/data/sample1.txt")
    private Resource sampleReceipt1;

    @Value("classpath:/data/sample2.txt")
    private Resource sampleReceipt2;

    @Test
    public void getCurrentUserReceipts_ShouldReturnAllUserReceipts() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        // get receipts link
        String receiptsLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.receipts.href");
        String receiptsUrl =  UriTemplate.fromTemplate(receiptsLink)
                                         .set("page", 0)
                                         .set("size", 10)
                                         .set("sort", null)
                                         .expand();

        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(receiptsUrl)
            ;
        //response.prettyPrint();

        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("page.size", equalTo(3))
            .body("page.totalElements", equalTo(14))
            .body("page.totalPages", equalTo(5))
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
    public void getUserReceiptById_ShouldReturnUserReceipt() {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        String receiptLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.receipt.href");
        String receiptUrl =  UriTemplate.fromTemplate(receiptLink)
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
            .body("images[0].id", equalTo("image001"))
            .body("images[0].status", equalTo(ProcessStatusType.UPLOADED.name()))
            .body("images[1].id", equalTo("image003"))
            .body("images[2].id", equalTo("image002"))
            .body("images[3].id", equalTo("image004"))
            .body("_links.images.href", endsWith(receiptUrl + "/images"))
            .body("_links.items.href", endsWith(receiptUrl + "/items"))
        ;
    }

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
    public void createNewReceipt_ShouldCreateReceipt_AndSaveImage_FromBase64String() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        String receiptsLink =
                given().filter(sessionFilter)
                       .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                       .then().extract().path("_links.receipts.href");
        String receiptsUrl =  UriTemplate.fromTemplate(receiptsLink)
                                         .set("page", null)
                                         .set("size", null)
                                         .set("sort", null)
                                         .expand();

        // add new image as base64 encoded string
        final String base64String = Base64.getEncoder().encodeToString("test".getBytes());
        ImageDataForm form = new ImageDataForm();
        form.setBase64String(base64String);
        Response response =
            given()
                .filter(sessionFilter)
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(receiptsUrl)
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
        final String receiptId = response.then().extract().path("id");
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            //.body("images[0].status", equalTo(ProcessStatusType.SCANNED.name())) TODO should be UPLOADED without process thread
            .body("_links.images.href", endsWith(UserApiUrls.URL_USER_RECEIPTS + "/" + receiptId + "/images"))
        ;

        //response.prettyPrint();

        // verify image in FileSystem
        String fileName = response.then().extract().path("images[0].fileName");
        Path imageFile = fileSystemService.getReceiptImageSubFolder(TEST_USERID_JOHN_DOE).resolve(fileName);
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals("test", reader.readLine());

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
    public void uploadNewReceipt_ShouldCreateReceipt_AndSaveImageFile() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        String uploadUrl =
            given().filter(sessionFilter)
                   .when().get(UtilConstants.API_ROOT + UserApiUrls.URL_USER)
                   .then().extract().path("_links.upload.href");

        // add new image as base64 encoded string
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
        Path imageFile = fileSystemService.getReceiptImageSubFolder(TEST_USERID_JOHN_DOE).resolve(fileName);
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals("test", reader.readLine());

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

}
