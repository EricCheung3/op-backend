package com.openprice.parser;

import com.openprice.parser.data.Item;

public interface StoreParser {

    /**
     * Gets config properties for the store.
     * @return
     */
    StoreConfig getStoreConfig();

    String parseField(ReceiptFieldType field, ReceiptLine receiptLine);

    Item parseItemLine(String lineString);
}
