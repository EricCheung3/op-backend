package com.openprice.rest.user;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.jayway.jsonpath.JsonPath;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.rest.ApiDocumentationBase;
import com.openprice.rest.UtilConstants;

public class UserReceiptApiDocumentation extends ApiDocumentationBase {

    @Test
    public void receiptListExample() throws Exception {
        mockMvc
        .perform(get(UtilConstants.API_ROOT + UserApiUrls.URL_USER_RECEIPTS).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receipts").description("An array of <<resources-user-receipt,Receipt resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-user-receipt-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void uploadReceiptExample() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "base64codedimg".getBytes());

        mockMvc
        .perform(
            fileUpload(UtilConstants.API_ROOT + UserApiUrls.URL_USER_RECEIPTS_UPLOAD)
            .file(file)
            .param("filename", "test.jpg")
            .with(user(USERNAME))
        )
        .andExpect(status().isCreated())
        .andDo(document("user-receipt-upload-example",
            requestParameters(
                parameterWithName("filename").description("The uploaded image file name")
            )
        ));
    }

    @Test
    public void receiptRetrieveExample() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "base64codedimg".getBytes());

        String receiptLocation =
            mockMvc
            .perform(
                fileUpload(UtilConstants.API_ROOT + UserApiUrls.URL_USER_RECEIPTS_UPLOAD)
                .file(file)
                .with(user(USERNAME))
                .with(csrf())

            )
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        mockMvc
        .perform(get(receiptLocation).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("images").description("<<resources-user-receipt-images, Link>> to receipt images"),
                linkWithRel("image").description("<<resources-user-receipt-image, Link>> to receipt image"),
                linkWithRel("items").description("<<resources-user-receipt-items, Link>> to receipt items"),
                linkWithRel("upload").description("<<resources-user-receipt-image-upload, Link>> to upload more image for this receipt"),
                linkWithRel("feedback").description("<<resources-user-receipt-feedback, Link>> to receipt feedback")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("images").description("Receipt image list"),
                fieldWithPath("rating").description("User rating for the receipt quality (0 or 1 for bad/good), default is null."),
                fieldWithPath("_links").description("<<resources-user-receipt-links,Links>> to other resources")
            )
        ));

        final Map<String, Integer> feedbackUpdate = new HashMap<>();
        feedbackUpdate.put("rating", 1);

        mockMvc
        .perform(
            put(receiptLocation+"/feedback")
            .with(user(USERNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(feedbackUpdate))
        )
        .andExpect(status().isNoContent())
        .andDo(document("user-receipt-feedback-update-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("rating").description("The user rating for the receipt, right now we only use 1 or 0 to indicate good or bad.")
            )
        ));

        mockMvc
        .perform(delete(receiptLocation).with(user(USERNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("user-receipt-delete-example"));

    }

    @Test
    public void receiptImageExample() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "base64codedimg".getBytes());

        String receiptLocation =
            mockMvc
            .perform(
                fileUpload(UtilConstants.API_ROOT + UserApiUrls.URL_USER_RECEIPTS_UPLOAD)
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
        String imagesLink = JsonPath.read(responseContent, "_links.images.href");

        file = new MockMultipartFile("file", "image2.jpg", "image/jpeg", "base64codedimg".getBytes());

        String receiptImageLocation =
            mockMvc
            .perform(
                fileUpload(uploadLink)
                .file(file)
                .with(user(USERNAME))
                .with(csrf())
            )
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getHeader("Location");

        mockMvc
        .perform(get(receiptImageLocation).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-image-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("receipt").description("<<resources-user-receipt,Link>> to owner receipt resource"),
                linkWithRel("download").description("URL for downloading image as jpg file"),
                linkWithRel("base64").description("URL for downloading image as base64 string")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("status").description("Receipt image process status"),
                fieldWithPath("ocrResult").description("Receipt image ocr process result"),
                fieldWithPath("fileName").description("Receipt image file name"),
                fieldWithPath("_links").description("<<resources-user-receipt-image-links,Links>> to other resources")
            )
        ));

        mockMvc
        .perform(
            fileUpload(uploadLink)
            .file(file)
            .param("filename", "test.jpg")
            .with(user(USERNAME))
        )
        .andExpect(status().isCreated())
        .andDo(document("user-receipt-image-upload-example",
            requestParameters(
                parameterWithName("filename").description("The uploaded image file name")
            )
        ));

        mockMvc
        .perform(get(imagesLink).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-image-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receiptImages").description("An array of <<resources-user-receipt-image, ReceiptImage resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-user-receipt-image-list-links,Links>> to other resources")
            )
        ));

    }

    @Test
    public void receiptItemExample() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "base64codedimg".getBytes());

        String receiptLocation =
            mockMvc
            .perform(
                fileUpload(UtilConstants.API_ROOT + UserApiUrls.URL_USER_RECEIPTS_UPLOAD)
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
        String itemsLink = JsonPath.read(responseContent, "_links.items.href");

        mockMvc
        .perform(get(itemsLink).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-item-list-example",
            preprocessResponse(prettyPrint())
        ));

    }

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

    protected void createReceipts() throws Exception {
        final UserAccount user = userAccountRepository.findByEmail(USERNAME);
        Receipt receipt = new Receipt();
        receipt.setUser(user);
        receipt = receiptRepository.save(receipt);

        // add two images to the receipt
        final ReceiptImage image1 = receipt.createImage();
        image1.setStatus(ProcessStatusType.UPLOADED);
        receipt.getImages().add(image1);
        receiptImageRepository.save(image1);
        final ReceiptImage image2 = receipt.createImage();
        image2.setStatus(ProcessStatusType.UPLOADED);
        receipt.getImages().add(image2);
        receiptImageRepository.save(image2);

        receipt = new Receipt();
        receipt.setUser(user);
        receipt = receiptRepository.save(receipt);
        final ReceiptImage image = receipt.createImage();
        image.setStatus(ProcessStatusType.UPLOADED);
        receipt.getImages().add(image);
        receiptImageRepository.save(image);

    }

    protected void deleteReceipts() throws Exception {
        UserAccount user = userAccountRepository.findByEmail(USERNAME);
        for (Receipt receipt : receiptRepository.findByUser(user)) {
            receiptRepository.delete(receipt);
        }
    }

}
