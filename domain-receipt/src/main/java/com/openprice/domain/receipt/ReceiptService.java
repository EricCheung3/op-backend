package com.openprice.domain.receipt;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.file.FileSystemService;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.data.Item;
import com.openprice.parser.simple.SimpleParser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final FileSystemService fileSystemService;
    private final SimpleParser simpleParser;

    @Inject
    public ReceiptService(final ReceiptRepository receiptRepository,
            final ReceiptImageRepository receiptImageRepository,
            final FileSystemService fileSystemService,
            final SimpleParser simpleParser) {
        this.receiptRepository = receiptRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.fileSystemService = fileSystemService;
        this.simpleParser = simpleParser;
    }

    public Receipt uploadImageForNewReceipt(final UserAccount user, final String base64Data) {
        byte[] content = null;
        try {
            content = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException ex) {
            log.error("Not a valid base64 encoded data!", ex);
            throw new RuntimeException("Not a valid image content.");
        }
        final Receipt receipt = receiptRepository.save(Receipt.createReceipt(user));
        saveImage(receipt, content);

        return receiptRepository.save(receipt);
    }

    public Receipt uploadImageForNewReceipt(final UserAccount user, final MultipartFile file) {
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (IOException ex) {
            log.error("No content of receipt image to save!");
            throw new RuntimeException("No image content.");
        }

        final Receipt receipt = receiptRepository.save(Receipt.createReceipt(user));
        saveImage(receipt, content);

        return receiptRepository.save(receipt);
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
        final Path imageFile = getImageFile(image);

        try (final OutputStream out = new BufferedOutputStream(Files.newOutputStream(imageFile, StandardOpenOption.CREATE_NEW)))
        {
            out.write(content);
        } catch (IOException ex) {
            log.error("Cannot save receipt image to image folder at '{}', please check server file system!", imageFile.toString());
            throw new RuntimeException("System Error! Cannot save image.", ex); //TODO design exceptions
        }

        image.setStatus(ProcessStatusType.UPLOADED);
        receipt.getImages().add(image);
        return receiptImageRepository.save(image);
    }

    /**
     * First, check if all receipt images are processed with OCR. If not, wait and check again.
     * If timeout, return empty item list.
     * If OCR finished for all images, get OCR result from database, and call parser to get receipt items.
     *
     * @param receipt
     * @return
     */
    public List<ReceiptItem> getParsedReceiptItems(Receipt receipt) {
        final List<ReceiptItem> result = new ArrayList<>();
        final List<String> ocrTextList = new ArrayList<>();
        boolean ocrReady = true;

        int counter = 0;
        while (counter++ < 20) {
            final List<ReceiptImage> images = receiptImageRepository.findByReceiptOrderByCreatedTime(receipt);
            ocrTextList.clear();
            for (final ReceiptImage image : images) {
                if (StringUtils.hasText(image.getOcrResult())) {
                    ocrTextList.add(image.getOcrResult());
                } else {
                    ocrReady = false;
                    break;
                }
            }
            if (ocrReady) {
                break;
            }
            try {
                Thread.sleep(300);
            } catch (Exception ex) {

            }
        }

        if (ocrReady) {
            log.debug("After checking {} times, get ocr result for receipt.", counter);
            try {
                ParsedReceipt parserResult = simpleParser.parseOCRResults(ocrTextList);
                for (final Item item : parserResult.getItems()) {
                    final ReceiptItem receiptItem = new ReceiptItem();
                    receiptItem.setName(item.getName());
                    receiptItem.setBuyPrice(item.getBuyPrice());
                    receiptItem.setCategory(item.getCategory());
                    receiptItem.setRegPrice(item.getRegPrice());
                    receiptItem.setSaving(item.getSaving());
                    receiptItem.setUnitPrice(item.getUnitPrice());
                    receiptItem.setWeight(item.getWeight());
                    result.add(receiptItem);
                }
                log.debug("SimpleParser returns {} items.", result.size());
            } catch (Exception ex) {
                log.error("SEVERE: Got exception during parsing ocr text.", ex);
            }
        }
        return result;
    }
}
