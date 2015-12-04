package com.openprice.rest.internal;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.openprice.common.ApiConstants;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.internal.api.ImageQueueRequest;
import com.openprice.internal.api.InternalServiceApiUrls;

@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest(randomPort = true)
@TestExecutionListeners(listeners = {
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@ActiveProfiles("dev")
@SpringApplicationConfiguration(classes = {InternalApiTestApplication.class})
public class ImageQueueRestApiIT {

    @Value("${local.server.port}")
    private int port;

    @Inject
    private ReceiptService receiptService;

    @Inject
    private ReceiptRepository receiptRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DatabaseSetup("classpath:/data/testData.xml")
    public void addImage_ShouldAddProcessItemToQueue() {
        final String TEST_RECEIPT_ID = "user001rec001";
        final String TEST_IMAGE_ID = "user001rec001image001";
        final ImageQueueRequest request = new ImageQueueRequest(TEST_IMAGE_ID, "user001", "user001");

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post(ApiConstants.INTERNAL_API_ROOT + InternalServiceApiUrls.URL_IMAGE_QUEUE)
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType(ContentType.JSON)
            .body("success", equalTo(true))
        ;

        // check ocr result for receipt TODO enabled after ABBYY ready
        //final Receipt receipt = receiptRepository.findOne(TEST_RECEIPT_ID);
        //final List<String> ocrResults = receiptService.loadOcrResults(receipt);
        //assertEquals(1, ocrResults.size());
    }
}
