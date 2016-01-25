package com.openprice.process;

import java.nio.file.Files;
import java.nio.file.Path;

import com.openprice.domain.receipt.OcrProcessLogRepository;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessResult;
import com.openprice.process.abbyy.CloudOcrService;

import lombok.extern.slf4j.Slf4j;

/**
 * Use ABBYY Cloud OCR SDK to do receipt image processing.
 * See http://ocrsdk.com/documentation/quick-start/work/
 *
 */
@Slf4j
public class CloudOCRImageProcessor extends AbstractImageProcessor {

    private final CloudOcrService cloudOcrService;

    public CloudOCRImageProcessor(final FileSystemService fileSystemService,
                                  final CloudOcrService cloudOcrService,
                                  final OcrProcessLogRepository ocrProcessLogRepository,
                                  final ReceiptImageRepository receiptImageRepository) {
        super("ABBYY Cloud SDK", fileSystemService, ocrProcessLogRepository, receiptImageRepository);
        this.cloudOcrService = cloudOcrService;
    }

    @Override
    public void processImage(ProcessItem item) {
        final long start = System.currentTimeMillis();
        final Path imageFile = fileSystemService.getReceiptImageFolder().resolve(getImageFilePath(item));

        if (Files.notExists(imageFile)) {
            log.error("Cannot open image file {}, please check file system.", imageFile.toString());
            throw new RuntimeException("Image file not exist: " + imageFile.toString());
        }
        log.debug("Start processing image {} saved at {} by calling Cloud OCR SDK...",
                item.getImageId(), imageFile.toString());
        final ImageProcessResult result = cloudOcrService.getImageResultFromCloud(imageFile.toString());
        final long duration = System.currentTimeMillis() - start;
        log.info("Finish processing image {} with cloud SDK, took {} milli-seconds.",
                item.getImageId(), duration);
        saveProcessResult(item, result, start, duration);
    }

}
