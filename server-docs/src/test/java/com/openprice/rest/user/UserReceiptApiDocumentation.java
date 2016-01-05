package com.openprice.rest.user;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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

import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.jsonpath.JsonPath;
import com.openprice.common.ApiConstants;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.product.ProductCategory;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreChainRepository;
import com.openprice.domain.store.StoreService;
import com.openprice.parser.common.TextResourceUtils;
import com.openprice.rest.user.receipt.FeedbackForm;
import com.openprice.rest.user.receipt.UserReceiptItemForm;

public class UserReceiptApiDocumentation extends UserApiDocumentationBase {

    @Test
    public void receiptListExample() throws Exception {
        mockMvc
        .perform(get(userReceiptsUrl()).with(user(USERNAME)))
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
            fileUpload(userReceiptUploadUrl())
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
        mockMvc
        .perform(get(userReceiptUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("images").description("<<resources-user-receipt-images, Link>> to receipt images"),
                linkWithRel("image").description("<<resources-user-receipt-image, Link>> to receipt image"),
                linkWithRel("result").description("<<resources-user-receipt-result, Link>> to receipt latest parser result"),
                linkWithRel("items").description("<<resources-user-receipt-items, Link>> to receipt latest parser result items"),
                linkWithRel("item").description("<<resources-user-receipt-item, Link>> to receipt parser result item"),
                linkWithRel("upload").description("<<resources-user-receipt-image-upload, Link>> to upload more image for this receipt"),
                linkWithRel("feedback").description("<<resources-user-receipt-feedback, Link>> to receipt feedback")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                //fieldWithPath("images").description("Receipt image list"), // TODO remove it later
                fieldWithPath("_embedded.receiptImages").description("Receipt image list"),
                fieldWithPath("needFeedback").description("Whether user can give feedback"),
                fieldWithPath("_links").description("<<resources-user-receipt-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void receiptFeedbackCreateExample() throws Exception {
        final FeedbackForm form = new FeedbackForm(1, "Poor result!");

        mockMvc
        .perform(
            post(userReceiptFeedbackUrl())
            .with(user(USERNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isNoContent())
        .andDo(document("user-receipt-feedback-create-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("rating").description("The user rating for the receipt, right now we only use 1 or 0 to indicate good or bad."),
                fieldWithPath("comment").description("The user comment for the receiptd.")
            )
        ));
    }

    @Test
    public void receiptDeleteExample() throws Exception {
        mockMvc
        .perform(delete(userReceiptUrl()).with(user(USERNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("user-receipt-delete-example"));
    }

    @Test
    public void receiptImageListExample() throws Exception {
        mockMvc
        .perform(get(userReceiptImagesUrl()).with(user(USERNAME)))
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
    public void receiptImageRetrieveExample() throws Exception {
        mockMvc
        .perform(get(userReceiptImageUrl()).with(user(USERNAME)))
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
                fieldWithPath("downloadUrl").description("Receipt image JPEG file download URL"),
                fieldWithPath("base64Url").description("Receipt image Base64 data download URL"),
                fieldWithPath("_links").description("<<resources-user-receipt-image-links, Links>> to other resources")
            )
        ));

    }

    @Test
    public void receiptImageUploadExample() throws Exception {
        final MockMultipartFile file = new MockMultipartFile("file", "image2.jpg", "image/jpeg", "base64codedimg".getBytes());
        mockMvc
        .perform(
            fileUpload(userReceiptImageUploadUrl())
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
    }

    @Test
    public void receiptParserResultRetrieveExample() throws Exception {
        mockMvc
        .perform(get(userReceiptParserResultUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-parser-result-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("items").description("<<resources-user-receipt-parser-result-item-list, Link>> to Receipt Parser Result Items list resource"),
                linkWithRel("item").description("<<resources-user-receipt-parser-result-item, Link>> to Receipt Parser Result Items resource")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("chainCode").description("Recognized store chain code, maybe null"),
                fieldWithPath("storeName").description("Recognized store chain name, default to '[Unknown]'"),
                fieldWithPath("branchName").description("Recognized store branch name, maybe null"),
                fieldWithPath("parsedTotal").description("parsed field value for Total"),
                fieldWithPath("parsedDate").description("parsed field value for Date"),
                fieldWithPath("items").description("parsed receipt items"), // TODO remove it later
                fieldWithPath("_embedded.receiptItems").description("parsed receipt items"),
                fieldWithPath("_links").description("<<resources-user-receipt-parser-result-links, Links>> to other resources")
            )
       ));
    }

    @Test
    public void receiptParserResultItemListExample() throws Exception {
        mockMvc
        .perform(get(userReceiptParserResultItemsUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-parser-result-item-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receiptItems").description("An array of <<resources-user-receipt-parser-result-item, Parser Result Receipt Item resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-user-receipt-parser-result-item-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void receiptParserResultItemRetrieveExample() throws Exception {
        mockMvc
        .perform(get(userReceiptParserResultItemUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-parser-result-item-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("result").description("<<resources-user-receipt-result, Link>> to receipt latest parser result"),
                linkWithRel("items").description("<<resources-user-receipt-items, Link>> to receipt latest parser result items")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("catalogCode").description("Parser parsed matching catalog code"),
                fieldWithPath("parsedName").description("Parser parsed item name"),
                fieldWithPath("displayName").description("User editable item name"),
                fieldWithPath("parsedPrice").description("Parser parsed item price"),
                fieldWithPath("displayPrice").description("User editable item price"),
                fieldWithPath("catalog").description("Catalog object for the item"),
                fieldWithPath("_links").description("<<resources-user-receipt-parser-result-item-retrieve-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void receiptParserResultItemUpdateExample() throws Exception {
        final UserReceiptItemForm form = new UserReceiptItemForm("eggs", "3.99");

        mockMvc
        .perform(
            put(userReceiptParserResultItemUrl())
            .with(user(USERNAME))
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.objectMapper.writeValueAsString(form))
        )
        .andExpect(status().isNoContent())
        .andDo(document("user-receipt-parser-result-item-update-example",
            preprocessRequest(prettyPrint()),
            requestFields(
                fieldWithPath("name").description("User edited item name."),
                fieldWithPath("price").description("User edited item price.")
            )
        ));
    }

    @Test
    public void receiptParserResultItemDeleteExample() throws Exception {
        mockMvc
        .perform(delete(userReceiptParserResultItemUrl()).with(user(USERNAME)))
        .andExpect(status().isNoContent())
        .andDo(document("user-receipt-parser-result-item-delete-example"));
    }

    @Inject
    StoreService storeService;

    @Inject
    StoreChainRepository storeRepository;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        createTestUser();
        createStores();
        createReceipts();
    }

    @After
    public void teardown() throws Exception {
        deleteReceipts();
        deleteStores();
        deleteTestUser();
    }

    protected void createStores() throws Exception {
        StoreChain rcss = storeService.createStoreChain("rcss", "Real Canadian Superstore");
        storeService.createStoreBranch(rcss, "Calgary Trail RCSS", "780-430-2769", "", "4821, Calgary Trail", "", "Edmonton", "AB", "", "Canada");
        storeService.createStoreBranch(rcss, "South Common RCSS", "780-490-3918", "", "1549 9711, 23 AVE NW", "", "Edmonton", "AB", "", "Canada");
        storeService.createCatalogProduct(rcss, "EGG", "1234", "1.99", "Large Egg", "Food,Egg", ProductCategory.meat);
        storeService.createCatalogProduct(rcss, "EGG", "1235", "1.59", "Medium Egg", "Food,Egg", ProductCategory.meat);
        storeService.createCatalogProduct(rcss, "EGG", "1236", "1.29", "Small Egg", "Food,Egg", ProductCategory.meat);

        storeService.createStoreChain("safeway", "Safeway");
    }

    protected void deleteStores() throws Exception {
        storeRepository.deleteAll();
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
    }

    protected void deleteReceipts() throws Exception {
        UserAccount user = userAccountRepository.findByEmail(USERNAME);
        for (Receipt receipt : receiptRepository.findByUser(user)) {
            receiptRepository.delete(receipt);
        }
    }

    private String userReceiptsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String receiptsLink = JsonPath.read(responseContent, "_links.receipts.href");
        return UriTemplate.fromTemplate(receiptsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String userReceiptUploadUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_links.upload.href");
    }

    private String userReceiptUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receipts[0]._links.self.href");
    }

    private String userReceiptFeedbackUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receipts[0]._links.feedback.href");
    }

    private String userReceiptImageUploadUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receipts[0]._links.upload.href");
    }

    private String userReceiptParserResultUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receipts[0]._links.result.href");
    }

    private String userReceiptParserResultItemsUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String itemsLink = JsonPath.read(responseContent, "_embedded.receipts[0]._links.items.href");
        return UriTemplate.fromTemplate(itemsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String userReceiptParserResultItemUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptParserResultItemsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receiptItems[0]._links.self.href");
    }

    private String userReceiptImagesUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptsUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String imagesLink = JsonPath.read(responseContent, "_embedded.receipts[0]._links.images.href");
        return UriTemplate.fromTemplate(imagesLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    private String userReceiptImageUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(userReceiptImagesUrl()).with(user(USERNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.receiptImages[0]._links.self.href");
    }

}
