package com.openprice.process;

import javax.transaction.Transactional;

import com.openprice.domain.receipt.ProcessLog;
import com.openprice.domain.receipt.ProcessLogRepository;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessResult;
import com.openprice.ocr.client.OcrService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoteOCRImageProcessor implements ImageProcessor {

    private final OcrService ocrService;
    private final FileSystemService fileSystemService;
    private final String serverName;
    //private final String serverUrl;
    private final ProcessLogRepository processLogRepository;
    private final ReceiptImageRepository receiptImageRepository;

    public RemoteOCRImageProcessor(final String serverName,
                                   final OcrService ocrService,
                                   final FileSystemService fileSystemService,
                                   final ProcessLogRepository processLogRepository,
                                   final ReceiptImageRepository receiptImageRepository) {
        this.serverName = serverName;
        this.ocrService = ocrService;
        this.fileSystemService = fileSystemService;
        this.processLogRepository = processLogRepository;
        this.receiptImageRepository = receiptImageRepository;
    }

    @Override
    public String getName() {
        return serverName;
    }

    @Override
    public void processImage(final ProcessItem item) {
        final long start = System.currentTimeMillis();
        final ReceiptImage image = receiptImageRepository.findOne(item.getImageId());
        final String imageFilePath = item.getOwnerId() + fileSystemService.getPathSeparator() + image.getFileName();
        log.debug("Start process image {} by calling OCR API at '{}'", imageFilePath, ocrService.getUrl());
        final ImageProcessResult result = ocrService.processUserReceiptImage(imageFilePath);
        final long duration = System.currentTimeMillis() - start;
        log.info("Finish process image {} with server '{}', took {} milli-seconds.", imageFilePath, serverName, duration);
        saveProcessResult(item, result, start, duration);
    }

    @Transactional
    private void saveProcessResult(final ProcessItem item, final ImageProcessResult result, final long start, final long duration) {
        final ReceiptImage image = receiptImageRepository.findOne(item.getImageId());
        final ProcessLog processLog = new ProcessLog();
        processLog.setImageId(item.getImageId());
        //processLog.setOwnerName(image.getReceipt().getUser().getId()); // TODO set user email as owner name, or "system" if this is admin uploaded image
        //processLog.setRequesterName(item.getRequesterId()); // TODO get email if requester is user; or admin username if requester is admin
        processLog.setServerName(serverName);
        processLog.setStartTime(start);
        processLog.setOcrDuration(duration);

        if (result.isSuccess()) {
            log.debug("Got OCR result as\n" + result.getOcrResult());
            processLog.setOcrResult(result.getOcrResult());
            image.setStatus(ProcessStatusType.SCANNED);
            image.setOcrResult(result.getOcrResult());
        } else {
            log.warn("OCR process error: " + result.getErrorMessage());
            processLog.setErrorMessage(result.getErrorMessage());
            image.setStatus(ProcessStatusType.SCANNED_ERR);
        }
        processLogRepository.save(processLog);
        receiptImageRepository.save(image);
    }
}
