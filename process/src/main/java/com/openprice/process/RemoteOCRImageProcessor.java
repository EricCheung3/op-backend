package com.openprice.process;

import javax.transaction.Transactional;

import org.springframework.web.client.RestTemplate;

import com.openprice.common.api.ImageProcessResult;
import com.openprice.common.api.OcrServerApiUrls;
import com.openprice.domain.receipt.ProcessLog;
import com.openprice.domain.receipt.ProcessLogRepository;
import com.openprice.domain.receipt.ProcessStatusType;
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoteOCRImageProcessor implements ImageProcessor {
    private final RestTemplate restTemplate;
    private final String serverName;
    private final String serverUrl;
    private final ProcessLogRepository processLogRepository;
    private final ReceiptImageRepository receiptImageRepository;

    public RemoteOCRImageProcessor(final String serverName,
                                   final String serverUrl,
                                   final ProcessLogRepository processLogRepository,
                                   final ReceiptImageRepository receiptImageRepository) {
        this(serverName, serverUrl, processLogRepository, receiptImageRepository, new RestTemplate());
    }

    public RemoteOCRImageProcessor(final String serverName,
                                   final String serverUrl,
                                   final ProcessLogRepository processLogRepository,
                                   final ReceiptImageRepository receiptImageRepository,
                                   final RestTemplate restTemplate) {
        this.serverName = serverName;
        this.serverUrl = serverUrl.endsWith("/")? serverUrl.substring(0, serverUrl.length()-1) : serverUrl;
        this.processLogRepository = processLogRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public String getName() {
        return serverName;
    }

    @Override
    public void processImage(final ProcessItem item) {
        final ProcessLog processLog = new ProcessLog();
        processLog.setImageId(item.getImage().getId());
        processLog.setUsername(item.getUsername());
        processLog.setServerName(serverName);

        final long start = System.currentTimeMillis();
        processLog.setStartTime(start);

        log.debug("==> Start process image {} from user '{}' by calling '{}'",
                item.getImage().getFileName(), item.getUsername(), serverUrl);

        final ImageProcessResult result = restTemplate.getForObject(serverUrl + OcrServerApiUrls.URL_OCR_PROCESS,
                                                                    ImageProcessResult.class,
                                                                    item.getUserId(),
                                                                    item.getImage().getFileName(),
                                                                    item.getUsername());
        processLog.setOcrResult(result.getOcrResult());
        log.debug("Got OCR result as\n" + result.getOcrResult());
        processLog.setOcrDuration(System.currentTimeMillis() - start);

        ReceiptImage image = receiptImageRepository.findOne(item.getImage().getId());
        image.setStatus(ProcessStatusType.SCANNED);
        image.setOcrResult(result.getOcrResult());
        saveProcessResult(processLog, image);

        log.info("Finish process image {} with server '{}', took {} milli-seconds.",
                item.getImage().getFileName(), serverName, processLog.getOcrDuration());

    }

    @Transactional
    private void saveProcessResult(final ProcessLog processLog, final ReceiptImage image) {
        processLogRepository.save(processLog);
        receiptImageRepository.save(image);
    }
}
