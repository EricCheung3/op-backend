package com.openprice.parser;

public interface StoreParser {

    /**
     * Gets config properties for the store.
     * @return
     */
    StoreConfig getStoreConfig();

    String parseField(ReceiptField field, ReceiptData receipt, int line);
}
