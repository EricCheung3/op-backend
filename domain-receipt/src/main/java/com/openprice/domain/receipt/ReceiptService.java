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
    private final ReceiptDataRepository receiptDataRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final ReceiptFeedbackRepository receiptFeedbackRepository;
    private final SimpleParser simpleParser;

    @Inject
    public ReceiptService(final ReceiptRepository receiptRepository,
                          final ReceiptImageRepository receiptImageRepository,
                          final ReceiptDataRepository receiptDataRepository,
                          final ReceiptItemRepository receiptItemRepository,
                          final ReceiptFeedbackRepository receiptFeedbackRepository,
                          final SimpleParser simpleParser) {
        this.receiptRepository = receiptRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.receiptDataRepository = receiptDataRepository;
        this.receiptItemRepository = receiptItemRepository;
        this.receiptFeedbackRepository = receiptFeedbackRepository;
        this.simpleParser = simpleParser;
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
            final List<String> ocrTextList = loadOcrResults(receipt);
            return parseOcrResults(receipt, ocrTextList);
        }
        return result;
    }

    public ReceiptData parseOcrResults(final Receipt receipt, final List<String> ocrTextList) {
        try {
            final ParsedReceipt parsedReceipt = simpleParser.parseOCRResults(ocrTextList);
            ReceiptData data = receipt.createReceiptDataFromParserResult(parsedReceipt);
            data = receiptDataRepository.save(data); // has to save ReceiptData first before saving ReceiptItem

            for (final Item item : parsedReceipt.getItems()) {
                final ReceiptItem receiptItem = data.addItem(item.getCatalogCode(), item.getName(), item.getBuyPrice());
                receiptItemRepository.save(receiptItem);
            }
            log.debug("SimpleParser returns {} items.", data.getItems().size());
            return receiptDataRepository.save(data);
        } catch (Exception ex) {
            log.error("SEVERE: Got exception during parsing ocr text.", ex);
        }

        return null;
    }

    public List<String> loadOcrResults(final Receipt receipt) {
        final List<String> ocrTextList = new ArrayList<>();
        boolean ocrReady = true;

        int counter = 0;
        while (counter++ < 40) {
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

        receipt.setNeedFeedback(false);
        receiptRepository.save(receipt);

        return feedback;
    }

}
