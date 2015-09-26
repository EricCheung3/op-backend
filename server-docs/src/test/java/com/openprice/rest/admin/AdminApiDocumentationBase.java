package com.openprice.rest.admin;

import javax.inject.Inject;

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
        final UserAccount user = userAccountRepository.findByEmail(USERNAME);
        for (final Receipt receipt : receiptRepository.findByUser(user)) {
            receiptRepository.delete(receipt);
        }
    }

    protected void createStores() throws Exception {
        StoreChain chain = storeService.createStoreChain("RCSS", "Real Canadian Superstore", "Grocery", "Superstore, RCSS");
        storeService.createStoreBranch(chain, "Calgary Trail RCSS", "780-430-2769", "", "4821, Calgary Trail", "", "Edmonton", "AB", "", "Canada");
        storeService.createStoreBranch(chain, "South Common RCSS", "780-490-3918", "", "1549 9711, 23 AVE NW", "", "Edmonton", "AB", "", "Canada");
    }

    protected void deleteStores() throws Exception {
        storeService.deleteAllStores();
    }
}
