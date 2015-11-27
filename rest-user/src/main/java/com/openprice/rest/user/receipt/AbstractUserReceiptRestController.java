package com.openprice.rest.user.receipt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptRepository;
import com.openprice.domain.receipt.ReceiptService;
import com.openprice.internal.client.InternalService;
import com.openprice.rest.ResourceNotFoundException;
import com.openprice.rest.user.AbstractUserRestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractUserReceiptRestController extends AbstractUserRestController {

    protected final ReceiptService receiptService;
    protected final ReceiptRepository receiptRepository;
    protected final InternalService internalService;

    public AbstractUserReceiptRestController(final UserAccountService userAccountService,
                                             final ReceiptService receiptService,
                                             final ReceiptRepository receiptRepository,
                                             final InternalService internalService) {
        super(userAccountService);
        this.receiptService = receiptService;
        this.receiptRepository = receiptRepository;
        this.internalService = internalService;
    }

    protected Receipt getReceiptByIdAndCheckUser(final String receiptId)
            throws ResourceNotFoundException, AccessDeniedException {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final Receipt receipt = receiptRepository.findOne(receiptId);
        if (receipt == null) {
            log.warn("ILLEGAL RECEIPT ACCESS! No such receipt Id: {}.", receiptId);
            throw new ResourceNotFoundException("No receipt with the id: " + receiptId);
        }
        if (!currentUser.equals(receipt.getUser())) {
            log.warn("ILLEGAL RECEIPT ACCESS! Receipt '{}' not belong to current user '{}'.", receiptId, currentUser.getId());
            throw new AccessDeniedException("Cannot access the receipt not belong to current user.");
        }
        return receipt;
    }

    @Transactional
    protected Receipt newReceiptWithBase64ImageData(final String base64Data) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        log.debug("User {} upload image as base64 string for new receipt", currentUser.getUsername());
        return receiptService.uploadImageForNewReceipt(currentUser, base64Data);
    }

    @Transactional
    protected Receipt newReceiptWithFile(final MultipartFile file) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        log.debug("User {} upload image file for new receipt", currentUser.getUsername());
        return receiptService.uploadImageForNewReceipt(currentUser, file);
    }

    @Transactional
    protected ReceiptImage newReceiptImageWithBase64ImageData(final String receiptId, final String base64Data) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        log.debug("User {} upload image base64 string for receipt {}.", currentUser.getUsername(), receiptId);
        return receiptService.appendImageToReceipt(receipt, base64Data);
    }

    @Transactional
    protected ReceiptImage newReceiptImageWithFile(final String receiptId, final MultipartFile file) {
        final UserAccount currentUser = getCurrentAuthenticatedUser();
        final Receipt receipt = getReceiptByIdAndCheckUser(receiptId);
        log.debug("User {} upload image file for receipt {}.", currentUser.getUsername(), receiptId);
        return receiptService.appendImageToReceipt(receipt, file);
    }

    protected void addReceiptImageToProcessQueue(final ReceiptImage image) {
        final String userId = getCurrentAuthenticatedUser().getId();
        internalService.addImageToProcessQueue(image.getId(), userId, userId);

    }

}
