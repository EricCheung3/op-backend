package com.openprice.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.io.CharStreams;
import com.openprice.domain.receipt.OcrProcessLogRepository;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessResult;

import lombok.extern.slf4j.Slf4j;

/**
 * This ImageProcessor will only return static OCR result for test purpose. The result is loaded from file
 * ocrResult.txt in classpath.
 */
@Slf4j
public class StaticResultImageProcessor extends AbstractImageProcessor {

    private static final String OCR_RESULT_FILE_PATH = "ocrResult.txt";

    private final String staticResult;

    private final int waitSeconds;

    public StaticResultImageProcessor(final FileSystemService fileSystemService,
                                      final OcrProcessLogRepository ocrProcessLogRepository,
                                      final ReceiptImageRepository receiptImageRepository,
                                      final int waitSeconds) {
        super("Static", fileSystemService, ocrProcessLogRepository, receiptImageRepository);
        this.waitSeconds = waitSeconds;

        try {
            Resource resource = new ClassPathResource(OCR_RESULT_FILE_PATH);
            InputStream resourceInputStream = resource.getInputStream();
            this.staticResult = CharStreams.toString(new InputStreamReader(resourceInputStream, "UTF-8"));
        } catch (IOException ex) {
            log.error("Cannot load static result from file {}, please check the file exits.", OCR_RESULT_FILE_PATH);
            throw new RuntimeException("Error loading static result file " + OCR_RESULT_FILE_PATH, ex);
        }
    }

    @Override
    protected ImageProcessResult getImageProcessResult(String imageFilePath) {

        if (waitSeconds > 0) {
            try {
                Thread.sleep(1000 * waitSeconds);
            } catch (Exception ex) {}
        }

        return new ImageProcessResult(true, staticResult, null);
    }


}
