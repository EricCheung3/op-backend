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

import com.openprice.common.client.ServiceSettings;
import com.openprice.domain.receipt.ProcessLogRepository;
import com.openprice.domain.receipt.ReceiptImageRepository;
import com.openprice.file.FileSystemService;
import com.openprice.ocr.client.OcrService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessQueueService {
    private final BlockingQueue<ProcessItem> queue;
    private final List<ProcessQueueConsumer> consumers;
    private final ProcessSettings processSettings;
    private final ProcessLogRepository processLogRepository;
    private final ReceiptImageRepository receiptImageRepository;
    private final FileSystemService fileSystemService;

    @Inject
    public ProcessQueueService(final ProcessSettings processSettings,
                               final ProcessLogRepository processLogRepository,
                               final ReceiptImageRepository receiptImageRepository,
                               final FileSystemService fileSystemService) {
        this.queue = new LinkedBlockingQueue<>();
        this.consumers = new ArrayList<>();
        this.processSettings = processSettings;
        this.processLogRepository = processLogRepository;
        this.receiptImageRepository = receiptImageRepository;
        this.fileSystemService = fileSystemService;
    }

    @PostConstruct
    public void init() {
        log.info("init ProcessQueueService...");

        if ( StringUtils.hasText(processSettings.getServerPrefix()) && processSettings.getNumberOfServers() > 0) {
            for (int i=1; i<=processSettings.getNumberOfServers(); i++) {
                startConsumer(new ServiceSettings(processSettings.getServerPrefix() + i, processSettings.getServerPort()));
            }
        } else if (!processSettings.getOcrServers().isEmpty()) {
            for (final ServiceSettings settings : processSettings.getOcrServers()) {
                startConsumer(settings);
            }
        } else {
            final ImageProcessor p = new StaticResultImageProcessor(processLogRepository, receiptImageRepository);
            final ProcessQueueConsumer consumer = new ProcessQueueConsumer(queue, p);
            consumers.add(consumer);
            new Thread(consumer).start();
        }
    }

    private void startConsumer(final ServiceSettings settings) {
        final OcrService ocrService = new OcrService(settings);
        final RemoteOCRImageProcessor p = new RemoteOCRImageProcessor(settings.getServerHost(),
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
            queue.add(new ProcessItem(null, null, null, null));
        }
    }

    /**
     * Adds one receipt image to the process queue, and it will be consumed by one of image processor.
     *
     * @param image
     */
    public void addImage(final String imageId, final String ownerId, final String requesterId) {
        queue.add(new ProcessItem(imageId, ownerId, requesterId, new Date()));
    }

    /**
     * Monitor how many images in the queue now.
     * @return
     */
    public int getQueueSize() {
        return queue.size();
    }
}
