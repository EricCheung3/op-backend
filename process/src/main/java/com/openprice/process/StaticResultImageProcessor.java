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

    static final String OCR_RESULT_FILE_PATH = "ocrResult.txt";

    private final String staticResult;

    public StaticResultImageProcessor(final FileSystemService fileSystemService,
                                      final OcrProcessLogRepository ocrProcessLogRepository,
                                      final ReceiptImageRepository receiptImageRepository) {
        super("Static", fileSystemService, ocrProcessLogRepository, receiptImageRepository);

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
    public void processImage(final ProcessItem item) {
        saveProcessResult(item, new ImageProcessResult(true, staticResult, null), System.currentTimeMillis(), 0l);
    }

}
