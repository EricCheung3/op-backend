package com.openprice.rest.admin;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import com.damnhandy.uri.template.UriTemplate;
import com.jayway.jsonpath.JsonPath;
import com.openprice.common.ApiConstants;
import com.openprice.domain.account.admin.AdminAccount;
import com.openprice.domain.account.admin.AdminAccountRepository;
import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptFeedback;
import com.openprice.domain.receipt.ReceiptFeedbackRepository;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptItemRepository;
import com.openprice.domain.receipt.ReceiptResult;
import com.openprice.domain.receipt.ReceiptResultRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.rest.ApiDocumentationBase;

public abstract class AdminApiDocumentationBase extends ApiDocumentationBase {
    public static final String ADMINNAME = "jdoe";

    @Inject
    protected AdminAccountService adminAccountService;

    @Inject
    protected AdminAccountRepository adminAccountRepository;

    @Inject
    protected ReceiptResultRepository receiptResultRepository;

    @Inject
    protected ReceiptItemRepository receiptItemRepository;

    @Inject
    protected ReceiptFeedbackRepository receiptFeedbackRepository;

    @Inject
    protected ReceiptService receiptService;

    protected String createTestAdmin() throws Exception {
        adminAccountService.createAdminAccount(ADMINNAME, "password", "John", "Doe", "john.doe@email.com", "VP Marketing");
        return ADMINNAME;
    }

    protected void deleteTestAdmin() throws Exception {
        AdminAccount account = adminAccountRepository.findByUsername(ADMINNAME);
        adminAccountRepository.delete(account);
    }

    protected void createReceipts() throws Exception {
        final UserAccount user = userAccountRepository.findByEmail(USERNAME);
        Receipt receipt = Receipt.testObjectBuilder().user(user).build();
        receipt = receiptRepository.save(receipt);

        // add two images to the receipt
        final ReceiptImage image1 = receipt.createImage();
        image1.setStatus(ProcessStatusType.UPLOADED);
        receiptImageRepository.save(image1);
        final ReceiptImage image2 = receipt.createImage();
        image2.setStatus(ProcessStatusType.UPLOADED);
        receiptImageRepository.save(image2);

        receipt = Receipt.testObjectBuilder().user(user).build();
        receipt = receiptRepository.save(receipt);
        final ReceiptImage image = receipt.createImage();
        image.setStatus(ProcessStatusType.UPLOADED);
        receiptImageRepository.save(image);

        // add parse result to one receipt
        final ReceiptResult receiptResult = ReceiptResult.testObjectBuilder()
                                                           .receipt(receipt)
                                                           .chainCode("rcss")
                                                           .branchName("Calgary Trail")
                                                           .date("2014/5/12")
                                                           .total("13.72")
                                                           .build();
        receiptResultRepository.save(receiptResult);

        // add items to the receipt
        final ReceiptItem item = ReceiptItem.testObjectBuilder()
                                            .id("")
                                            .receiptResult(receiptResult)
                                            .parsedName("apple")
                                            .displayName("Apple")
                                            .parsedPrice("0.32")
                                            .displayPrice("0.32")
                                            .lineNumber(1)
                                            .catalogCode("Fruit")
                                            .build();
        receiptItemRepository.save(item);
        // add two feedback to one receipt (second one)
        receiptService = new ReceiptService(receiptRepository, receiptFeedbackRepository);
        final ReceiptFeedback feedback = receiptService.addFeedback(receipt, 5, "Excellent!");
        receiptFeedbackRepository.save(feedback);

        receipt = receiptRepository.findOne(receipt.getId());
        receipt.setNeedFeedback(true);
        final ReceiptFeedback feedback2 = receiptService.addFeedback(receipt, 4, "Good!");
        receiptFeedbackRepository.save(feedback2);
    }

    protected void deleteReceipts() throws Exception {
        final UserAccount user = userAccountRepository.findByEmail(USERNAME);
        for (final Receipt receipt : receiptRepository.findByUser(user)) {
            receiptRepository.delete(receipt);
        }
    }

    protected String adminUrl() {
        return ApiConstants.EXTERNAL_API_ROOT + AdminApiUrls.URL_ADMIN;
    }

    protected String usersUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(adminUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        final String receiptsLink = JsonPath.read(responseContent, "_links.users.href");
        return UriTemplate.fromTemplate(receiptsLink).set("page", null).set("size", null).set("sort", null).expand();
    }

    protected String testUserUrl() throws Exception {
        final String responseContent =
            mockMvc
            .perform(get(usersUrl()).with(user(ADMINNAME)))
            .andExpect(status().isOk())
            .andReturn().getResponse()
            .getContentAsString();
        return JsonPath.read(responseContent, "_embedded.userAccounts[0]._links.self.href");
    }

}
