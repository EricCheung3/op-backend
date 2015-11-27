package com.openprice.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.openprice.domain.receipt.ProcessLogRepository;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.OcrServerApiUrls;
import com.openprice.ocr.client.OcrService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessQueueService {
    private final BlockingQueue<ProcessItem> queue;
    private final List<ProcessQueueConsumer> consumers;
    private final ProcessSettings settings;
    private final ProcessLogRepository processLogRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final FileSystemService fileSystemService;

    @Inject
    public ProcessQueueService(final ProcessSettings settings,
                               final ProcessLogRepository processLogRepository,
                               final ReceiptImageRepository receiptImageRepository,
                               final FileSystemService fileSystemService) {
        this.queue = new LinkedBlockingQueue<>();
        this.consumers = new ArrayList<>();
        this.settings = settings;
        this.processLogRepository = processLogRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.fileSystemService = fileSystemService;
    }

    @PostConstruct
    public void init() {
        log.info("init ProcessQueueService...");

        if ( StringUtils.hasText(settings.getServerPrefix()) && settings.getNumberOfServers() > 0) {
            for (int i=1; i<=settings.getNumberOfServers(); i++) {
                startConsumer(settings.getServerPrefix() + i);
            }
        } else if (!settings.getServers().isEmpty()) {
            for (final String server : settings.getServers()) {
                startConsumer(server);
            }
        } else {
            final ImageProcessor p = new StaticResultImageProcessor(processLogRepository, receiptImageRepository);
            final ProcessQueueConsumer consumer = new ProcessQueueConsumer(queue, p);
            consumers.add(consumer);
            new Thread(consumer).start();
        }
    }

    private void startConsumer(final String server) {
        final String serviceUrl = "http://" + server + ":" + settings.getServerPort() + OcrServerApiUrls.URL_OCR_PROCESSOR;
        final OcrService ocrService = new OcrService(serviceUrl);
        final RemoteOCRImageProcessor p = new RemoteOCRImageProcessor(server,
                                                                      ocrService,
                                                                      fileSystemService,
                                                                      processLogRepository,
                                                                      receiptImageRepository);
        final ProcessQueueConsumer consumer = new ProcessQueueConsumer(queue, p);
        consumers.add(consumer);
        new Thread(consumer).start();
    }

    @PreDestroy()
    public void stop() {
        log.info("stop ProcessQueueService...");
        int count = consumers.size();
        while (count-- > 0) {
            queue.add(new ProcessItem());
        }
    }

    /**
     * Adds one receipt image to the process queue, and it will be consumed by one of image processor.
     *
     * @param image
     */
    public void addImage(final String imageId, final String ownerId, final String requesterId) {
        final ProcessItem item = new ProcessItem();
        item.setOwnerId(ownerId);
        item.setRequesterId(requesterId);
        item.setImageId(imageId);
        item.setAddTime(new Date());
        queue.add(item);
    }

    /**
     * Monitor how many images in the queue now.
     * @return
     */
    public int getQueueSize() {
        return queue.size();
    }
}
