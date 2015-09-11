package com.openprice.ocr;

import static com.jayway.restassured.RestAssured.when;
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

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.openprice.common.api.OcrServerApiUrls;
import com.openprice.file.FileSystemService;

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
        final String TEST_USERNAME = "john.doe@openprice.com";
        final byte[] content = "image".getBytes();
        // save test file
        final Path imageFolder = fileSystemService.getReceiptImageSubFolder(TEST_USERID);
        final Path imageFile = imageFolder.resolve(TEST_FILENAME);
        try (final OutputStream out = new BufferedOutputStream(Files.newOutputStream(imageFile, StandardOpenOption.CREATE_NEW)))
        {
            out.write(content);
        } catch (IOException ex) {
            throw new RuntimeException("System Error! Cannot save image.", ex);
        }

        String url =  UriTemplate.fromTemplate(OcrServerApiUrls.URL_OCR_PROCESS)
                                 .set("userId", TEST_USERID)
                                 .set("fileName", TEST_FILENAME)
                                 .set("username", TEST_USERNAME)
                                 .expand();
        when()
            .get(url)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("username", equalTo(TEST_USERNAME))
            .body("fileName", equalTo(TEST_FILENAME))
            .body("ocrResult", equalTo("result"))
        ;

    }
}
