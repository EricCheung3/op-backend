package com.openprice.domain.receipt;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.data.Item;
import com.openprice.parser.simple.SimpleParser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final ReceiptResultRepository receiptResultRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final ReceiptFeedbackRepository receiptFeedbackRepository;
    private final SimpleParser simpleParser;

    @Inject
    public ReceiptService(final ReceiptRepository receiptRepository,
                          final ReceiptImageRepository receiptImageRepository,
                          final ReceiptResultRepository receiptResultRepository,
                          final ReceiptItemRepository receiptItemRepository,
                          final ReceiptFeedbackRepository receiptFeedbackRepository,
                          final SimpleParser simpleParser) {
        this.receiptRepository = receiptRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.receiptResultRepository = receiptResultRepository;
        this.receiptItemRepository = receiptItemRepository;
        this.receiptFeedbackRepository = receiptFeedbackRepository;
        this.simpleParser = simpleParser;
    }

    /**
     * Return latest parser result of ReceiptResult object for the receipt in the database.
     * If no parser result yet, try to query ocr result for all receipt images and call Simple Parser.
     *
     * @param receipt
     * @return null if no parser result after waiting 1 mins
     */
    public ReceiptResult getLatestReceiptResult(final Receipt receipt) {
        final ReceiptResult result = receiptResultRepository.findFirstByReceiptOrderByCreatedTimeDesc(receipt);

        if (result != null) {
            return result;
        }

        log.debug("No receipt result yet for receipt {}, call parser to generate...", receipt.getId());
        final List<String> ocrTextList = loadOcrResults(receipt);
        if (ocrTextList.size() == 0) {
            log.debug("Cannot load OCR result for receipt {}.", receipt.getId());
            return null;
        }

        return parseOcrResults(receipt, ocrTextList);
    }

    public ReceiptResult parseOcrResults(final Receipt receipt, final List<String> ocrTextList) {
        try {
            final ParsedReceipt parsedReceipt = simpleParser.parseOCRResults(ocrTextList);
            if (parsedReceipt != null) {
                ReceiptResult result = receipt.createReceiptResultFromParserResult(parsedReceipt);
                result = receiptResultRepository.save(result); // has to save ReceiptResult first before saving ReceiptItem

                int lineNumber = 1;
                for (final Item item : parsedReceipt.getItems()) {
                    final ReceiptItem receiptItem = result.addItem(item.getCatalogCode(), item.getName(), item.getBuyPrice());
                    // FIXME add lineNumber from parser items
                    receiptItem.setLineNumber(lineNumber++);
                    receiptItemRepository.save(receiptItem);
                }
                log.debug("SimpleParser returns {} items.", parsedReceipt.getItems().size());
                return receiptResultRepository.save(result);
            } else {
                return null;
            }
        } catch (Exception ex) {
            log.error("SEVERE: Got exception during parsing ocr text.", ex);
            return null;
        }

    }

    public List<String> loadOcrResults(final Receipt receipt) {
        final List<String> ocrTextList = new ArrayList<>();
        boolean ocrReady = true;

        int counter = 0;
        while (counter++ < 60) {
            ocrTextList.clear();
            ocrReady = true;

            final List<ReceiptImage> images = receiptImageRepository.findByReceiptOrderByCreatedTime(receipt);
            if (images.size() == 0) {
                log.error("No images in database for receipt {}.", receipt.getId());
                return ocrTextList;
            }

            for (final ReceiptImage image : images) {
                if (StringUtils.hasText(image.getOcrResult())) {
                    ocrTextList.add(image.getOcrResult());
                } else {
                    ocrReady = false;
                    break;
                }
            }

            if (ocrReady) {
                log.info("After checking {} times, get ocr result for receipt.", counter);
                return ocrTextList;
            }

            try {
                Thread.sleep(1000);
            } catch (Exception ex) {

            }
        }
        log.warn("After checking one minute, no ocr result for receipt.");
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

        feedback = receiptFeedbackRepository.save(feedback);

        receipt.setNeedFeedback(false);
        receiptRepository.save(receipt);

        return feedback;
    }

}
