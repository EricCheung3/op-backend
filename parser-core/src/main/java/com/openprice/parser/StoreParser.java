package com.openprice.parser;

public interface StoreParser {

    /**
     * Gets config properties for the store.
     * @return
     */
    StoreConfig getStoreConfig();

    String parseField(ReceiptFieldType field, ReceiptLine receiptLine);

    ParsedItem parseItemLine(String lineString, int lineNumber);
}
