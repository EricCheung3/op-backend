package com.openprice.process;

public interface ImageProcessor {
    /**
     * Returns this process or name for logging purpose.
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
