package com.openprice.rest.user.receipt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
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

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/testData.xml")
public class UserReceiptUploadRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Value("classpath:/data/sample1.txt")
    private Resource sampleReceipt1;

    @Value("classpath:/data/sample2.txt")
    private Resource sampleReceipt2;

    @Value("classpath:/data/ocrResult.txt")
    private Resource sampleOcrResult;

    @Test
    public void createNewReceipt_ShouldCreateReceipt_AndSaveImage_FromBase64String() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        // add new image as base64 encoded string
        final String base64String = Base64.getEncoder().encodeToString("test".getBytes());
        final ImageDataForm form = new ImageDataForm(base64String);

        Response response =
            given()
                .filter(sessionFilter)
                .contentType(ContentType.JSON)
                .body(form)
            .when()
                .post(userReceiptsUrl(sessionFilter))
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
        //response.prettyPrint();
        final String receiptId = response.then().extract().path("id");
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("_links.images.href", endsWith(URL_USER_RECEIPTS + "/" + receiptId + "/images" + UtilConstants.PAGINATION_TEMPLATES))
        ;

        // verify image in FileSystem
        String fileName = response.then().extract().path("_embedded.receiptImages[0].fileName");
        Path imageFile = fileSystemService.getReceiptImageSubFolder(TEST_USERID_JOHN_DOE).resolve(fileName);
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals("test", reader.readLine());

        String downloadUrl = response.then().extract().path("_embedded.receiptImages[0]._links.download.href");
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

        // add new image as base64 encoded string
        Response response =
            given()
                .filter(sessionFilter)
                .multiPart("file", sampleReceipt1.getFile())
            .when()
                .post(userReceiptUploadUrl(sessionFilter))
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
        ;
        //response.prettyPrint();

        // verify image in FileSystem
        String fileName = response.then().extract().path("_embedded.receiptImages[0].fileName");
        Path imageFile = fileSystemService.getReceiptImageSubFolder(TEST_USERID_JOHN_DOE).resolve(fileName);
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals("test", reader.readLine());

        String downloadUrl = response.then().extract().path("_embedded.receiptImages[0]._links.download.href");
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
    public void hackloadNewReceipt_ShouldCreateReceipt_AndSaveImageFileWithOcrResult() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        // add new image as base64 encoded string
        Response response =
            given()
                .filter(sessionFilter)
                .multiPart("image", sampleReceipt1.getFile())
                .multiPart("ocr", sampleOcrResult.getFile())
            .when()
                .post("/api/user/receipts/hackload")
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
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            //.body("images[0].status", equalTo(ProcessStatusType.SCANNED.name()))
        ;

        // verify image in FileSystem
        String fileName = response.then().extract().path("_embedded.receiptImages[0].fileName");
        Path imageFile = fileSystemService.getReceiptImageSubFolder(TEST_USERID_JOHN_DOE).resolve(fileName);
        assertTrue(Files.exists(imageFile));
        BufferedReader reader = Files.newBufferedReader(imageFile, Charset.defaultCharset());
        assertEquals("test", reader.readLine());

        String downloadUrl = response.then().extract().path("_embedded.receiptImages[0]._links.download.href");
        given()
            .filter(sessionFilter)
        .when()
            .get(downloadUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType("image/jpeg")
        ;
    }

    //@Test
    public void hackloadOcrResult_ShouldUpdateReceiptImageOcrResult() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        // add new image as base64 encoded string
        Response response =
            given()
                .filter(sessionFilter)
                .multiPart("file", sampleReceipt1.getFile())
            .when()
                .post(userReceiptUploadUrl(sessionFilter))
            ;

        response
        .then()
            .statusCode(HttpStatus.SC_CREATED)
        ;

        // hackload OCR
        String receiptUrl = response.getHeader("Location");
        response =
            given()
                .filter(sessionFilter)
                .multiPart("ocr", sampleOcrResult.getFile())
            .when()
                .post(receiptUrl + "/hackload")
            ;
        assertEquals(receiptUrl, response.getHeader("Location"));

        // verify result
        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(receiptUrl)
            ;
        //response.prettyPrint();

        String resultUrl = response.then().extract().path("_links.result.href");
        response =
            given()
                .filter(sessionFilter)
            .when()
                .get(resultUrl)
            ;
        //response.prettyPrint();
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("chainCode", equalTo("rcss"))
            .body("branchName", equalTo("Calgary Trail"))
            .body("parsedTotal", equalTo("104.73"))
            .body("_embedded.receiptItems[0].catalogCode", equalTo(""))
            .body("_embedded.receiptItems[0].parsedName", equalTo("k dgon cook    wine    mrj"))
            .body("_embedded.receiptItems[0].parsedPrice", equalTo("2.69"))
            .body("_embedded.receiptItems[0].catalog", nullValue())
            .body("_embedded.receiptItems[1].catalogCode", equalTo("rooster garlic_06038388591"))
            .body("_embedded.receiptItems[1].parsedName", equalTo("rooster garlic"))
            .body("_embedded.receiptItems[1].parsedPrice", equalTo("0.68"))
            .body("_embedded.receiptItems[1].catalog", nullValue())
            .body("_embedded.receiptItems[2].catalogCode", equalTo(""))
            .body("_embedded.receiptItems[2].parsedName", equalTo("ducks fr7n    mrj"))
            .body("_embedded.receiptItems[2].parsedPrice", equalTo("15.23"))
            .body("_embedded.receiptItems[2].catalog", nullValue())
            .body("_embedded.receiptItems[3].catalogCode", equalTo("hairtail_77016160104"))
            .body("_embedded.receiptItems[3].parsedName", equalTo("hairtail"))
            .body("_embedded.receiptItems[3].parsedPrice", equalTo("7.36"))
            .body("_embedded.receiptItems[3].catalog", nullValue())
        ;

    }


}
