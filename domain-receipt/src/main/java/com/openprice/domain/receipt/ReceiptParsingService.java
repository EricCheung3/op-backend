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
public class ReceiptParsingService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final ReceiptResultRepository receiptResultRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final SimpleParser simpleParser;

    @Inject
    public ReceiptParsingService(final ReceiptRepository receiptRepository,
                                 final ReceiptImageRepository receiptImageRepository,
                                 final ReceiptResultRepository receiptResultRepository,
                                 final ReceiptItemRepository receiptItemRepository,
                                 final SimpleParser simpleParser) {
        this.receiptRepository = receiptRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.receiptResultRepository = receiptResultRepository;
        this.receiptItemRepository = receiptItemRepository;
        this.simpleParser = simpleParser;
    }

    /**
     * Whenever we have receipt image processed (scanned by OCR engine), we call this function to parse all scanned
     * images with successful OCR result.
     *
     * @param receipt
     * @return
     */
    public Receipt parseScannedReceiptImages(final Receipt receipt) {
        final Long unscannedImageCount =
                receiptImageRepository.countByReceiptAndStatus(receipt, ProcessStatusType.UPLOADED);
        if (unscannedImageCount > 0) {
            receipt.setStatus(ReceiptStatusType.WAIT_FOR_RESULT);
            return receiptRepository.save(receipt);
        }

        final List<ReceiptImage> scannedImages =
                receiptImageRepository.findByReceiptAndStatusOrderByCreatedTime(receipt,
                                                                                ProcessStatusType.SCANNED);
        if (scannedImages.size() == 0) {
            if (receiptImageRepository.countByReceiptAndStatus(receipt, ProcessStatusType.SCANNED_ERR) > 0) {
                receipt.setStatus(ReceiptStatusType.OCR_ERROR);
                return receiptRepository.save(receipt);
            }
            log.error("SEVERE: no UPLOADED/SCANNED/SCANNED_ERR images for receipt {}. Must be something wrong!", receipt.getId());
            return receipt;
        }

        final List<String> ocrTextList = new ArrayList<>();
        for (final ReceiptImage image : scannedImages) {
            if (StringUtils.hasText(image.getOcrResult())) {
                ocrTextList.add(image.getOcrResult());
            }
        }

        ReceiptResult result = parseOcrAndSaveResults(receipt, ocrTextList);
        receipt.setStatus( (result == null) ? ReceiptStatusType.PARSER_ERROR : ReceiptStatusType.HAS_RESULT);
        return receiptRepository.save(receipt);
    }

    public ReceiptResult parseOcrAndSaveResults(final Receipt receipt, final List<String> ocrTextList) {
        try {
            final ParsedReceipt parsedReceipt = simpleParser.parseOCRResults(ocrTextList);
            if (parsedReceipt != null) {
                ReceiptResult result = receipt.createReceiptResultFromParserResult(parsedReceipt);
                result = receiptResultRepository.save(result); // has to save ReceiptResult first before saving ReceiptItem

                int lineNumber = 1;
                for (final Item item : parsedReceipt.getItems()) {
                    final ReceiptItem receiptItem = result.addItem(item.getProduct().toCatalogCode(), item.getProduct().getName(), item.getBuyPrice());
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

}
