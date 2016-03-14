package com.openprice.rest.user.receipt;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.damnhandy.uri.template.UriTemplate;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.google.common.io.ByteStreams;
import com.jayway.restassured.filter.session.SessionFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.openprice.rest.UtilConstants;
import com.openprice.rest.user.AbstractUserRestApiIntegrationTest;

@DatabaseSetup("classpath:/data/test-data.xml")
public class UserReceiptUploadRestApiIT extends AbstractUserRestApiIntegrationTest {

    @Value("classpath:/data/BostonPizza.JPG")
    private Resource sampleImage;

    @Value("classpath:/data/ocr-result.txt")
    private Resource sampleOcrResult;

    @Test
    public void createNewReceiptWIthBase64String_ShouldCreateReceipt_AndSaveImage_FromBase64String() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        // add new image as base64 encoded string
        final byte[] sampleImageContent = ByteStreams.toByteArray(sampleImage.getInputStream());
        final String base64String = Base64.getEncoder().encodeToString(sampleImageContent);
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

        verifyImage(sessionFilter, response.then().extract().path("_links.images.href"));

    }

    private void verifyImage(final SessionFilter sessionFilter,
                             final String imagesLink) throws Exception {
        Response response =
            given()
                .filter(sessionFilter)
            .when()
                .get(UriTemplate.fromTemplate(imagesLink).set("page", null).set("size", null).set("sort", null).expand())
            ;
        //response.prettyPrint();

        // verify image in FileSystem
        String fileName = response.then().extract().path("_embedded.receiptImages[0].fileName");
        Path imageFile = fileSystemService.getReceiptImageSubFolder(TEST_USERID_JOHN_DOE).resolve(fileName);
        assertTrue(Files.exists(imageFile));
        byte[] data = Files.readAllBytes(imageFile);
        assertTrue(data.length > 0);

        String downloadUrl = response.then().extract().path("_embedded.receiptImages[0]._links.download.href");
        given()
            .filter(sessionFilter)
        .when()
            .get(downloadUrl)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType("image/jpeg")
        ;

        String imageUrl = response.then().extract().path("_embedded.receiptImages[0]._links.self.href");
        response = given()
            .filter(sessionFilter)
        .when()
            .get(imageUrl)
        ;

        String base64 = response.then().extract().path("base64");
        assertEquals(164344, base64.length());

    }

    @Test
    public void uploadNewReceiptWIthImageFile_ShouldCreateReceipt_AndSaveImageFile() throws Exception {
        final SessionFilter sessionFilter = login(TEST_USERNAME_JOHN_DOE);

        // add new image as base64 encoded string
        Response response =
            given()
                .filter(sessionFilter)
                .multiPart("file", sampleImage.getFile())
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

        verifyImage(sessionFilter, response.then().extract().path("_links.images.href"));
    }

}
