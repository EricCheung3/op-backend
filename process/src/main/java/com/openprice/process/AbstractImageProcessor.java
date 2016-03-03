package com.openprice.process;

import java.nio.file.Files;
import java.nio.file.Path;

import javax.transaction.Transactional;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountRepository;
import com.openprice.domain.receipt.OcrProcessLog;
import com.openprice.domain.receipt.OcrProcessLogRepository;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptParsingService;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractImageProcessor implements ImageProcessor {

    private final String name;
    private final FileSystemService fileSystemService;
    private final ReceiptParsingService receiptParsingService;
    private final OcrProcessLogRepository ocrProcessLogRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final UserAccountRepository userAccountRepository;

    public AbstractImageProcessor(final String name,
                                  final FileSystemService fileSystemService,
                                  final ReceiptParsingService receiptParsingService,
                                  final OcrProcessLogRepository ocrProcessLogRepository,
                                  final ReceiptImageRepository receiptImageRepository,
                                  final UserAccountRepository userAccountRepository) {
        this.name = name;
        this.fileSystemService = fileSystemService;
        this.receiptParsingService = receiptParsingService;
        this.ocrProcessLogRepository = ocrProcessLogRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void processImage(ProcessItem item) {
        final long start = System.currentTimeMillis();
        final Path imageFile = fileSystemService.getReceiptImageFolder().resolve(getImageFilePath(item));

        if (Files.notExists(imageFile)) {
            log.error("Cannot open image file {}, please check file system.", imageFile.toString());
            throw new RuntimeException("Image file not exist: " + imageFile.toString());
        }
        log.debug("Start processing image {} saved at {} by calling {}...",
                item.getImageId(), imageFile.toString(), name);
        final ImageProcessResult result = getImageProcessResult(imageFile.toString());
        final long duration = System.currentTimeMillis() - start;
        log.info("Finish processing image {} with {}, took {} milli-seconds.",
                item.getImageId(), name, duration);
        saveProcessResult(item, result, start, duration);
    }

    protected abstract ImageProcessResult getImageProcessResult(String filePath);

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    private String getImageFilePath(final ProcessItem item) {
        final ReceiptImage image = receiptImageRepository.findOne(item.getImageId());
        if (image == null) {
            log.warn("Image '{}' was deleted when ocr process started!", item.getImageId());
            return null;
        }
        return item.getOwnerId() + fileSystemService.getPathSeparator() + image.getFileName();
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    private void saveProcessResult(final ProcessItem item,
                                   final ImageProcessResult result,
                                   final long start,
                                   final long duration) {
        final ReceiptImage image = receiptImageRepository.findOne(item.getImageId());
        if (image == null) {
            log.warn("Image '{}' was deleted when ocr process finished!", item.getImageId());
            return;
        }
        final OcrProcessLog processLog = new OcrProcessLog();
        processLog.setImageId(item.getImageId());
        //processLog.setOwnerName(image.getReceipt().getUser().getId()); // TODO set user email as owner name, or "system:"+username if this is admin uploaded image
        //processLog.setRequesterName(item.getRequesterId()); // TODO get email if requester is user; or admin username if requester is admin
        processLog.setServerName(name);
        processLog.setRequestTime(item.getAddTime().getTime());
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
        ocrProcessLogRepository.save(processLog);
        receiptImageRepository.save(image);

        Receipt receipt = image.getReceipt();
        receipt = receiptParsingService.parseScannedReceiptImages(receipt);

        if (true) { // for test purpose to see what happens in Cloud. TODO change to debug
            final UserAccount owner = userAccountRepository.findOne(item.getOwnerId()); // we assume only user can be owner now
            if (owner != null) {
                log.info("Process receipt from user '{}' ...", owner.getProfile().getDisplayName());
            }
            log.info("After OCR process, receipt status is : "+receipt.getStatus());
            log.info("After parsing, receipt status is : "+receipt.getStatus());
        }

    }
}
