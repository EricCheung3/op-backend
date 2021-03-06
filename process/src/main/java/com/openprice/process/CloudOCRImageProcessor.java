package com.openprice.process;

import com.openprice.domain.account.user.UserAccountRepository;
import com.openprice.domain.receipt.OcrProcessLogRepository;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptParsingService;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessResult;
import com.openprice.process.abbyy.CloudOcrService;

/**
 * Use ABBYY Cloud OCR SDK to do receipt image processing.
 * See http://ocrsdk.com/documentation/quick-start/work/
 *
 */
public class CloudOCRImageProcessor extends AbstractImageProcessor {

    private final CloudOcrService cloudOcrService;

    public CloudOCRImageProcessor(final FileSystemService fileSystemService,
                                  final CloudOcrService cloudOcrService,
                                  final ReceiptParsingService receiptParsingService,
                                  final OcrProcessLogRepository ocrProcessLogRepository,
                                  final ReceiptImageRepository receiptImageRepository,
                                  final UserAccountRepository userAccountRepository) {
        super("ABBYY Cloud SDK",
              fileSystemService,
              receiptParsingService,
              ocrProcessLogRepository,
              receiptImageRepository,
              userAccountRepository);
        this.cloudOcrService = cloudOcrService;
    }

    @Override
    protected ImageProcessResult getImageProcessResult(String imageFilePath) {
        return cloudOcrService.getImageResultFromCloud(imageFilePath);
    }

}
