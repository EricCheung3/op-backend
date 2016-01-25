package com.openprice.process;

import javax.transaction.Transactional;

import com.openprice.domain.receipt.OcrProcessLog;
import com.openprice.domain.receipt.OcrProcessLogRepository;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractImageProcessor implements ImageProcessor {

    private final String name;
    protected final FileSystemService fileSystemService;
    private final OcrProcessLogRepository ocrProcessLogRepository;
    private final ReceiptImageRepository receiptImageRepository;

    public AbstractImageProcessor(final String name,
                                  final FileSystemService fileSystemService,
                                  final OcrProcessLogRepository ocrProcessLogRepository,
                                  final ReceiptImageRepository receiptImageRepository) {
        this.name = name;
        this.fileSystemService = fileSystemService;
        this.ocrProcessLogRepository = ocrProcessLogRepository;
        this.receiptImageRepository = receiptImageRepository;
    }

    @Override
    public String getName() {
        return name;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    protected String getImageFilePath(final ProcessItem item) {
        final ReceiptImage image = receiptImageRepository.findOne(item.getImageId());
        if (image == null) {
            log.warn("Image '{}' was deleted when ocr process started!", item.getImageId());
            return null;
        }
        return item.getOwnerId() + fileSystemService.getPathSeparator() + image.getFileName();
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    protected void saveProcessResult(final ProcessItem item, final ImageProcessResult result, final long start, final long duration) {
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
    }

}
