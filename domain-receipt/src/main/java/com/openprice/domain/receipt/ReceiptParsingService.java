package com.openprice.domain.receipt;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.openprice.parser.ParsedField;
import com.openprice.parser.ParsedItem;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.ReceiptParser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReceiptParsingService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final ReceiptResultRepository receiptResultRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final ReceiptFieldRepository receiptFieldRepository;
    private final ReceiptParser receiptParser;

    @Inject
    public ReceiptParsingService(final ReceiptRepository receiptRepository,
                                 final ReceiptImageRepository receiptImageRepository,
                                 final ReceiptResultRepository receiptResultRepository,
                                 final ReceiptItemRepository receiptItemRepository,
                                 final ReceiptFieldRepository receiptFieldRepository,
                                 final ReceiptParser receiptParser) {
        this.receiptRepository = receiptRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.receiptResultRepository = receiptResultRepository;
        this.receiptItemRepository = receiptItemRepository;
        this.receiptFieldRepository = receiptFieldRepository;
        this.receiptParser = receiptParser;
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

        if (result != null) {
            receipt.setStatus(ReceiptStatusType.HAS_RESULT);
            final LocalDate receiptDate = getReceiptDate(result.getParsedDate());
            if (receiptDate != null) {
                receipt.setReceiptDate(receiptDate);
            }
        } else{
            receipt.setStatus(ReceiptStatusType.PARSER_ERROR);
        }
        return receiptRepository.save(receipt);
    }

    public ReceiptResult parseOcrAndSaveResults(final Receipt receipt, final List<String> ocrTextList) {
        try {
            final ParsedReceipt parsedReceipt = receiptParser.parseReceiptOcrResult(ocrTextList);
            if (parsedReceipt != null) {
                logParsedResult(parsedReceipt); // TODO remove it after we have admin UI to display

                ReceiptResult result = receipt.createReceiptResultFromParserResult(parsedReceipt);
                result = receiptResultRepository.save(result); // has to save ReceiptResult first before saving ReceiptItem
                for (final ParsedItem item : parsedReceipt.getItems()) {
                    final ReceiptItem receiptItem = result.addItem(item.getCatalogCode(),
                                                                   item.getParsedName(),
                                                                   item.getParsedBuyPrice(),
                                                                   item.getLineNumber());
                    receiptItemRepository.save(receiptItem);
                }
                log.debug("ReceiptParser returns {} items.", parsedReceipt.getItems().size());

                for (final ReceiptFieldType fieldType : parsedReceipt.getFields().keySet()) {
                    final ParsedField field = parsedReceipt.getFields().get(fieldType);
                    final ReceiptField receiptField = result.addField(field.getFieldType(),
                                                                      field.getFieldValue(),
                                                                      field.getLineNumber());
                    receiptFieldRepository.save(receiptField);
                }
                return receiptResultRepository.save(result);
            } else {
                return null;
            }
        } catch (Exception ex) {
            log.error("SEVERE: Got exception during parsing ocr text.", ex);
            return null;
        }
    }

    private void logParsedResult(final ParsedReceipt parsedReceipt) {
        if (parsedReceipt.getChainCode() != null) {
            log.info("    recognized store chain code - '{}'", parsedReceipt.getChainCode());
        }
        if (parsedReceipt.getBranchName() != null) {
            log.info("    recognized branch name - '{}'", parsedReceipt.getBranchName());
        }
        if (parsedReceipt.getFields().get(ReceiptFieldType.Date) != null) {
            log.info("    parsed Date - '{}'", parsedReceipt.getFields().get(ReceiptFieldType.Date).getFieldValue());
        } else {
            log.info("    no Date found!");
        }
        if (parsedReceipt.getFields().get(ReceiptFieldType.Total) != null) {
            log.info("    parsed Total - '{}'", parsedReceipt.getFields().get(ReceiptFieldType.Total).getFieldValue());
        } else {
            log.info("    no Total found!");
        }
        if (parsedReceipt.getFields().get(ReceiptFieldType.SubTotal) != null) {
            log.info("    parsed SubTotal - '{}'", parsedReceipt.getFields().get(ReceiptFieldType.SubTotal).getFieldValue());
        } else {
            log.info("    no SubTotal found!");
        }
    }

    LocalDate getReceiptDate(final String parsedDate) {
        if (!StringUtils.isEmpty(parsedDate)) {
            String[] splitDateStrings = parsedDate.split("/");
            if (splitDateStrings.length == 3) {
                try {
                    return LocalDate.of(Integer.parseInt(splitDateStrings[0]),
                                        Month.of(Integer.parseInt(splitDateStrings[1])),
                                        Integer.parseInt(splitDateStrings[2]));
                } catch (Exception ex) {
                    log.error("Cannot set receipt date from parsed date from simple parser: '{}'", parsedDate);
                }
            }
        }
        return null;
    }

}
