package com.openprice.process;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.transaction.Transactional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.io.CharStreams;
import com.openprice.domain.receipt.ProcessLog;
import com.openprice.domain.receipt.ProcessLogRepository;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StaticResultImageProcessor implements ImageProcessor {
    static final String OCR_RESULT_FILE_PATH = "ocrResult.txt";

    private final ProcessLogRepository processLogRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final String staticResult;

    public StaticResultImageProcessor(final ProcessLogRepository processLogRepository,
                                      final ReceiptImageRepository receiptImageRepository) {
        this.processLogRepository = processLogRepository;
        this.receiptImageRepository = receiptImageRepository;

        try {
            Resource resource = new ClassPathResource(OCR_RESULT_FILE_PATH);
            InputStream resourceInputStream = resource.getInputStream();
            this.staticResult = CharStreams.toString(new InputStreamReader(resourceInputStream, "UTF-8"));
        } catch (IOException ex) {
            log.error("Cannot load static result from file {}, please check the file exits.", OCR_RESULT_FILE_PATH);
            throw new RuntimeException("Error loading static result file "+OCR_RESULT_FILE_PATH, ex);
        }
    }


    @Override
    public String getName() {
        return "StaticResult";
    }

    @Override
    public void processImage(final ProcessItem item) {
        final ProcessLog processLog = new ProcessLog();
        processLog.setImageId(item.getImage().getId());
        processLog.setUsername(item.getUsername());
        processLog.setServerName("static");

        processLog.setStartTime(System.currentTimeMillis());
        processLog.setOcrResult(staticResult);
        processLog.setOcrDuration(0l);

        ReceiptImage image = receiptImageRepository.findOne(item.getImage().getId());
        image.setStatus(ProcessStatusType.SCANNED);
        image.setOcrResult(staticResult);
        saveProcessResult(processLog, image);
    }

    @Transactional
    private void saveProcessResult(final ProcessLog processLog, final ReceiptImage image) {
        processLogRepository.save(processLog);
        receiptImageRepository.save(image);
    }

}
