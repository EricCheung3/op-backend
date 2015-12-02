package com.openprice.rest.admin.receipt;

import com.openprice.domain.account.admin.AdminAccountService;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.domain.receipt.ReceiptUploadService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.admin.AbstractAdminRestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractReceiptAdminRestController extends AbstractAdminRestController {

    protected final ReceiptService receiptService;
    protected final ReceiptUploadService receiptUploadService;
    protected final ReceiptRepository receiptRepository;

    public AbstractReceiptAdminRestController(final AdminAccountService adminAccountService,
                                              final ReceiptService receiptService,
                                              final ReceiptUploadService receiptUploadService,
                                              final ReceiptRepository receiptRepository) {
        super(adminAccountService);
        this.receiptService = receiptService;
        this.receiptUploadService = receiptUploadService;
        this.receiptRepository = receiptRepository;
    }


    protected Receipt loadReceiptById(final String receiptId) {
        final Receipt receipt = receiptRepository.findOne(receiptId);
        if (receipt == null) {
            log.warn("ILLEGAL RECEIPT ACCESS! No such receipt Id: {}.", receiptId);
            throw new ResourceNotFoundException("No receipt with the id: " + receiptId);
        }
        return receipt;
    }



}
