package com.openprice.parser.api;

import com.openprice.parser.ParsedItem;
import com.openprice.parser.ReceiptFieldType;

public interface StoreParser {

    /**
     * Gets config properties for the store.
     * @return
     */
    StoreConfig getStoreConfig();

    String parseField(ReceiptFieldType field, ReceiptLine receiptLine);

    ParsedItem parseItemLine(String lineString, int lineNumber);
}
