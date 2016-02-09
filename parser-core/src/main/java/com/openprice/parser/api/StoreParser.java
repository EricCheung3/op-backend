package com.openprice.parser.api;

import com.openprice.parser.ParsedItem;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.StoreConfigImpl;

public interface StoreParser {

    /**
     * Gets config properties for the store.
     * @return
     */
    StoreConfigImpl getStoreConfig();

    String parseField(ReceiptFieldType field, ReceiptLine receiptLine);

    ParsedItem parseItemLine(String lineString, int lineNumber);
}
