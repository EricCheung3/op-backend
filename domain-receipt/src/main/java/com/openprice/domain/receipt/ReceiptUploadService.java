package com.openprice.domain.receipt;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.file.FileSystemService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceiptUploadService {

    private final ReceiptParsingService receiptParsingService;
    private final ReceiptRepository receiptRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final FileSystemService fileSystemService;

    @Inject
    public ReceiptUploadService(final ReceiptParsingService receiptParsingService,
                                final ReceiptRepository receiptRepository,
                                final ReceiptImageRepository receiptImageRepository,
                                final FileSystemService fileSystemService) {
        this.receiptParsingService = receiptParsingService;
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
        image.setStatus(ProcessStatusType.UPLOADED);
        log.debug("Save uploaded receipt image to {}.", imageFile);
        return receiptImageRepository.save(image);
    }

    /**
     * Hack solution to upload receipt image with OCR result.
     *
     * @param user
     * @param image
     * @param ocr
     * @return
     */
    public ReceiptImage hackloadImageFileAndOcrResultForNewReceipt(final UserAccount user,
                                                                   final MultipartFile image,
                                                                   final MultipartFile ocr) {
        byte[] content = null;
        try {
            content = image.getBytes();
        } catch (IOException ex) {
            log.error("No content of receipt image to save!");
            throw new RuntimeException("No image content.");
        }

        final Receipt receipt = receiptRepository.save(new Receipt(user));
        final ReceiptImage receiptImage = saveImage(receipt, content);

        try {
            receiptImage.setOcrResult(new String(ocr.getBytes()));
            receiptImage.setStatus(ProcessStatusType.SCANNED);
            return receiptImageRepository.save(receiptImage);
        } catch (IOException ex) {
            log.error("No content of OCR result to save!");
            throw new RuntimeException("No OCR result content.");
        }
    }

    /**
     * Hack solution to upload OCR result for existing receipt.
     *
     * @param receipt
     * @param ocr
     */
    public void hackloadOcrResult(final Receipt receipt, final MultipartFile ocr) {
        if (receiptImageRepository.countByReceipt(receipt) != 1) {
            log.error("Cannot hack load OCR result to receipt not having just one image!");
            return;
        }
        ReceiptImage receiptImage = receiptImageRepository.findFirstByReceiptOrderByCreatedTime(receipt);
        try {
            final String ocrText = new String(ocr.getBytes());
            receiptImage.setOcrResult(ocrText);
            receiptImage.setStatus(ProcessStatusType.SCANNED);
            receiptImageRepository.save(receiptImage);
            receiptParsingService.parseOcrAndSaveResults(receipt, Arrays.asList(ocrText));
            log.info("Hackloaded OCR result into receipt {} and parsed the result.", receipt.getId());
        } catch (IOException ex) {
            log.error("No content of OCR result to save!");
            throw new RuntimeException("No OCR result content.");
        }

    }
}
