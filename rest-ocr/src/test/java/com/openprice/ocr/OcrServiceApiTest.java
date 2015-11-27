package com.openprice.ocr;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessRequest;
import com.openprice.ocr.api.OcrServerApiUrls;

@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest(randomPort = true)
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@SpringApplicationConfiguration(classes = {OcrServerTestApplication.class})
public class OcrServiceApiTest {

    @Value("${local.server.port}")
    private int port;

    @Inject
    protected FileSystemService fileSystemService;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void process_ShouldReturnOcrResult() {
        final String TEST_FILENAME = "2015_09_09_12_00_01_001.jpg";
        final String TEST_USERID = "user001";
        final byte[] content = "image".getBytes();
        // save test image file
        final Path imageFolder = fileSystemService.getReceiptImageSubFolder(TEST_USERID);
        final Path imageFile = imageFolder.resolve(TEST_FILENAME);
        try (final OutputStream out = new BufferedOutputStream(Files.newOutputStream(imageFile, StandardOpenOption.CREATE_NEW)))
        {
            out.write(content);
        } catch (IOException ex) {
            throw new RuntimeException("System Error! Cannot save image.", ex);
        }

        final ImageProcessRequest request = new ImageProcessRequest(TEST_USERID + fileSystemService.getPathSeparator() + TEST_FILENAME);

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post(OcrServerApiUrls.URL_OCR_PROCESSOR)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("success", equalTo(true))
            .body("ocrResult", equalTo("result"))
        ;

    }
}
