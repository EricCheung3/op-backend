package com.openprice.process;

/**
 * Interface for a generic image processor.
 */
public interface ImageProcessor {

    /**
     * Returns this processor name for logging purpose.
     * @return
     */
    String getName();

    /**
     * Starts the image process with process item.
     *
     * @param item
     */
    void processImage(final ProcessItem item);
}
