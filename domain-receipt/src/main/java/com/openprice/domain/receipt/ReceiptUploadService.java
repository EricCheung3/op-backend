package com.openprice.domain.receipt;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openprice.common.ImageResourceUtils;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.file.FileSystemService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceiptUploadService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final FileSystemService fileSystemService;

    @Inject
    public ReceiptUploadService(final ReceiptRepository receiptRepository,
                                final ReceiptImageRepository receiptImageRepository,
                                final FileSystemService fileSystemService) {
        this.receiptRepository = receiptRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.fileSystemService = fileSystemService;
    }

    public ReceiptImage uploadImageForNewReceipt(final UserAccount user, final String base64Data) {
        byte[] content = null;
        try {
            content = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException ex) {
            log.error("Not a valid base64 encoded data!", ex);
            throw new RuntimeException("Not a valid image content.");
        }
        final Receipt receipt = receiptRepository.save(new Receipt(user));
        return saveImage(receipt, content);
    }

    public ReceiptImage uploadImageForNewReceipt(final UserAccount user, final MultipartFile file) {
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (IOException ex) {
            log.error("No content of receipt image to save!");
            throw new RuntimeException("No image content.");
        }

        final Receipt receipt = receiptRepository.save(new Receipt(user));
        return saveImage(receipt, content);
    }

    public ReceiptImage appendImageToReceipt(final Receipt receipt, final String base64Data) {
        byte[] content = null;
        try {
            content = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException ex) {
            log.error("Not a valid base64 encoded data!", ex);
            throw new RuntimeException("Not a valid image content.");
        }

        final ReceiptImage image = saveImage(receipt, content);
        receiptRepository.save(receipt);
        return image;
    }

    public ReceiptImage appendImageToReceipt(final Receipt receipt, final MultipartFile file) {
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (IOException ex) {
            log.error("No content of receipt image to save!");
            throw new RuntimeException("No image content.");
        }

        final ReceiptImage image = saveImage(receipt, content);

        receiptRepository.save(receipt);
        return image;
    }

    public String getImageFilePath(final ReceiptImage image) {
        final Receipt receipt = image.getReceipt();
        final UserAccount user = receipt.getUser();
        return user.getId() + fileSystemService.getPathSeparator() + image.getFileName();
    }

    public Path getImageFile(final ReceiptImage image) {
        final Receipt receipt = image.getReceipt();
        final UserAccount user = receipt.getUser();
        final Path imageFolder = fileSystemService.getReceiptImageSubFolder(user.getId());
        return imageFolder.resolve(image.getFileName());
    }

    private ReceiptImage saveImage(final Receipt receipt, final byte[] content) {
        final ReceiptImage image = receipt.createImage();
        final Path imageFile = fileSystemService.saveReceiptImage(receipt.getUser().getId(), image.getFileName(), content);
        log.debug("Save uploaded receipt image to {}.", imageFile);

        image.setStatus(ProcessStatusType.UPLOADED);
        final byte[] resizedContent = ImageResourceUtils.resizeJpgImage(content);
        image.setBase64(new String(Base64.getEncoder().encode(resizedContent)));

        return receiptImageRepository.save(image);
    }
}
