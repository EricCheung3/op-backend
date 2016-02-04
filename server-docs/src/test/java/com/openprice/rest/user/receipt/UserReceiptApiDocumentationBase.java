package com.openprice.rest.user.receipt;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.jsonpath.JsonPath;
import com.openprice.common.ApiConstants;
import com.openprice.common.TextResourceUtils;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptStatusType;
import com.openprice.rest.user.UserApiDocumentationBase;

public abstract class UserReceiptApiDocumentationBase extends UserApiDocumentationBase {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestUser();
        createReceipts();
    }

    @After
    public void teardown() throws Exception {
        deleteReceipts();
        deleteTestUser();
    }

    @Value("classpath:/ocrResult.txt")
    private Resource ocrResultResource;

    protected void createReceipts() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "base64codedimg".getBytes());

        String receiptLocation =
            mockMvc
            .perform(
                fileUpload(ApiConstants.EXTERNAL_API_ROOT + URL_USER_RECEIPTS_UPLOAD)
                .file(file)
                .with(user(USERNAME))
                .with(csrf())
            )
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        String responseContent =
            mockMvc
            .perform(get(receiptLocation).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        String uploadLink = JsonPath.read(responseContent, "_links.upload.href");

        file = new MockMultipartFile("file", "image2.jpg", "image/jpeg", "base64codedimg".getBytes());

        mockMvc
        .perform(
            fileUpload(uploadLink)
            .file(file)
            .with(user(USERNAME))
            .with(csrf())
        );

        // set sample ocr result to image
        final String ocrResult = TextResourceUtils.loadTextResource(ocrResultResource);
        final String receiptId = receiptLocation.substring(receiptLocation.lastIndexOf("/")+1);
        Receipt receipt = receiptRepository.findOne(receiptId);
        List<ReceiptImage> images = receiptImageRepository.findByReceiptOrderByCreatedTime(receipt);
        for (ReceiptImage image : images) {
            image.setOcrResult(ocrResult);
            receiptImageRepository.save(image);
        }
        // trigger parser
        receiptParsingService.parseOcrAndSaveResults(receipt, Arrays.asList(ocrResult));
        receipt.setStatus(ReceiptStatusType.HAS_RESULT);
        receiptRepository.save(receipt);
    }

    protected void deleteReceipts() throws Exception {
        UserAccount user = userAccountRepository.findByEmail(USERNAME);
        for (Receipt receipt : receiptRepository.findByUser(user)) {
            receiptRepository.delete(receipt);
        }
    }

    protected String userReceiptsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String receiptsLink = JsonPath.read(responseContent, "_links.receipts.href");
        return UriTemplate.fromTemplate(receiptsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userReceiptUploadUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_links.upload.href");
    }

    protected String userReceiptUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receipts[0]._links.self.href");
    }

    protected String userReceiptFeedbackUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receipts[0]._links.feedback.href");
    }

    protected String userReceiptImageUploadUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receipts[0]._links.upload.href");
    }

    protected String userReceiptParserResultUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receipts[0]._links.result.href");
    }

    protected String userReceiptParserResultItemsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String itemsLink = JsonPath.read(responseContent, "_embedded.receipts[0]._links.items.href");
        return UriTemplate.fromTemplate(itemsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userReceiptParserResultItemUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptParserResultItemsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receiptItems[0]._links.self.href");
    }

    protected String userReceiptImagesUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String imagesLink = JsonPath.read(responseContent, "_embedded.receipts[0]._links.images.href");
        return UriTemplate.fromTemplate(imagesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String userReceiptImageUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptImagesUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receiptImages[0]._links.self.href");
    }

}
