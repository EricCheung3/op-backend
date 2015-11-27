package com.openprice.process;

import java.util.concurrent.BlockingQueue;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessQueueConsumer implements Runnable {

    private final BlockingQueue<ProcessItem> queue;
    private final ImageProcessor imageProcessor;

    public ProcessQueueConsumer(final BlockingQueue<ProcessItem> queue, final ImageProcessor imageProcessor) {
        this.queue = queue;
        this.imageProcessor = imageProcessor;
    }

    @Override
    public void run() {
        log.info("Process Queue Consumer started for processor {}.", imageProcessor.getName());
        while (true) {
            try {
                ProcessItem item = queue.take();
                if (item.getImageId() == null) {
                    break; // poison pill algorithm
                }
                imageProcessor.processImage(item);
            } catch (InterruptedException ex) {
                log.error("Got InterruptedException in ImageProcessor!", ex);
            } catch (Exception ex) {
                log.error("SEVERE!!! got exception during image processing.", ex);
            }
        }
        log.info("Process Queue Consumer stopped for processor {}.", imageProcessor.getName());
    }
}
