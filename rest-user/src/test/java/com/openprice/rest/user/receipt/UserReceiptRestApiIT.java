package com.openprice.rest.user.receipt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;
import com.openprice.rest.user.UserApiTestApplication;
import com.openprice.rest.user.UserApiUrls;

@SpringApplicationConfiguration(classes = {UserApiTestApplication.class})
@DatabaseSetup("classpath:/data/testData.xml")
public class UserReceiptRestApiIT extends AbstractUserRestApiIntegrationTest {
    @Value("classpath:/data/sample1.txt")
    private Resource sampleReceipt1;

    @Value("classpath:/data/sample2.txt")
    private Resource sampleReceipt2;

    @Inject
    private ReceiptRepository receiptRepository;

    @Inject
    private ReceiptImageRepository receiptImageRepository;

    @Test
    public void getCurrentUserReceipts_ShouldReturnAllUserReceipts() {
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

        given()
            .filter(sessionFilter)
        .when()
            .get(userReceiptUrl(sessionFilter, "receipt001"))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("id", equalTo("receipt001"))
            .body("images[0].id", equalTo("image001"))
            .body("images[0].status", equalTo(ProcessStatusType.UPLOADED.name()))
            .body("images[1].id", equalTo("image003"))
            .body("images[2].id", equalTo("image002"))
            .body("images[3].id", equalTo("image004"))
            .body("_links.images.href", endsWith("/images" + UtilConstants.PAGINATION_TEMPLATES))
            .body("_links.image.href", endsWith("/images/{imageId}"))
            //.body("_links.items.href", endsWith("/items"))
        ;
    }


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
        final String receiptId = response.then().extract().path("id");
        response
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            //.body("images[0].status", equalTo(ProcessStatusType.SCANNED.name())) TODO should be UPLOADED without process thread
            .body("_links.images.href", endsWith(UserApiUrls.URL_USER_RECEIPTS + "/" + receiptId + "/images" + UtilConstants.PAGINATION_TEMPLATES))
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