package com.openprice.parser.store;

import com.openprice.parser.ReceiptData;
import com.openprice.parser.ReceiptField;

public interface StoreParser {

    /**
     * Gets config properties for the store.
     * @return
     */
    StoreConfig getStoreConfig();

    String parseField(ReceiptField field, ReceiptData receipt, int line);
}
