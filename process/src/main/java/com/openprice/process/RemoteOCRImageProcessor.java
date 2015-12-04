package com.openprice.process;

import com.openprice.domain.receipt.OcrProcessLogRepository;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessResult;
import com.openprice.ocr.client.OcrService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoteOCRImageProcessor extends AbstractImageProcessor {

    private final OcrService ocrService;


    public RemoteOCRImageProcessor(final String serverName,
                                   final OcrService ocrService,
                                   final FileSystemService fileSystemService,
                                   final OcrProcessLogRepository ocrProcessLogRepository,
                                   final ReceiptImageRepository receiptImageRepository) {
        super(serverName, fileSystemService, ocrProcessLogRepository, receiptImageRepository);
        this.ocrService = ocrService;
    }

    @Override
    public void processImage(final ProcessItem item) {
        final long start = System.currentTimeMillis();
        final String imageFilePath = getImageFilePath(item);
        if (imageFilePath == null) {
            return;
        }
        log.debug("Start processing image {} saved at {} by calling OCR API at '{}'",
                item.getImageId(), imageFilePath, ocrService.getOcrServiceUrl());
        final ImageProcessResult result = ocrService.processUserReceiptImage(imageFilePath);
        final long duration = System.currentTimeMillis() - start;
        log.info("Finish processing image {} with server '{}', took {} milli-seconds.",
                item.getImageId(), getName(), duration);
        saveProcessResult(item, result, start, duration);
    }

}
