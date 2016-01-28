package com.openprice.process;

import com.openprice.domain.receipt.OcrProcessLogRepository;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessResult;
import com.openprice.ocr.client.OcrService;

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
    protected ImageProcessResult getImageProcessResult(String imageFilePath) {
        return ocrService.processUserReceiptImage(imageFilePath);
    }

}
