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
import com.openprice.domain.receipt.ReceiptImage;
import com.openprice.domain.receipt.ReceiptImageRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProcessQueueService {
    private final BlockingQueue<ProcessItem> queue;
    private final List<ProcessQueueConsumer> consumers;
    private final ProcessSettings settings;
    private final ProcessLogRepository processLogRepository;
    private final ReceiptImageRepository receiptImageRepository;

    @Inject
    public ProcessQueueService(final ProcessSettings settings,
                               final ProcessLogRepository processLogRepository,
                               final ReceiptImageRepository receiptImageRepository) {
        this.queue = new LinkedBlockingQueue<>();
        this.consumers = new ArrayList<>();
        this.settings = settings;
        this.processLogRepository = processLogRepository;
        this.receiptImageRepository = receiptImageRepository;
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
        final String serverUrl = "http://" + server + ":" + settings.getServerPort();
        final RemoteOCRImageProcessor p = new RemoteOCRImageProcessor(server, serverUrl, processLogRepository, receiptImageRepository);
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
    public void addImage(final ReceiptImage image) {
        log.info("New image {} added to queue for file {}.", image.getId(), image.getFileName());
        final ProcessItem item = new ProcessItem();
        item.setImage(image);
        item.setAddTime(new Date());
        item.setUserId(image.getReceipt().getUser().getId());
        item.setUsername(image.getReceipt().getUser().getUsername());
        queue.add(item);
    }

    public void addImage(final String imageId) {
        addImage(receiptImageRepository.findOne(imageId));
    }

    /**
     * Monitor how many images in the queue now.
     * @return
     */
    public int getQueueSize() {
        return queue.size();
    }
}
