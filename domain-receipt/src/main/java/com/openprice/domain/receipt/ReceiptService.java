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
import com.openprice.parser.data.ReceiptField;
import com.openprice.parser.data.ValueLine;
import com.openprice.parser.simple.SimpleParser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final ReceiptDataRepository receiptDataRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final ReceiptFeedbackRepository receiptFeedbackRepository;
    private final FileSystemService fileSystemService;
    private final SimpleParser simpleParser;

    @Inject
    public ReceiptService(final ReceiptRepository receiptRepository,
                          final ReceiptImageRepository receiptImageRepository,
                          final ReceiptDataRepository receiptDataRepository,
                          final ReceiptItemRepository receiptItemRepository,
                          final ReceiptFeedbackRepository receiptFeedbackRepository,
                          final FileSystemService fileSystemService,
                          final SimpleParser simpleParser) {
        this.receiptRepository = receiptRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.receiptDataRepository = receiptDataRepository;
        this.receiptItemRepository = receiptItemRepository;
        this.receiptFeedbackRepository = receiptFeedbackRepository;
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
     * Return latest parser result of ReceiptData object for the receipt in the database.
     * If no parser result yet, try to query ocr result for all receipt images and call Simple Parser.
     *
     * @param receipt
     * @return
     */
    public ReceiptData getLatestReceiptParserResult(final Receipt receipt) {
        final ReceiptData result = receiptDataRepository.findFirstByReceiptOrderByCreatedTimeDesc(receipt);

        if (result == null) {
            log.debug("No receipt data yet for receipt {}, call parser to generate...", receipt.getId());
            return generateParsedReceiptData(receipt);
        }
        return result;
    }

    /*
     * Call parser to generate ReceiptData from receipt image OCR result if new uploaded receipt was not parsed yet.
     */
    private ReceiptData generateParsedReceiptData(final Receipt receipt) {
        ReceiptData data = new ReceiptData();
        final List<String> ocrTextList = loadOcrResults(receipt);
        try {
            ParsedReceipt parsedReceipt = simpleParser.parseOCRResults(ocrTextList);
            // simple parser can parse the receipt, try to set result properties
            data.setReceipt(receipt);
            if (parsedReceipt.getChain() != null) {
                data.setChainCode(parsedReceipt.getChain().getCode());
            }
            if (parsedReceipt.getBranch() != null) {
                data.setBranchName(parsedReceipt.getBranch().getBranchName());
            }
            final ValueLine parsedTotalValue = parsedReceipt.getFieldToValueMap().get(ReceiptField.Total);
            if (parsedTotalValue != null) {
                data.setParsedTotal(parsedTotalValue.getValue());
            }
            final ValueLine parsedDateValue = parsedReceipt.getFieldToValueMap().get(ReceiptField.Date);
            if (parsedDateValue != null) {
                data.setParsedDate(parsedDateValue.getValue());
            }
            data = receiptDataRepository.save(data);

            for (final Item item : parsedReceipt.getItems()) {
                final ReceiptItem receiptItem = new ReceiptItem();
                receiptItem.setParsedName(item.getName());
                receiptItem.setParsedPrice(item.getBuyPrice());
                receiptItem.setDisplayName(item.getName());
                receiptItem.setDisplayPrice(item.getBuyPrice());
                receiptItem.setReceiptData(data);
                receiptItemRepository.save(receiptItem);
                data.getItems().add(receiptItem);
            }
            log.debug("SimpleParser returns {} items.", data.getItems().size());

            // save result to database, should save items as well.
            return receiptDataRepository.save(data);
        } catch (Exception ex) {
            log.error("SEVERE: Got exception during parsing ocr text.", ex);
        }

        return null;
    }

    private List<String> loadOcrResults(final Receipt receipt) {
        final List<String> ocrTextList = new ArrayList<>();
        boolean ocrReady = true;

        int counter = 0;
        while (counter++ < 10) {
            final List<ReceiptImage> images = receiptImageRepository.findByReceiptOrderByCreatedTime(receipt);
            ocrTextList.clear();
            ocrReady = true;
            for (final ReceiptImage image : images) {
                if (StringUtils.hasText(image.getOcrResult())) {
                    ocrTextList.add(image.getOcrResult());
                } else {
                    ocrReady = false;
                    break;
                }
            }
            if (ocrReady) {
                log.debug("After checking {} times, get ocr result for receipt.", counter);
                break;
            }
            try {
                Thread.sleep(500);
            } catch (Exception ex) {

            }
        }
        return ocrTextList;
    }

    /**
     * Add feedback from user for receipt, and set receipt <code>needFeedback</code> to false.
     * @param receipt
     * @param rating
     * @param comment
     * @return
     */
    public ReceiptFeedback addFeedback(final Receipt receipt, final Integer rating, final String comment) {
        ReceiptFeedback feedback = new ReceiptFeedback();
        feedback.setReceipt(receipt);
        feedback.setRating(rating);
        feedback.setComment(comment);
        receiptFeedbackRepository.save(feedback);

        receipt.getFeedbacks().add(feedback);
        receipt.setNeedFeedback(false);
        receiptRepository.save(receipt);

        return feedback;
    }
}
