package com.openprice.process;

import com.openprice.domain.receipt.OcrProcessLogRepository;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.domain.receipt.ReceiptParsingService;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessResult;
import com.openprice.ocr.client.OcrService;

public class RemoteOCRImageProcessor extends AbstractImageProcessor {

    private final OcrService ocrService;

    public RemoteOCRImageProcessor(final String serverName,
                                   final FileSystemService fileSystemService,
                                   final OcrService ocrService,
                                   final ReceiptParsingService receiptParsingService,
                                   final OcrProcessLogRepository ocrProcessLogRepository,
                                   final ReceiptImageRepository receiptImageRepository) {
        super(serverName, fileSystemService, receiptParsingService, ocrProcessLogRepository, receiptImageRepository);
        this.ocrService = ocrService;
    }

    @Override
    protected ImageProcessResult getImageProcessResult(String imageFilePath) {
        return ocrService.processUserReceiptImage(imageFilePath);
    }

}
