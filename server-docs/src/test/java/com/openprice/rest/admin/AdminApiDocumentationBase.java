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
import com.openprice.domain.receipt.ReceiptField;
import com.openprice.domain.receipt.ReceiptFieldRepository;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.domain.receipt.ReceiptItemRepository;
import com.openprice.domain.receipt.ReceiptResult;
import com.openprice.domain.receipt.ReceiptResultRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.parser.ReceiptFieldType;
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
    protected ReceiptFieldRepository receiptFieldRepository;

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
        final ReceiptResult receiptResult = addReceiptResult(receipt, "rcss", "Calgary Trail", "2014/5/12", "13.72");

        addReceiptItem(receiptResult, "apple", "Apple", "1.25", "1.25", 2, "Fruit");
        addReceiptItem(receiptResult, "banana", "Banana", "0.25", "0.25", 3, "Fruit");

        addReceiptField(receiptResult, ReceiptFieldType.AddressCity, "Edmonton", 2);
        addReceiptField(receiptResult, ReceiptFieldType.AddressProv, "AB", 3);

        addReceiptFeedback(receipt, 5, "Excellent!");
        receipt = receiptRepository.findOne(receipt.getId());
        receipt.setNeedFeedback(true);
        addReceiptFeedback(receipt, 4, "Good!");
        receipt = receiptRepository.findOne(receipt.getId());
        receipt.setNeedFeedback(true);
        addReceiptFeedback(receipt, 3, "Poor!");
    }

    protected ReceiptResult addReceiptResult(Receipt receipt, String chainCode, String branchName, String date, String total) throws Exception {
        final ReceiptResult receiptResult = ReceiptResult.testObjectBuilder()
                                                         .receipt(receipt)
                                                         .chainCode(chainCode)
                                                         .branchName(branchName)
                                                         .date(date)
                                                         .total(total)
                                                         .build();
        receiptResultRepository.save(receiptResult);
        return receiptResult;
    }

    protected void addReceiptItem(ReceiptResult receiptResult, String parsedName, String displayName, String parsedPrice, String displayPrice, int lineNumber, String catalogCode) throws Exception {
        final ReceiptItem item = ReceiptItem.testObjectBuilder()
                                            .id("")
                                            .receiptResult(receiptResult)
                                            .parsedName(parsedName)
                                            .displayName(displayName)
                                            .parsedPrice(parsedPrice)
                                            .displayPrice(displayPrice)
                                            .lineNumber(lineNumber)
                                            .catalogCode(catalogCode)
                                            .build();
        receiptItemRepository.save(item);
    }

    protected void addReceiptField(ReceiptResult receiptResult, ReceiptFieldType type, String value, int lineNumber) throws Exception {
        final ReceiptField field = ReceiptField.testObjectBuilder()
                                               .id("")
                                               .receiptResult(receiptResult)
                                               .type(type)
                                               .value(value)
                                               .lineNumber(lineNumber)
                                               .build();
        receiptFieldRepository.save(field);
    }

    protected void addReceiptFeedback(Receipt receipt, int rating, String comment) throws Exception {
        receiptService = new ReceiptService(receiptRepository, receiptFeedbackRepository);
        final ReceiptFeedback feedback = receiptService.addFeedback(receipt, rating, comment);
        receiptFeedbackRepository.save(feedback);
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
