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
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.store.StoreChain;
import com.openprice.domain.store.StoreService;
import com.openprice.rest.ApiDocumentationBase;

public abstract class AdminApiDocumentationBase extends ApiDocumentationBase {
    public static final String ADMINNAME = "jdoe";

    @Inject
    protected AdminAccountService adminAccountService;

    @Inject
    protected AdminAccountRepository adminAccountRepository;

    @Inject
    protected StoreService storeService;

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
        Receipt receipt = Receipt.createReceipt(user);
        receipt = receiptRepository.save(receipt);

        // add two images to the receipt
        final ReceiptImage image1 = receipt.createImage();
        image1.setStatus(ProcessStatusType.UPLOADED);
        receiptImageRepository.save(image1);
        final ReceiptImage image2 = receipt.createImage();
        image2.setStatus(ProcessStatusType.UPLOADED);
        receiptImageRepository.save(image2);

        receipt = Receipt.createReceipt(user);
        receipt = receiptRepository.save(receipt);
        final ReceiptImage image = receipt.createImage();
        image.setStatus(ProcessStatusType.UPLOADED);
        receiptImageRepository.save(image);

    }

    protected void deleteReceipts() throws Exception {
        final UserAccount user = userAccountRepository.findByEmail(USERNAME);
        for (final Receipt receipt : receiptRepository.findByUser(user)) {
            receiptRepository.delete(receipt);
        }
    }

    protected void createStores() throws Exception {
        StoreChain chain = storeService.createStoreChain("RCSS", "Real Canadian Superstore");
        storeService.createStoreBranch(chain, "Calgary Trail RCSS", "780-430-2769", "", "4821, Calgary Trail", "", "Edmonton", "AB", "", "Canada");
        storeService.createStoreBranch(chain, "South Common RCSS", "780-490-3918", "", "1549 9711, 23 AVE NW", "", "Edmonton", "AB", "", "Canada");
        storeService.createCatalog(chain, "MILK", "1234", "4.99", "Homo Milk", "Food,Dairy,milk", "dairy");
        storeService.createCatalog(chain, "EGG", "1235", "2.99", "Free Run Egg", "Food,Egg", "meat");
    }

    protected void deleteStores() throws Exception {
        storeService.deleteAllStores();
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
